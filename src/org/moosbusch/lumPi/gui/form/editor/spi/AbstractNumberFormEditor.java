/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.spi;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.TextInputContentListener;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.gui.form.editor.validator.spi.AbstractNumberValidator;
import org.moosbusch.lumPi.gui.form.spi.AbstractDynamicForm;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractNumberFormEditor<T extends Number, V extends AbstractNumberValidator<T>>
        extends AbstractTextInputFormEditor<TextInput> {

    @Override
    protected TextInput createComponent() {
        TextInput result = new TextInput();
        result.getTextInputContentListeners().add(
                new TextInputContentListenerImpl());
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V getValidator(AbstractDynamicForm<? extends Object> form) {
        Validator result = super.getValidator(form);

        if (result != null) {
            if (result instanceof AbstractNumberValidator<?>) {
                return (V) result;
            }
        }

        return null;
    }

    private class TextInputContentListenerImpl extends TextInputContentListener.Adapter {

        @Override
        public Vote previewInsertText(TextInput textInput, CharSequence text, int index) {
            if (StringUtils.isNotBlank(text)) {
                if (textInput.getValidator().isValid(text.toString())) {
                    return Vote.APPROVE;
                }
            }

            return Vote.DENY;
        }
    }
}
