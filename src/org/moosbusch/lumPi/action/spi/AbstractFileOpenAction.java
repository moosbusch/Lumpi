/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import org.apache.pivot.wtk.FileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractFileOpenAction<T extends BindableWindow, V extends AbstractSubmitableFileBrowserSheet>
        extends FileBrowserAction.Adapter<T, V> {

    @Override
    public void onChildWindowShowing(T applicationWindow, V childWindow) {
        childWindow.setMode(FileBrowserSheet.Mode.OPEN);
        childWindow.clearSelection();
        childWindow.setVisible(true);
    }
}
