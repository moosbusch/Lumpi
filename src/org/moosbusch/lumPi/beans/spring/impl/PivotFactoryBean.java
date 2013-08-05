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
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
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
        AutowireCapableBeanFactory autowireBeanFactory =
                getApplicationContext().getAutowireCapableBeanFactory();
        List<Class<?>> superClasses =
                ClassUtils.getAllSuperclasses(bindable.getClass());

        for (Class<?> superClass : superClasses) {
            if (Bindable.class.isAssignableFrom(superClass)) {
                Object obj = superClass.cast(bindable);
                serializer.bind(bindable, superClass);
                autowireBeanFactory.autowireBean(obj);
            }
        }

        serializer.bind(bindable);
        autowireBeanFactory.autowireBean(bindable);
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
