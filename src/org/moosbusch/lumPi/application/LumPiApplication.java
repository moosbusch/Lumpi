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

import java.net.URL;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Application;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface LumPiApplication<T extends LumPiApplicationContext, V extends BindableWindow>
        extends Application {

    public Class<?>[] getBeanConfigurationClasses();

    public String[] getBeanConfigurationPackages();

    public URL getBXMLConfigurationFile();

    public Resources getResources();

    public T getApplicationContext();

    public V getApplicationWindow();


}
