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
package org.moosbusch.lumPi.beans.spring.spi;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Action;
import org.moosbusch.lumPi.action.ChildWindowAction;
import org.moosbusch.lumPi.application.DesktopApplication;
import org.moosbusch.lumPi.beans.PropertyChangeAware;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import static org.moosbusch.lumPi.beans.spring.PivotApplicationContext.APPLICATION_WINDOW_PROPERTYNAME;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractPivotApplicationContext
        extends AnnotationConfigApplicationContext
        implements PivotApplicationContext {

    private final BXMLSerializer serializer;
    private final PropertyChangeAware pca;
    private BindableWindow applicationWindow;

    public AbstractPivotApplicationContext(DesktopApplication application) {
        this.pca = new PropertyChangeAware.Adapter(this);
        this.serializer = initSerializer(Objects.requireNonNull(application));
        init(application);
    }

    private void init(DesktopApplication application) {
        registerShutdownHook();
        addPropertyChangeListener(new ApplicationWindowListener());

        try {
            loadXmlConfig(application);
            loadAnnotationConfig(application);
        } finally {
            refresh();
        }
    }

    private BXMLSerializer initSerializer(DesktopApplication application) {
        BXMLSerializer result = Objects.requireNonNull(createSerializer());
        result.setLocation(application.getBXMLConfiguration());
        result.setResources(application.getResources());
        return result;
    }

    private void loadAnnotationConfig(DesktopApplication application) {
        Class<?> pivotFactoryBeanClass = Objects.requireNonNull(
                application.getPivotBeanFactoryClass());
        String[] beanPackages = application.getAnnotatedBeanPackages();
        Class<?>[] annotatedClasses = application.getAnnotatedClasses();

        if ((ArrayUtils.isNotEmpty(annotatedClasses))) {
            register(ArrayUtils.add(annotatedClasses, pivotFactoryBeanClass));
        } else {
            register(pivotFactoryBeanClass);
        }

        if ((ArrayUtils.isNotEmpty(beanPackages))) {
            scan(beanPackages);
        }
    }

    private void loadXmlConfig(DesktopApplication application) {
        String[] configLocations = configLocationsFromURLs(
                application.getBeanConfigurations());

        if (ArrayUtils.isNotEmpty(configLocations)) {
            XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this);

            for (int cnt = 0; cnt < configLocations.length; cnt++) {
                Resource res = getResource(configLocations[cnt]);

                if (res != null) {
                    xmlReader.loadBeanDefinitions(res);
                }
            }
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

    protected BXMLSerializer createSerializer() {
        return new BXMLSerializer();
    }

    private void processNamespace() {
        Map<String, Object> result = getNamespace();

        for (String id : result) {
            Object obj = Objects.requireNonNull(result.get(id));

            if (obj instanceof Bindable) {
                bindBean((Bindable) obj);
            }

            if (obj instanceof Action) {
                registerAction(id, (Action) obj);
            }

            if (obj instanceof ChildWindowAction) {
                ChildWindowAction cwa = (ChildWindowAction) obj;
                BindableWindow window = Objects.requireNonNull(getApplicationWindow());
                cwa.setApplicationWindow(window);
            }
        }
    }

    private void registerAction(String actionNameKey, Action action) {
        if (StringUtils.isNotBlank(actionNameKey)) {
            Action.getNamedActions().put(actionNameKey, action);
        }
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
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        T result = super.getBean(requiredType);

        return result;
    }

    @Override
    public Object getBean(String id) {
        Object result = null;

        if (StringUtils.isNotBlank(id)) {
            result = super.getBean(id);

            if ((result == null) && (getNamespace() != null)) {
                result = getNamespace().get(id);
            }
        }

        return result;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        Object result = super.getBean(name, requiredType);

        if ((result == null) && (getNamespace() != null)) {
            result = getNamespace().get(name);

            if (result != null) {
                if (ClassUtils.isAssignable(result.getClass(), requiredType)) {
                    return (T) result;
                }
            }
        }

        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        Object result = super.getBean(name, args);

        if (result == null) {
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

        return null;
    }

    @Override
    public void bindBean(Bindable bindable) {
        BXMLSerializer ser = Objects.requireNonNull(getSerializer());
        ser.setNamespace(getNamespace());
        AutowireCapableBeanFactory autowireBeanFactory =
                getAutowireCapableBeanFactory();
        List<Class<?>> superClasses =
                ClassUtils.getAllSuperclasses(bindable.getClass());

        for (Class<?> superClass : superClasses) {
            if (Bindable.class.isAssignableFrom(superClass)) {
                Object obj = superClass.cast(bindable);
                ser.bind(bindable, superClass);
                autowireBeanFactory.autowireBean(obj);
            }
        }

        ser.bind(bindable);
        autowireBeanFactory.autowireBean(bindable);
        bindable.initialize(ser.getNamespace(),
                ser.getLocation(), ser.getResources());
    }

    @Override
    public final BXMLSerializer getSerializer() {
        return serializer;
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
    public BindableWindow getApplicationWindow() {
        return applicationWindow;
    }

    @Override
    public void setApplicationWindow(BindableWindow applicationWindow) {
        this.applicationWindow = applicationWindow;
        firePropertyChange(APPLICATION_WINDOW_PROPERTYNAME);
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
                processNamespace();
            }
        }

    }
}
