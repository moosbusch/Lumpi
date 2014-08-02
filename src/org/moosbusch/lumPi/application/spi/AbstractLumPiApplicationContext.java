/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moosbusch.lumPi.application.spi;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Action;
import org.moosbusch.lumPi.application.LumPiApplication;
import org.moosbusch.lumPi.application.LumPiApplicationContext;
import static org.moosbusch.lumPi.application.LumPiApplicationContext.APPLICATION_WINDOW_PROPERTY_NAME;
import org.moosbusch.lumPi.application.event.impl.ApplicationWindowLoadedEvent;
import org.moosbusch.lumPi.application.event.impl.NamespaceEvent;
import org.moosbusch.lumPi.beans.PropertyChangeAware;
import org.moosbusch.lumPi.beans.impl.LumPiMiscBean;
import org.moosbusch.lumPi.beans.impl.Options;
import org.moosbusch.lumPi.beans.impl.PivotFactoryBean;
import org.moosbusch.lumPi.beans.impl.PropertyChangeAwareAdapter;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.moosbusch.lumPi.gui.window.swing.impl.HostFrame;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractLumPiApplicationContext
        extends AnnotationConfigApplicationContext
        implements LumPiApplicationContext {

    private final LumPiApplication<? extends LumPiApplicationContext> app;
    private final PropertyChangeAware pca;
    private Map<String, Object> namespace;
    private BindableWindow applicationWindow;
    private final HostFrame hostFrame;
    private final Options options;
    private Felix felix;

    public AbstractLumPiApplicationContext(LumPiApplication<? extends LumPiApplicationContext> application) {
        this.app = Objects.requireNonNull(application);
        this.pca = new PropertyChangeAwareAdapter(this);
        this.hostFrame = new HostFrame(application);
        this.options = new Options(Preferences.userNodeForPackage(application.getClass()));
//        this.bxmlSerializer = initSerializer(Objects.requireNonNull(application));
        init(application);
    }

    private void init(LumPiApplication<? extends LumPiApplicationContext> application) {
        registerShutdownHook();
        addPropertyChangeListener(new PropertyChangeListenerImpl());

        try {
            registerFactoryBeans(application);
            loadXmlConfig(application);
            loadAnnotationConfig(application);
        } finally {
            refresh();
        }
    }

    private void loadAnnotationConfig(LumPiApplication<? extends LumPiApplicationContext> application) {
        String[] beanPackages = application.getConfigurationPackages();
        Class<?>[] annotatedClasses = application.getConfigurationClasses();
        if (ArrayUtils.isNotEmpty(annotatedClasses)) {
            register(annotatedClasses);
        }
        if (ArrayUtils.isNotEmpty(beanPackages)) {
            scan(beanPackages);
        }
    }

    private void loadXmlConfig(LumPiApplication<? extends LumPiApplicationContext> application) {
        String[] configLocations = configLocationsFromURLs(
                application.getBeanConfigurationFiles());
        if (ArrayUtils.isNotEmpty(configLocations)) {
            XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this);
            for (String configLocation : configLocations) {
                Resource res = getResource(configLocation);
                if (res != null) {
                    xmlReader.loadBeanDefinitions(res);
                }
            }
        }
    }

    private Class<?>[] getPivotCoreConfigurationClasses(
            LumPiApplication<? extends LumPiApplicationContext> application) {
        return new Class<?>[]{PivotFactoryBean.class, Objects.requireNonNull(
            application.getPivotBeanConfigurationClass()), LumPiMiscBean.class};
    }

    private void registerFactoryBeans(LumPiApplication<? extends LumPiApplicationContext> application) {
        Class<?>[] corePivotConfigurationClasses = Objects.requireNonNull(
                getPivotCoreConfigurationClasses(application));
        for (Class<?> pivotFactoryBeanClass : corePivotConfigurationClasses) {
            register(pivotFactoryBeanClass);
        }
    }

    private String[] configLocationsFromURLs(URL[] locations) {
        if (ArrayUtils.isNotEmpty(locations)) {
            String[] result = new String[locations.length];
            for (int cnt = 0; cnt < result.length; cnt++) {
                result[cnt] = locations[cnt].toExternalForm();
            }
            return result;
        }
        return null;
    }

    protected void registerAction(String actionNameKey, Action action) {
        if (StringUtils.isNotBlank(actionNameKey)) {
            Action.getNamedActions().put(actionNameKey, action);
        }
    }

    @Override
    public final <T> T createBean(Class<T> type) {
        if (BindableWindow.class.isAssignableFrom(type)) {
            BindableWindow result = getApplicationWindow();

            if (result != null) {
                return (T) result;
            } else {
                result = Objects.requireNonNull((BindableWindow) getAutowireCapableBeanFactory().createBean(type));
                setApplicationWindow(result);
                return (T) result;
            }
        }

        return getAutowireCapableBeanFactory().createBean(type);
    }

    @Override
    public final LumPiApplication<? extends LumPiApplicationContext> getApplication() {
        return app;
    }

    @Override
    public final URL getLocation() {
        return app.getBXMLConfigurationFile();
    }

    @Override
    public final Map<String, Object> getNamespace() {
        return namespace;
    }

    @Override
    public final void setNamespace(Map<String, Object> uiNamespace) {
        this.namespace = uiNamespace;
        firePropertyChange(NAMESPACE_PROPERTY_NAME);
    }

    @Override
    public Options getPreferences() {
        return options;
    }

    @Override
    public final BindableWindow getApplicationWindow() {
        return applicationWindow;
    }

    @Override
    public final void setApplicationWindow(BindableWindow applicationWindow) {
        this.applicationWindow = Objects.requireNonNull(applicationWindow);
        firePropertyChange(APPLICATION_WINDOW_PROPERTY_NAME);
    }

    @Override
    public final HostFrame getHostFrame() {
        return hostFrame;
    }

    @Override
    public final BundleContext getBundleContext() {
        return felix.getBundleContext();
    }

    @Override
    public void startFramework(BundleActivator... bundleActivators) throws Exception {
        java.util.Map<String, Object> osgiProperties = getOSGiProperties();
        osgiProperties.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, Arrays.asList(bundleActivators));
        this.felix = new Felix(osgiProperties);
        felix.start();
    }

    @Override
    public void stopFramework() throws Exception {
        if (felix != null) {
            try {
                felix.stop();
                felix.waitForStop(0);
            } catch (BundleException | InterruptedException ex) {
                Logger.getLogger(LumPiApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public <T> Collection<ServiceReference<T>> getServiceReferences(Class<T> type, String serviceId)
            throws InvalidSyntaxException {
        String idFilter = "(" + getServiceIdPropertyName() + "=" + serviceId + ")";
        return getBundleContext().getServiceReferences(type, idFilter);
    }

    @Override
    public <S> S getService(ServiceReference<S> sr) {
        return getBundleContext().getService(sr);
    }

    @Override
    public boolean ungetService(ServiceReference<?> sr) {
        return getBundleContext().ungetService(sr);
    }

    @Override
    public java.util.Map<String, Object> getOSGiProperties() {
        return new HashMap<>();
    }

    @Override
    public String getServiceIdPropertyName() {
        return SERVICE_ID_PROPERTY_NAME;
    }

    @Override
    public BeanMonitor getMonitor() {
        return pca.getMonitor();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pca.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pca.removePropertyChangeListener(pcl);
    }

    @Override
    public void firePropertyChange(String propertyName) {
        pca.firePropertyChange(propertyName);
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            switch (propertyName) {
                case APPLICATION_WINDOW_PROPERTY_NAME: {
                    publishEvent(new ApplicationWindowLoadedEvent(
                            AbstractLumPiApplicationContext.this));
                    break;
                }
                case NAMESPACE_PROPERTY_NAME: {
                    publishEvent(new NamespaceEvent(getNamespace()));
                    break;
                }
            }
        }
    }

}
