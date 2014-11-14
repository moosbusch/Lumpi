/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moosbusch.lumPi.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Display;
import org.moosbusch.lumPi.application.LumPiApplication;

/**
 *
 * @author Gunnar Kappei
 */
public class ApplicationLauncher {

    public static final void main(Class<? extends LumPiApplication<?, ?>> applicationClass) {
        main(new String[]{applicationClass.getName()});
    }

    public static void main(String[] args) {
        String applicationClassName = args[0];
        final LumPiApplication<?, ?> application;

        try {
            application = (LumPiApplication<?, ?>) ClassLoader.getSystemClassLoader().loadClass(
                    applicationClassName).newInstance();
            if (application != null) {
                final Display display =
                        application.getApplicationContext().getHostFrame().getDisplay();

                ApplicationContext.queueCallback(() -> {
                    try {
                        application.startup(display, new HashMap<>());
                    } catch (Exception exception) {
                        ApplicationContext.handleUncaughtException(exception);
                    }
                });
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ApplicationLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
