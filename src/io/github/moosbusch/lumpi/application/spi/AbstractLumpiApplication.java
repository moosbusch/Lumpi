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

package io.github.moosbusch.lumpi.application.spi;

import io.github.moosbusch.lumpi.application.LumpiApplication;
import io.github.moosbusch.lumpi.application.LumpiApplicationContext;
import io.github.moosbusch.lumpi.application.impl.DefaultLumpiApplicationContext;
import io.github.moosbusch.lumpi.beans.impl.LumPiMiscBean;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;
import io.github.moosbusch.lumpi.gui.window.swing.impl.HostFrame;
import io.github.moosbusch.lumpi.util.LumpiUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Display;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractLumpiApplication extends Application.Adapter
    implements LumpiApplication<LumpiApplicationContext, BindableWindow> {
    private final LumpiApplicationContext context;
    private final PivotApplicationContext pivotAppContext;

    public AbstractLumpiApplication() {
        this.pivotAppContext = new PivotApplicationContext();
        this.context = initApplicationContext();
    }

    private void closeApplicationContext() {
        try (final LumpiApplicationContext ctx = getApplicationContext()) {
            ctx.shutdownContext();
            ctx.close();
        }
    }

    private LumpiApplicationContext initApplicationContext() {
        return Objects.requireNonNull(createApplicationContext());
    }

    protected LumpiApplicationContext createApplicationContext() {
        return new DefaultLumpiApplicationContext(this);
    }

    protected void onApplicationEventImpl(ApplicationEvent event) {
    }

    @Override
    public final BindableWindow getApplicationWindow() {
        return getApplicationContext().getApplicationWindow();
    }

    @Override
    public final LumpiApplicationContext getApplicationContext() {
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
            Logger.getLogger(AbstractLumpiApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            HostFrame hostFrame = getApplicationContext().getHostFrame();
            hostFrame.dispose();
        }
        return result;
    }

    @Override
    public Collection<Class<?>> getBeanConfigurationClasses() {
        return LumpiUtil.asSet(new Class<?>[]{LumPiMiscBean.class});
    }

    @Override
    public Collection<String> getBeanConfigurationPackages() {
        return Collections.emptySet();
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
