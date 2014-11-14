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
import org.apache.pivot.collections.MapListener;
import org.apache.pivot.collections.Set;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.beans.PropertyChangeAware;
import org.moosbusch.lumPi.beans.impl.Options;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.moosbusch.lumPi.gui.window.swing.impl.HostFrame;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author moosbusch
 */
public interface LumPiApplicationContext
        extends ConfigurableApplicationContext, PropertyChangeAware, MapListener<String, Object> {

    public static final String APPLICATION_WINDOW_PROPERTY_NAME = "applicationWindow";

    public void initializeGUI();

    public <T> T createBean(Class<T> type);

    public SpringBXMLSerializer getSerializer();

    public Resources getResources();

    public URL getLocation();

    public Options getPreferences();

    public LumPiApplication<? extends LumPiApplicationContext, ? extends BindableWindow> getApplication();

    public BindableWindow getApplicationWindow();

    public void setApplicationWindow(BindableWindow window);

    public Set<String> getComponents();

    public HostFrame getHostFrame();

    public void shutdownContext();

}
