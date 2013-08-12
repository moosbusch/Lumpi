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

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.moosbusch.lumPi.beans.spring.impl.PivotApplicationContextImpl;
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

    public abstract class Adapter
            extends Application.Adapter implements DesktopApplication {

        private Display display = null;
        private final PivotApplicationContext context;
        private final HostWindowResizeListener windowListener;

        public Adapter() {
            this.context = new PivotApplicationContextImpl(this);
            this.windowListener = new HostWindowResizeListener();
        }

        private java.awt.Window getHostWindow() {
            if (getApplicationWindow() != null) {
                return getApplicationWindow().getDisplay().getHostWindow();
            }

            return null;
        }

        private void closeApplicationWindow() {
            Display dsp = getDisplay();

            for (int i = dsp.getLength() - 1; i >= 0; i--) {
                Window win = (Window) dsp.get(i);
                win.close();
            }
        }

        private void closeApplicationContext() {
            try (PivotApplicationContext ctx = getApplicationContext()) {
                ctx.stop();
            }
        }

        protected void startupImpl(Display display, Map<String, String> namespace) throws Exception {
        }

        protected boolean shutdownImpl(boolean optional) {
            return false;
        }

        @Override
        public final BindableWindow getApplicationWindow() {
            return getApplicationContext().getBean(BindableWindow.class);
        }

        @Override
        public final Display getDisplay() {
            return display;
        }

        private void setDisplay(Display display) {
            this.display = display;
        }

        @Override
        public final PivotApplicationContext getApplicationContext() {
            return context;
        }

        @Override
        public final void startup(Display display, Map<String, String> properties)
                throws Exception {
            setDisplay(Objects.requireNonNull(display));
            Window window = Objects.requireNonNull(getApplicationWindow());
            window.open(display);
            DesktopApplicationContext.sizeHostToFit(window);
            java.awt.Window hostWindow = Objects.requireNonNull(getHostWindow());
            hostWindow.addComponentListener(windowListener);
            startupImpl(display, properties);
        }

        @Override
        public final boolean shutdown(boolean optional) {
            boolean optionalExt = shutdownImpl(optional);
            boolean result = false;

            closeApplicationContext();
            closeApplicationWindow();

            try {
                result = super.shutdown(optionalExt);
            } catch (Exception ex) {
                Logger.getLogger(DesktopApplication.Adapter.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

            return result;
        }

        @Override
        public Preferences getPreferences() {
            return Preferences.userNodeForPackage(getClass());
        }

        @Override
        public Class<? extends PivotFactoryBean> getPivotBeanFactoryClass() {
            return PivotFactoryBean.class;
        }

        @Override
        public Class<?>[] getAnnotatedClasses() {
            return null;
        }

        @Override
        public String[] getAnnotatedBeanPackages() {
            return null;
        }

        @Override
        public Resources getResources() {
            return null;
        }

        private class HostWindowResizeListener extends ComponentAdapter {

            @Override
            public void componentResized(ComponentEvent e) {
                java.awt.Component cmp = e.getComponent();

                if (cmp instanceof java.awt.Window) {
                    java.awt.Window hostWindow = (java.awt.Window) cmp;
                    java.awt.Insets insets = hostWindow.getInsets();

                    synchronized (hostWindow.getTreeLock()) {
                        getApplicationWindow().setPreferredSize(
                                cmp.getWidth() - insets.left - insets.right,
                                cmp.getHeight() - insets.top - insets.bottom);
                    }
                }
            }
        }
    }


}
