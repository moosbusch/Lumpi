/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.moosbusch.lumpi.action;

import java.io.File;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.FileBrowserSheet;
import io.github.moosbusch.lumpi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface FileBrowserAction<T extends BindableWindow,
        V extends AbstractSubmitableFileBrowserSheet>
        extends SheetAction<T, V> {

    public void processFiles(FileBrowserSheet.Mode sheetMode, List<File> files);

}
