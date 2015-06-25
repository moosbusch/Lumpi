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
package io.github.moosbusch.lumpi.application.impl;

import org.apache.pivot.util.Resources;
import io.github.moosbusch.lumpi.application.LumpiApplication;
import io.github.moosbusch.lumpi.application.LumpiApplicationContext;
import io.github.moosbusch.lumpi.application.SpringBXMLSerializer;
import io.github.moosbusch.lumpi.application.spi.AbstractLumpiApplicationContext;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public class DefaultLumpiApplicationContext
        extends AbstractLumpiApplicationContext {

    public DefaultLumpiApplicationContext(LumpiApplication<? extends LumpiApplicationContext,
            ? extends BindableWindow> application) {
        super(application);
    }

    @Override
    public void shutdownContext() {
    }

    @Override
    public SpringBXMLSerializer getSerializer() {
        return createBean(DefaultSpringBXMLSerializer.class);
    }

    @Override
    public Resources getResources() {
        return null;
    }

}
