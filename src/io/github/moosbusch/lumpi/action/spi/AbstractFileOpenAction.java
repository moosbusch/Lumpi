/*

 *
 */
package io.github.moosbusch.lumpi.action.spi;

import org.apache.pivot.wtk.FileBrowserSheet;
import io.github.moosbusch.lumpi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractFileOpenAction<T extends BindableWindow, V extends AbstractSubmitableFileBrowserSheet>
        extends AbstractFileBrowserAction<T, V> {

    public AbstractFileOpenAction() {
        super(FileBrowserSheet.Mode.OPEN);
    }

    @Override
    public void onChildWindowShowing(T applicationWindow, V childWindow) {
        childWindow.clearSelection();
        childWindow.setVisible(true);
    }
}
