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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.moosbusch.lumPi.application.impl.DefaultPivotApplicationContext;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author moosbusch
 */
public interface DesktopApplication<T extends PivotApplicationContext>
        extends Application {

    public Class<?>[] getConfigurationClasses();

    public String[] getConfigurationPackages();

    public URL[] getBeanConfigurationFiles();

    public URL getBXMLConfigurationFile();

    public Resources getResources();

    public T getApplicationContext();

    public BindableWindow getApplicationWindow();

    public abstract class Adapter extends Application.Adapter
            implements DesktopApplication<PivotApplicationContext> {

        private final PivotApplicationContext context;

        public Adapter() {
            this.context = initApplicationContext();
        }

        private void closeApplicationContext() {
            try (PivotApplicationContext ctx = getApplicationContext()) {
                ctx.shutdownContext();
                ctx.close();
            }
        }

        private PivotApplicationContext initApplicationContext() {
            return Objects.requireNonNull(createApplicationContext());
        }

        protected PivotApplicationContext createApplicationContext() {
            return new DefaultPivotApplicationContext(this);
        }

        protected void startupImpl(Display display, BindableWindow applicationWindow,
                Map<String, String> namespace) throws Exception {
        }

        protected boolean shutdownImpl(boolean optional) {
            return false;
        }

        protected void onApplicationEventImpl(ApplicationEvent event) {
        }

        @Override
        public final BindableWindow getApplicationWindow() {
            BindableWindow result = getApplicationContext().getApplicationWindow();

            if (result != null) {
                return result;
            }

            return getApplicationContext().getBean(BindableWindow.class);
        }

        @Override
        public final PivotApplicationContext getApplicationContext() {
            return context;
        }

        @Override
        public final void startup(Display display, Map<String, String> properties)
                throws Exception {
            BindableWindow window = Objects.requireNonNull(getApplicationWindow());
            window.setDisplay(display);
            startupImpl(display, window, properties);
            window.open();
        }

        @Override
        public final boolean shutdown(boolean optional) {
            boolean optionalExt = shutdownImpl(optional);
            boolean result = false;

            closeApplicationContext();

            try {
                result = super.shutdown(optionalExt);
            } catch (Exception ex) {
                Logger.getLogger(DesktopApplication.Adapter.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

            return result;
        }

        @Override
        public Class<?>[] getConfigurationClasses() {
            return null;
        }

        @Override
        public String[] getConfigurationPackages() {
            return null;
        }

        @Override
        public Resources getResources() {
            return null;
        }
    }

}
