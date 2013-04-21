/*

 *
 */
package org.moosbusch.lumPi.gui.dialog.impl;

import java.io.File;
import org.apache.pivot.collections.Sequence;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;

/**
 *
 * @author moosbusch
 */
public class DefaultSubmitableFileBrowserSheet extends AbstractSubmitableFileBrowserSheet {

    @Override
    protected void onCancel() {
    }

    @Override
    protected void onSubmit(Sequence<File> value) {
    }

    @Override
    public boolean canSubmit(Sequence<File> value) {
        return ((getSelectedFiles() != null) || (getSelectedFile() != null));
    }

}
