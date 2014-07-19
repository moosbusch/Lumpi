/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.action;

import java.io.File;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface FileBrowserAction<T extends BindableWindow,
        V extends AbstractSubmitableFileBrowserSheet>
        extends SheetAction<T, V> {

    public void processFiles(FileBrowserSheet.Mode sheetMode, List<File> files);

}
