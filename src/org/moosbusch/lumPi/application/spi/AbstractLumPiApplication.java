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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Container;
import org.apache.pivot.wtk.Display;
import org.moosbusch.lumPi.application.LumPiApplication;
import org.moosbusch.lumPi.application.LumPiApplicationContext;
import org.moosbusch.lumPi.application.OSGiController;
import org.moosbusch.lumPi.application.impl.DefaultLumPiApplicationContext;
import org.moosbusch.lumPi.beans.PivotBeanConfiguration;
import org.moosbusch.lumPi.beans.impl.DefaultPivotBeanConfiguration;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.moosbusch.lumPi.gui.window.swing.impl.HostFrame;
import org.moosbusch.lumPi.task.spi.AbstractApplicationTask;
import org.osgi.framework.BundleActivator;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractLumPiApplication extends Application.Adapter
    implements LumPiApplication<LumPiApplicationContext> {
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
    public final boolean isOSGiAware() {
        return ArrayUtils.isNotEmpty(getBundleActivators()) || ArrayUtils.isNotEmpty(getServiceNames());
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
    public final LumPiApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public OSGiController getOSGiController() {
        return getApplicationContext();
    }

    @Override
    public BundleActivator[] getBundleActivators() {
        return null;
    }

    @Override
    public String[] getServiceNames() {
        return null;
    }

    @Override
    public final void startup(Display display, Map<String, String> properties) throws Exception {
        if (isOSGiAware()) {
            OSGiController controller = Objects.requireNonNull(getOSGiController());
            controller.startFramework(getBundleActivators());
        }
        new UILoaderTask(this).executeTask();
    }

    @Override
    public final boolean shutdown(boolean optional) {
        boolean result = false;
        closeApplicationContext();
        try {
            if (isOSGiAware()) {
                getOSGiController().stopFramework();
            }
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
    public Class<? extends PivotBeanConfiguration> getPivotBeanConfigurationClass() {
        return DefaultPivotBeanConfiguration.class;
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

    private static class UILoaderTask extends AbstractApplicationTask<BindableWindow>
        implements TaskListener<BindableWindow> {

        private final Reference<LumPiApplication<? extends LumPiApplicationContext>> appRef;

        public UILoaderTask(LumPiApplication<? extends LumPiApplicationContext> app) {
            this.appRef = new WeakReference<>(app);
            init();
        }

        private void init() {
            Container.setEventDispatchThreadChecker(null);
        }

        @Override
        public BindableWindow execute() throws TaskExecutionException {
            return Objects.requireNonNull(appRef.get().getApplicationWindow());
        }

        public void executeTask() {
            execute(this);
        }

        @Override
        public void taskExecuted(Task<BindableWindow> task) {
            BindableWindow result = Objects.requireNonNull(task.getResult());
            HostFrame hostFrame = appRef.get().getApplicationContext().getHostFrame();
            hostFrame.setWindow(result);
        }

        @Override
        public void executeFailed(Task<BindableWindow> task) {
        }
    }

    private static class PivotApplicationContext extends ApplicationContext {

        static {
            createTimer();
        }

    }

}
