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

import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import java.net.URL;
import java.util.prefs.Preferences;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.moosbusch.lumPi.application.spi.AbstractDesktopApplication;
import org.moosbusch.lumPi.beans.spring.impl.PivotFactoryBean;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface DesktopApplication extends Application {

    public Preferences getPreferences();

    public Class<? extends PivotFactoryBean> getPivotBeanFactoryClass();

    public Class<?>[] getAnnotatedClasses();

    public String[] getAnnotatedBeanPackages();

    public URL[] getBeanConfigurations();

    public URL getBXMLConfiguration();

    public Resources getResources();

    public PivotApplicationContext getApplicationContext();

    public BindableWindow getApplicationWindow();

    public Display getDisplay();

    public abstract class Adapter extends AbstractDesktopApplication
            implements DesktopApplication {

        @Override
        protected void startupImpl(Display display, Map<String, String> namespace) throws Exception {
        }

        @Override
        protected boolean shutdownImpl(boolean optional) {
            return false;
        }

        @Override
        public Resources getResources() {
            return null;
        }

    }
}
