/*
 Copyright 2013 Gunnar Kappei

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.moosbusch.lumPi.application;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.beans.Resolvable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Action;
import org.moosbusch.lumPi.action.ChildWindowAction;
import static org.moosbusch.lumPi.application.OSGiController.SERVICE_ID_PROPERTY_NAME;
import org.moosbusch.lumPi.application.impl.DefaultSpringBXMLSerializer;
import org.moosbusch.lumPi.beans.PropertyChangeAware;
import org.moosbusch.lumPi.beans.impl.LumPiMiscBean;
import org.moosbusch.lumPi.beans.impl.Options;
import org.moosbusch.lumPi.beans.impl.PivotFactoryBean;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.moosbusch.lumPi.gui.window.swing.impl.HostFrame;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author moosbusch
 */
public interface LumPiApplicationContext
        extends ConfigurableApplicationContext, Resolvable, OSGiController,
        PropertyChangeAware {

    public static final String APPLICATION_WINDOW_PROPERTYNAME = "applicationWindow";

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException;

    @Override
    public boolean containsBean(String id);

    @Override
    public Object getBean(String id);

    @Override
    public <T> T getBean(String name, Class<T> requiredType);

    @Override
    public Object getBean(String name, Object... args) throws BeansException;

    public void bindBean(Bindable bindable, SpringBXMLSerializer ser);

    public void autowireBean(Object beanInstance);

    public Object configureBean(Object beanInstance, String id);

    public <T> T createBean(Class<T> type);

    public SpringBXMLSerializer getSerializer();

    public Options getPreferences();

    public LumPiApplication<? extends LumPiApplicationContext> getApplication();

    public BindableWindow getApplicationWindow();

    public void setApplicationWindow(BindableWindow window);

    public HostFrame getHostFrame();

    public void shutdownContext();

    public abstract class Adapter
            extends AnnotationConfigApplicationContext
            implements LumPiApplicationContext {

        private final LumPiApplication<? extends LumPiApplicationContext> app;
        private final SpringBXMLSerializer bxmlSerializer;
        private final PropertyChangeAware pca;
        private BindableWindow applicationWindow;
        private final HostFrame hostFrame;
        private final Options options;
        private Felix felix;

        public Adapter(LumPiApplication<? extends LumPiApplicationContext> application) {
            this.app = Objects.requireNonNull(application);
            this.pca = new PropertyChangeAware.Adapter(this);
            this.hostFrame = new HostFrame(application);
            this.options = new Options(Preferences.userNodeForPackage(application.getClass()));
            this.bxmlSerializer = initSerializer(Objects.requireNonNull(application));
            init(application);
        }

        private void init(LumPiApplication<? extends LumPiApplicationContext> application) {
            registerShutdownHook();
            addPropertyChangeListener(new ApplicationWindowListener());

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

            if ((ArrayUtils.isNotEmpty(annotatedClasses))) {
                register(annotatedClasses);
            }

            if ((ArrayUtils.isNotEmpty(beanPackages))) {
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

        private void registerFactoryBeans(
                LumPiApplication<? extends LumPiApplicationContext> application) {
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

        private SpringBXMLSerializer initSerializer(LumPiApplication<? extends LumPiApplicationContext> application) {
            SpringBXMLSerializer result = Objects.requireNonNull(createSerializer());
            result.setLocation(application.getBXMLConfigurationFile());
            result.setResources(application.getResources());
            return result;
        }

        protected void processNamespace(Map<String, Object> namespace) {
            SpringBXMLSerializer ser = getSerializer();
            ser.setNamespace(getNamespace());

            for (String id : namespace) {
                Object obj = Objects.requireNonNull(namespace.get(id));

                if (obj instanceof Bindable) {
                    bindBean((Bindable) obj, ser);
                }

                if (obj instanceof Action) {
                    registerAction(id, (Action) obj);
                }

                if (obj instanceof ChildWindowAction) {
                    ChildWindowAction cwa = (ChildWindowAction) obj;
                    BindableWindow window = Objects.requireNonNull(
                            getApplicationWindow());
                    cwa.setApplicationWindow(window);
                }
            }
        }

        protected void registerAction(String actionNameKey, Action action) {
            if (StringUtils.isNotBlank(actionNameKey)) {
                Action.getNamedActions().put(actionNameKey, action);
            }
        }

        protected SpringBXMLSerializer createSerializer() {
            return new DefaultSpringBXMLSerializer(this);
        }

        @Override
        public final void autowireBean(Object beanInstance) {
            getAutowireCapableBeanFactory().autowireBean(beanInstance);
        }

        @Override
        public final <T> T createBean(Class<T> type) {
            return getAutowireCapableBeanFactory().createBean(type);
        }

        @Override
        public final Object configureBean(Object beanInstance, String id) {
            return getAutowireCapableBeanFactory().configureBean(beanInstance, id);
        }

        @Override
        public void bindBean(Bindable bindable, SpringBXMLSerializer ser) {
            ser.bind(bindable, bindable.getClass());
            bindable.initialize(ser.getNamespace(),
                    ser.getLocation(), ser.getResources());
        }

        @Override
        public boolean containsBean(String id) {
            boolean result = false;

            if (StringUtils.isNotBlank(id)) {
                result = super.containsBean(id);

                if ((!result) && (getParent() != null)) {
                    result = getParent().containsBean(id);
                }

                if ((!result) && (getNamespace() != null)) {
                    result = getNamespace().containsKey(id);
                }
            }

            return result;
        }

        @Override
        public Object getBean(String id) {
            Object result = null;

            if (StringUtils.isNotBlank(id)) {
                if (super.containsBean(id)) {
                    result = super.getBean(id);
                } else if (getNamespace() != null) {
                    result = getNamespace().get(id);
                }
            }

            return result;
        }

        @Override
        public <T> T getBean(String name, Class<T> requiredType) {
            Object result = null;

            if (super.containsBean(name)) {
                result = super.getBean(name, requiredType);
            } else if (getNamespace() != null) {
                result = getNamespace().get(name);

                if (result != null) {
                    if (ClassUtils.isAssignable(result.getClass(), requiredType)) {
                        return (T) result;
                    }
                }
            }

            if (result != null) {
                return (T) result;
            }

            return null;
        }

        @Override
        public Object getBean(String name, Object... args) throws BeansException {
            Object result = null;

            if (super.containsBean(name)) {
                result = super.getBean(name, args);
            } else if (getNamespace() != null) {
                result = getNamespace().get(name);

                if (result != null) {
                    Constructor<?> constructor;

                    if (ArrayUtils.isNotEmpty(args)) {
                        Class<?>[] parameterTypes = new Class<?>[0];

                        for (Object arg : args) {
                            parameterTypes = ArrayUtils.add(parameterTypes, arg.getClass());
                        }

                        constructor = ConstructorUtils.getAccessibleConstructor(
                                result.getClass(), parameterTypes);
                    } else {
                        constructor = ConstructorUtils.getAccessibleConstructor(
                                result.getClass(), new Class<?>[0]);
                    }

                    if (constructor != null) {
                        return result;
                    }
                }
            }

            return result;
        }

        @Override
        public final LumPiApplication<? extends LumPiApplicationContext> getApplication() {
            return app;
        }

        @Override
        public final SpringBXMLSerializer getSerializer() {
            return bxmlSerializer;
        }

        @Override
        public final URL getLocation() {
            return getSerializer().getLocation();
        }

        @Override
        public final void setLocation(URL location) {
            getSerializer().setLocation(location);
        }

        @Override
        public final Map<String, Object> getNamespace() {
            Map<String, Object> result = getSerializer().getNamespace();

            for (final String beanId : getBeanDefinitionNames()) {
                if (!result.containsKey(beanId)) {
                    final Object bean = getBean(beanId);
                    result.put(beanId, bean);
                }
            }

            return result;
        }

        @Override
        public final void setNamespace(Map<String, Object> pivotNamespace) {
            getSerializer().setNamespace(pivotNamespace);
        }

        @Override
        public final Resources getResources() {
            return getSerializer().getResources();
        }

        @Override
        public final void setResources(Resources resources) {
            getSerializer().setResources(resources);
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
            this.applicationWindow = applicationWindow;
            firePropertyChange(APPLICATION_WINDOW_PROPERTYNAME);
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
            osgiProperties.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
                    Arrays.asList(bundleActivators));
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
                    Logger.getLogger(LumPiApplicationContext.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public <T> Collection<ServiceReference<T>> getServiceReferences(
                Class<T> type, String serviceId) throws InvalidSyntaxException {
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

        private class ApplicationWindowListener implements PropertyChangeListener {

            @Override
            public void propertyChanged(Object bean, String propertyName) {
                if (propertyName.equals(APPLICATION_WINDOW_PROPERTYNAME)) {
                    processNamespace(getNamespace());
                }
            }
        }
    }
}
