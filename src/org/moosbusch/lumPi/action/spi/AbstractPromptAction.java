/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import java.util.Objects;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Window;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitablePrompt;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractPromptAction<T extends AbstractSubmitablePrompt<?>>
        extends AbstractChildWindowAction implements SheetCloseListener {

    private T prompt;

    public abstract void onPromptShowing(T sheet);

    public abstract void onPromptSubmitted(T sheet);

    public abstract void onPromptCanceled(T sheet);

    public T getPrompt() {
        return prompt;
    }

    public void setPrompt(T prompt) {
        this.prompt = prompt;
    }

    public final void perform() {
        perform(null);
    }

    @Override
    @SuppressWarnings({"unchecked", "unchecked"})
    public final void perform(Component source) {
        AbstractSubmitablePrompt<?> sht = Objects.requireNonNull(getPrompt());
        Window win = Objects.requireNonNull(getApplicationWindow());
        onPromptShowing((T) sht);
        sht.open(win, this);
    }

    @Override
    public final void sheetClosed(Sheet sheet) {
        @SuppressWarnings("unchecked")
        T sht = (T) sheet;

        if (sht.isCanceled()) {
            onPromptCanceled(sht);
        } else {
            onPromptSubmitted(sht);
        }
    }

}
