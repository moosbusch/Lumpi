/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import org.apache.pivot.wtk.FileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractOpenFileAction<T extends AbstractSubmitableFileBrowserSheet>
    extends AbstractSheetAction<T> {

    @Override
    public void onSheetShowing(T sheet) {
        sheet.setMode(FileBrowserSheet.Mode.OPEN);
        sheet.clearSelection();
        sheet.setVisible(true);
    }
}
