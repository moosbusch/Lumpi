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
package io.github.moosbusch.lumpi.application.spi;

import io.github.moosbusch.lumpi.application.LumpiApplication;
import io.github.moosbusch.lumpi.application.LumpiApplicationContext;
import io.github.moosbusch.lumpi.application.event.impl.ApplicationWindowLoadedEvent;
import io.github.moosbusch.lumpi.beans.PivotBeanConfiguration;
import io.github.moosbusch.lumpi.beans.PropertyChangeAware;
import io.github.moosbusch.lumpi.beans.impl.Options;
import io.github.moosbusch.lumpi.beans.impl.PropertyChangeAwareAdapter;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;
import io.github.moosbusch.lumpi.gui.window.swing.impl.HostFrame;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.HashSet;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Set;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.WindowStateListener;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractLumpiApplicationContext
        extends AnnotationConfigApplicationContext
        implements LumpiApplicationContext {

    public static final String APPLICATION_BEAN_NAME = "applicationBean";
    public static final String APPLICATION_WINDOW_BEAN_NAME = "applicationWindowBean";
    private final Set<String> components;
    private final PropertyChangeAware pca;
    private final HostFrame hostFrame;
    private final Options options;

    public AbstractLumpiApplicationContext(LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow> application) {
        this.pca = new PropertyChangeAwareAdapter(this);
        this.hostFrame = new HostFrame(application);
        this.options = new Options(Preferences.userNodeForPackage(application.getClass()));
        this.components = new HashSet<>();
        init(application);
    }

    private void init(LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow> application) {
        registerShutdownHook();
        addPropertyChangeListener(new PropertyChangeListenerImpl());

        try {
            getBeanFactory().registerSingleton(APPLICATION_BEAN_NAME, application);
            loadAnnotationConfig(application);
        } finally {
            refresh();
        }
    }

    private void loadAnnotationConfig(LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow> application) {
        Collection<String> beanPackages = application.getBeanConfigurationPackages();
        Collection<Class<?>> annotatedClasses = application.getBeanConfigurationClasses();

        if (isValidBeanConfigurationClasses(annotatedClasses)) {

            if (!annotatedClasses.isEmpty()) {
                register(annotatedClasses.toArray(new Class[0]));
            }

            if (beanPackages != null) {
                if (!beanPackages.isEmpty()) {
                    scan(beanPackages.toArray(new String[0]));
                }
            }

            return;
        }

        throw new IllegalStateException(
                "No configuration-class of type 'PivotBeanConfiguration' is defined!");

    }

    protected boolean isValidBeanConfigurationClasses(Collection<Class<?>> beanConfigurationClasses) {
        return beanConfigurationClasses.stream().anyMatch((beanConfigurationClass)
                -> (PivotBeanConfiguration.class.isAssignableFrom(beanConfigurationClass)));
    }

    protected void registerAction(String actionNameKey, Action action) {
        if (StringUtils.isNotBlank(actionNameKey)) {
            Action.getNamedActions().put(actionNameKey, action);
        }
    }

    protected String getBeanCreationMethodName(Class<?> type) {
        Collection<Class<?>> configClasses =
                getApplication().getBeanConfigurationClasses();

        if (configClasses != null) {
            if (!configClasses.isEmpty()) {
                for (Class<?> configClass : configClasses) {
                    Method[] methods = configClass.getMethods();

                    for (Method method : methods) {
                        Class<?> returnType = method.getReturnType();
                        if (returnType == type) {
                            return method.getName();
                        }
                    }
                }
            }
        }

        throw new BeanCreationException("No qualifying bean of type '"
                + type.getName() + "' is defined!");
    }

    protected BindableWindow readObject()
            throws IOException, SerializationException {
        BindableWindow result;
        Resources resources = getResources();
        URL location = Objects.requireNonNull(getLocation());
        if (resources != null) {
            result = (BindableWindow) getSerializer().readObject(
                    location, resources);
        } else {
            result = (BindableWindow) getSerializer().readObject(
                    location);
        }
        return result;
    }

    @Override
    public final void initializeGUI() {
        BindableWindow window = null;
        try {
            window = Objects.requireNonNull(readObject());
        } catch (IOException | SerializationException ex) {
            Logger.getLogger(AbstractLumpiApplicationContext.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            if (window != null) {
                getHostFrame().setWindow(window);
                setApplicationWindow(window);
            }
        }
    }

    @Override
    public final <T> T createBean(Class<T> type) {
        return getBean(getBeanCreationMethodName(type), type);
    }

    @Override
    public final <T> T createBean(Class<T> type, Object... args) {
        return (T) getBean(getBeanCreationMethodName(type), args);
    }

    @Override
    public final LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow> getApplication() {
        return (LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow>) getBean(APPLICATION_BEAN_NAME);
    }

    @Override
    public final URL getLocation() {
        return getApplication().getBXMLConfigurationFile();
    }

    @Override
    public final Set<String> getComponents() {
        return components;
    }

    @Override
    public Options getPreferences() {
        return options;
    }

    @Override
    public final BindableWindow getApplicationWindow() {
        return (BindableWindow) getBean(APPLICATION_WINDOW_BEAN_NAME);
    }

    @Override
    public final void setApplicationWindow(BindableWindow applicationWindow) {
        getBeanFactory().registerSingleton(APPLICATION_WINDOW_BEAN_NAME,
                Objects.requireNonNull(applicationWindow));
        firePropertyChange(APPLICATION_WINDOW_PROPERTY_NAME);
    }

    @Override
    public final HostFrame getHostFrame() {
        return hostFrame;
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

    @Override
    public void valueAdded(Map<String, Object> map, String key) {
        Object value = Objects.requireNonNull(map.get(key));

        if (getComponents().add(key)) {
            getBeanFactory().registerSingleton(key, value);

            if (value instanceof Window) {
                Window window = (Window) value;
                window.getWindowStateListeners().add(new WindowStateListenerImpl());
            }
        }
    }

    @Override
    public void valueUpdated(Map<String, Object> map, String key, Object previousValue) {
    }

    @Override
    public void valueRemoved(Map<String, Object> map, String key, Object value) {
    }

    @Override
    public void mapCleared(Map<String, Object> map) {
    }

    @Override
    public void comparatorChanged(Map<String, Object> map, Comparator<String> previousComparator) {
    }

    private class WindowStateListenerImpl extends WindowStateListener.Adapter {

        @Override
        public Vote previewWindowOpen(Window window) {
            for (String key : getComponents()) {
                final Object value = Objects.requireNonNull(getBean(key));

                if (value instanceof Bindable) {
                    org.apache.pivot.wtk.ApplicationContext.queueCallback(new BindingCallback(AbstractLumpiApplicationContext.this, (Bindable) value));
                }
            }
            return super.previewWindowOpen(window);
        }

    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            switch (propertyName) {
                case APPLICATION_WINDOW_PROPERTY_NAME: {
                    publishEvent(new ApplicationWindowLoadedEvent(
                            AbstractLumpiApplicationContext.this));
                    break;
                }
                default: {
                }
            }
        }

    }

    private class BindingCallback implements Runnable {

        private final Bindable target;

        public BindingCallback(ApplicationContext applicationContext, Bindable target) {
            this.target = target;
        }

        @Override
        public void run() {
            try {
                bind(AbstractLumpiApplicationContext.this, target);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AbstractLumpiApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void bind(ApplicationContext applicationContext, Bindable bean) throws IllegalAccessException {
            Field[] fields = bean.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(BXML.class)) {
                    String fieldName = field.getName();
                    int fieldModifiers = field.getModifiers();
                    BXML bindingAnnotation = field.getAnnotation(BXML.class);
                    if ((fieldModifiers & Modifier.FINAL) > 0) {
                        throw new IllegalAccessException(fieldName + " is final.");
                    }
                    if ((fieldModifiers & Modifier.PUBLIC) == 0) {
                        field.setAccessible(true);
                    }
                    String id = bindingAnnotation.id();
                    if (StringUtils.isBlank(id) || id.equals("\0")) {
                        id = field.getName();
                    }
                    if (applicationContext.containsBean(id)) {
                        Object value = applicationContext.getBean(id);
                        FieldUtils.writeDeclaredField(target, id, value, true);
                    }
                }
            }
        }

    }

}
