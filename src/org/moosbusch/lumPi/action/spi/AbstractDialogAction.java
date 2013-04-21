/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import java.util.Objects;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.DialogCloseListener;
import org.apache.pivot.wtk.HorizontalAlignment;
import org.apache.pivot.wtk.VerticalAlignment;
import org.apache.pivot.wtk.Window;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableDialog;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractDialogAction<T extends AbstractSubmitableDialog<?>>
    extends AbstractChildWindowAction implements DialogCloseListener {

    private T dialog;

    public abstract void onDialogShowing(T dialog);

    public abstract void onDialogSubmitted(T dialog, boolean modal);

    public abstract void onDialogCanceled(T dialog, boolean modal);

    public T getDialog() {
        return dialog;
    }

    public void setDialog(T dialog) {
        this.dialog = dialog;
    }

    public final void perform() {
        perform(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void perform(Component source) {
        AbstractSubmitableDialog<?> dlg = Objects.requireNonNull(getDialog());
        Window win = Objects.requireNonNull(getApplicationWindow());
        onDialogShowing((T) dlg);
        dlg.open(win, this);
        dlg.align(win.getBounds(), HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    }

    @Override
    public final void dialogClosed(Dialog dialog, boolean modal) {
        @SuppressWarnings("unchecked")
        T dlg = (T) dialog;

        if (dlg.isCanceled()) {
            onDialogCanceled(dlg, modal);
        } else {
            onDialogSubmitted(dlg, modal);
        }
    }
}
