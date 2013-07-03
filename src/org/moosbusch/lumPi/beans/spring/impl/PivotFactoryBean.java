/*

 *
 */
package org.moosbusch.lumPi.beans.spring.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.Action;
import org.moosbusch.lumPi.action.ChildWindowAction;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import org.moosbusch.lumPi.beans.spring.annotation.Autowire;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author moosbusch
 */
@Configuration
public class PivotFactoryBean implements FactoryBean<BindableWindow> {

    @Autowired
    private ApplicationContext applicationContext;
    private BindableWindow applicationWindow;
    private BXMLSerializer serializer;

    private Map<String, Object> processNamespace(BXMLSerializer serializer) {
        Map<String, Object> result = serializer.getNamespace();

        for (String id : result) {
            Object obj = Objects.requireNonNull(result.get(id));

            if (obj instanceof Bindable) {
                bind(serializer, (Bindable) obj);
            }

            if (obj instanceof Action) {
                registerAction(id, (Action) obj);
            }

            if (obj instanceof ChildWindowAction) {
                ChildWindowAction cwa = (ChildWindowAction) obj;
                BindableWindow window = Objects.requireNonNull(getApplicationWindow());
                cwa.setApplicationWindow(window);
            }

            if (obj.getClass().isAnnotationPresent(Autowire.class)) {
                getApplicationContext().getAutowireCapableBeanFactory().autowireBean(obj);
            }
        }

        return result;
    }

    protected BindableWindow readObject(BXMLSerializer serializer)
            throws IOException, SerializationException {
        BindableWindow result = getApplicationWindow();

        if (result == null) {
            if (serializer.getResources() != null) {
                result = (BindableWindow) serializer.readObject(
                        serializer.getLocation(), serializer.getResources());
            } else {
                result = (BindableWindow) serializer.readObject(
                        serializer.getLocation());
            }
        }

        return result;
    }

    protected void registerAction(String actionNameKey, Action action) {
        if (StringUtils.isNotBlank(actionNameKey)) {
            Action.getNamedActions().put(actionNameKey, action);
        }
    }

    protected void bind(BXMLSerializer serializer, Bindable bindable) {
        List<Class<?>> superClasses =
                ClassUtils.getAllSuperclasses(bindable.getClass());

        for (Class<?> superClass : superClasses) {
            if (Bindable.class.isAssignableFrom(superClass)) {
                serializer.bind(bindable, superClass);
            }
        }

        serializer.bind(bindable);
        bindable.initialize(serializer.getNamespace(),
                serializer.getLocation(), serializer.getResources());
    }

    protected BindableWindow getApplicationWindow() {
        return applicationWindow;
    }

    protected void setApplicationWindow(BindableWindow applicationWindow) {
        this.applicationWindow = applicationWindow;
    }

    @Override
    public final BindableWindow getObject() throws Exception {
        BXMLSerializer ser = Objects.requireNonNull(getSerializer());
        BindableWindow result = readObject(ser);
        setApplicationWindow(Objects.requireNonNull(result));
        getApplicationContext().setNamespace(processNamespace(serializer));
        getApplicationContext().setLocation(ser.getLocation());
        getApplicationContext().setResources(ser.getResources());
        return result;
    }

    @Override
    public final Class<BindableWindow> getObjectType() {
        return BindableWindow.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public PivotApplicationContext getApplicationContext() {
        return (PivotApplicationContext) applicationContext;
    }

    public BXMLSerializer getSerializer() {
        return serializer;
    }

    public void setSerializer(BXMLSerializer serializer) {
        this.serializer = serializer;
    }

}
