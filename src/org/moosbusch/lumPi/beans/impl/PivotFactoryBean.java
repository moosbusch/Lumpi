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
package org.moosbusch.lumPi.beans.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.application.LumPiApplicationContext;
import org.moosbusch.lumPi.application.SpringBXMLSerializer;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author moosbusch
 */
@Configuration
public class PivotFactoryBean implements FactoryBean<BindableWindow>,
        ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    protected BindableWindow readObject(SpringBXMLSerializer serializer)
            throws IOException, SerializationException {
        BindableWindow result = getApplicationContext().getApplicationWindow();

        if (result == null) {
            Resources resources = getApplicationContext().getResources();
            URL location = Objects.requireNonNull(getApplicationContext().getLocation());
            
            if (resources != null) {
                result = (BindableWindow) serializer.readObject(
                        location, resources);
            } else {
                result = (BindableWindow) serializer.readObject(
                        location);
            }

            getApplicationContext().setNamespace(serializer.getNamespace());
        }

        return result;
    }

    @Override
    public final BindableWindow getObject() throws Exception {
        return Objects.requireNonNull(readObject(
                getApplicationContext().getSerializer()));
    }

    @Override
    public final Class<BindableWindow> getObjectType() {
        return BindableWindow.class;
    }

    @Override
    public final boolean isSingleton() {
        return true;
    }

    public LumPiApplicationContext getApplicationContext() {
        return (LumPiApplicationContext) applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
