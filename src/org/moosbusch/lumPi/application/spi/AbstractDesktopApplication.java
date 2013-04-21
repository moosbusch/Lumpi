/*

 *
 */
package org.moosbusch.lumPi.application.spi;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.moosbusch.lumPi.application.DesktopApplication;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import org.moosbusch.lumPi.beans.spring.impl.PivotApplicationContextImpl;
import org.moosbusch.lumPi.beans.spring.impl.SpringAwareBXMLSerializer;
import org.moosbusch.lumPi.beans.spring.impl.PivotFactoryBean;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractDesktopApplication
        extends Application.Adapter implements DesktopApplication {

    private Display display = null;
    private final SpringAwareBXMLSerializer serializer;
    private final PivotApplicationContext context;
    private final HostWindowResizeListener windowListener;

    public AbstractDesktopApplication() {
        this.context = new PivotApplicationContextImpl(this);
        this.serializer = initSerializer();
        this.windowListener = new HostWindowResizeListener();
        init();
    }

    private SpringAwareBXMLSerializer initSerializer() {
        return Objects.requireNonNull(createSerializer());
    }

    private void init() {
        PivotFactoryBean pivotFactory =
                context.getBean(PivotFactoryBean.class);
        pivotFactory.setBxml(this.getBXMLConfiguration());
        pivotFactory.setResources(getResources());
        pivotFactory.setSerializer(getSerializer());
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
            PivotApplicationContext parentCtx = ctx.getParent();

            if (parentCtx != null) {
                if (parentCtx.isActive()) {
                    if (parentCtx.isRunning()) {
                        parentCtx.stop();
                    }
                }
            }

            if (ctx.isActive()) {
                if (ctx.isRunning()) {
                    ctx.stop();
                }
            }
        }
    }

    protected abstract SpringAwareBXMLSerializer createSerializer();

    protected abstract void startupImpl(Display display, Map<String, String> namespace)
            throws Exception;

    protected abstract boolean shutdownImpl(boolean optional);

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
    public final SpringAwareBXMLSerializer getSerializer() {
        return serializer;
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
            Logger.getLogger(AbstractDesktopApplication.class.getName()).log(
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
