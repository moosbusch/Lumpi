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

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.ArrayUtils;
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
import org.moosbusch.lumPi.application.LumPiApplication;
import org.moosbusch.lumPi.application.LumPiApplicationContext;
import org.moosbusch.lumPi.application.event.impl.ApplicationWindowLoadedEvent;
import org.moosbusch.lumPi.beans.PropertyChangeAware;
import org.moosbusch.lumPi.beans.impl.Options;
import org.moosbusch.lumPi.beans.impl.PropertyChangeAwareAdapter;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.moosbusch.lumPi.gui.window.swing.impl.HostFrame;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractLumPiApplicationContext
        extends AnnotationConfigApplicationContext
        implements LumPiApplicationContext {

    private final LumPiApplication<? extends LumPiApplicationContext, ? extends BindableWindow> app;
    private final Set<String> components;
    private final PropertyChangeAware pca;
    private BindableWindow applicationWindow;
    private final HostFrame hostFrame;
    private final Options options;

    public AbstractLumPiApplicationContext(LumPiApplication<? extends LumPiApplicationContext, ? extends BindableWindow> application) {
        this.app = Objects.requireNonNull(application);
        this.pca = new PropertyChangeAwareAdapter(this);
        this.hostFrame = new HostFrame(application);
        this.options = new Options(Preferences.userNodeForPackage(application.getClass()));
        this.components = new HashSet<>();
        init(application);
    }

    private void init(LumPiApplication<? extends LumPiApplicationContext, ? extends BindableWindow> application) {
        registerShutdownHook();
        addPropertyChangeListener(new PropertyChangeListenerImpl());

        try {
            loadAnnotationConfig(application);
        } finally {
            refresh();
        }
    }

    private void loadAnnotationConfig(LumPiApplication<? extends LumPiApplicationContext, ? extends BindableWindow> application) {
        String[] beanPackages = application.getBeanConfigurationPackages();
        Class<?>[] annotatedClasses = application.getBeanConfigurationClasses();

        if (ArrayUtils.isNotEmpty(annotatedClasses)) {
            register(annotatedClasses);
        }

        if (ArrayUtils.isNotEmpty(beanPackages)) {
            scan(beanPackages);
        }
    }

    protected void registerAction(String actionNameKey, Action action) {
        if (StringUtils.isNotBlank(actionNameKey)) {
            Action.getNamedActions().put(actionNameKey, action);
        }
    }

    protected String getBeanCreationMethodName(Class<?> type) {
        Class<?>[] configClasses = getApplication().getBeanConfigurationClasses();
        if (ArrayUtils.isNotEmpty(configClasses)) {
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
        throw new BeanCreationException("No qualifying bean of type '" + type.getName() + "' is defined!");
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
            Logger.getLogger(AbstractLumPiApplicationContext.class.getName()).log(
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
        String beanCreationMethodName = getBeanCreationMethodName(type);
        return getBean(beanCreationMethodName, type);
    }

    @Override
    public final LumPiApplication<? extends LumPiApplicationContext, ? extends BindableWindow> getApplication() {
        return app;
    }

    @Override
    public final URL getLocation() {
        return app.getBXMLConfigurationFile();
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
                    org.apache.pivot.wtk.ApplicationContext.queueCallback(
                            new BindingCallback(AbstractLumPiApplicationContext.this, (Bindable) value));
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
                            AbstractLumPiApplicationContext.this));
                    break;
                }
                default: {
                }
            }
        }

    }

    private static class BindingCallback implements Runnable {

        private final Bindable target;
        private final Reference<ApplicationContext> applicationContextRef;

        public BindingCallback(ApplicationContext applicationContext, Bindable target) {
            this.target = target;
        this.applicationContextRef = new WeakReference<>(applicationContext);
        }

        @Override
        public void run() {
            try {
                bind(applicationContextRef.get(), target);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AbstractLumPiApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
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
