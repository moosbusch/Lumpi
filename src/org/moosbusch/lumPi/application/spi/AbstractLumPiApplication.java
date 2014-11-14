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

package org.moosbusch.lumPi.application.spi;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Display;
import org.moosbusch.lumPi.application.LumPiApplication;
import org.moosbusch.lumPi.application.LumPiApplicationContext;
import org.moosbusch.lumPi.application.impl.DefaultLumPiApplicationContext;
import org.moosbusch.lumPi.beans.impl.LumPiMiscBean;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.moosbusch.lumPi.gui.window.swing.impl.HostFrame;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractLumPiApplication extends Application.Adapter
    implements LumPiApplication<LumPiApplicationContext, BindableWindow> {
    private final LumPiApplicationContext context;
    private final PivotApplicationContext pivotAppContext;

    public AbstractLumPiApplication() {
        this.pivotAppContext = new PivotApplicationContext();
        this.context = initApplicationContext();
    }

    private void closeApplicationContext() {
        try (final LumPiApplicationContext ctx = getApplicationContext()) {
            ctx.shutdownContext();
            ctx.close();
        }
    }

    private LumPiApplicationContext initApplicationContext() {
        return Objects.requireNonNull(createApplicationContext());
    }

    protected LumPiApplicationContext createApplicationContext() {
        return new DefaultLumPiApplicationContext(this);
    }

    protected void onApplicationEventImpl(ApplicationEvent event) {
    }

    @Override
    public final BindableWindow getApplicationWindow() {
        return getApplicationContext().getApplicationWindow();
    }

    @Override
    public final LumPiApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public final void startup(Display display, Map<String, String> properties) throws Exception {
        getApplicationContext().initializeGUI();
    }

    @Override
    public final boolean shutdown(boolean optional) {
        boolean result = false;
        closeApplicationContext();

        try {
            result = super.shutdown(optional);
        } catch (Exception ex) {
            Logger.getLogger(AbstractLumPiApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            HostFrame hostFrame = getApplicationContext().getHostFrame();
            hostFrame.dispose();
        }
        return result;
    }

    @Override
    public Class<?>[] getBeanConfigurationClasses() {
        return new Class<?>[]{LumPiMiscBean.class};
    }

    @Override
    public String[] getBeanConfigurationPackages() {
        return null;
    }

    @Override
    public Resources getResources() {
        return null;
    }

    private static class PivotApplicationContext extends ApplicationContext {

        static {
            createTimer();
        }

    }

}
