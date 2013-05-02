/*

 *
 */
package org.moosbusch.lumPi.application;

import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import java.net.URL;
import java.util.prefs.Preferences;
import org.apache.pivot.beans.BXMLSerializer;
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

    public BXMLSerializer getSerializer();

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
        protected BXMLSerializer createSerializer() {
            return new BXMLSerializer();
        }

        @Override
        public Resources getResources() {
            return null;
        }

    }
}
