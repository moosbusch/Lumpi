/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.action.spi;

import java.io.File;
import org.apache.pivot.collections.List;
import org.moosbusch.lumPi.action.SheetAction;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface FileBrowserAction<T extends BindableWindow, V extends AbstractSubmitableFileBrowserSheet>
        extends SheetAction<T, V> {

    public void processFiles(List<File> files);

    public static abstract class Adapter<T extends BindableWindow, V extends AbstractSubmitableFileBrowserSheet>
            extends SheetAction.Adapter<T, V> implements FileBrowserAction<T, V> {

        @Override
        public final void onChildWindowSubmitted(T applicationWindow, V sheet) {
            processFiles(sheet.getSelectedFiles());
        }
    }
}
