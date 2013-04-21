/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.moosbusch.lumPi.action.spi.LabelableAction;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractQuitAction extends LabelableAction {

    protected abstract boolean canExit();

    @Override
    public void perform(Component source) {
        if (canExit()) {
        DesktopApplicationContext.exit();
        }
    }

}
