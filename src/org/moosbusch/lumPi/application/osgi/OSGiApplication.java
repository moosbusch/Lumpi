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
package org.moosbusch.lumPi.application.osgi;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Display;
import org.moosbusch.lumPi.application.DesktopApplication;
import org.moosbusch.lumPi.application.PivotApplicationContext;
import org.osgi.framework.BundleActivator;

/**
 *
 * @author moosbusch
 */
public interface OSGiApplication<T extends PivotApplicationContext> extends DesktopApplication<T> {

    public OSGiController getOSGiController();

    public BundleActivator[] getBundleActivators();

    public String[] getServiceNames();

    public static abstract class Adapter extends DesktopApplication.Adapter
            implements OSGiApplication<PivotApplicationContext> {

        private final OSGiController oSGiController;

        public Adapter() {
            this.oSGiController = new OSGiController.Adapter();
        }

        protected void startupImplExt(Display display, Map<String, String> namespace) throws Exception {
        }

        protected boolean shutdownImplExt(boolean optional) {
            return false;
        }

        @Override
        protected final void startupImpl(Display display, Map<String, String> namespace) throws Exception {
            OSGiController controller = Objects.requireNonNull(getOSGiController());
            controller.startFramework(getBundleActivators());
            startupImplExt(display, namespace);
        }

        @Override
        protected final boolean shutdownImpl(boolean optional) {
            OSGiController controller = Objects.requireNonNull(getOSGiController());

            try {
                controller.stopFramework();
            } catch (Exception ex) {
                Logger.getLogger(OSGiApplication.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

            return shutdownImplExt(optional);
        }

        @Override
        public OSGiController getOSGiController() {
            return oSGiController;
        }
    }
}
