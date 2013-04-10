/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.action.spi;

import java.util.Objects;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableSheet;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSheetAction<T extends AbstractSubmitableSheet<?>>
        extends AbstractChildWindowAction implements SheetCloseListener {

    private T sheet;

    public abstract void onSheetShowing(T sheet);

    public abstract void onSheetSubmitted(T sheet);

    public abstract void onSheetCanceled(T sheet);

    public T getSheet() {
        return sheet;
    }

    public void setSheet(T sheet) {
        this.sheet = sheet;
    }

    public final void perform() {
        perform(null);
    }

    @Override
    @SuppressWarnings({"unchecked", "unchecked"})
    public final void perform(Component source) {
        AbstractSubmitableSheet<?> sht = Objects.requireNonNull(getSheet());
        BindableWindow win = Objects.requireNonNull(getApplicationWindow());
        onSheetShowing((T) sht);
        sht.open(win, this);
    }

    @Override
    public final void sheetClosed(Sheet sheet) {
        @SuppressWarnings("unchecked")
        T sht = (T) sheet;

        if (sht.isCanceled()) {
            onSheetCanceled(sht);
        } else {
            onSheetSubmitted(sht);
        }
    }


}