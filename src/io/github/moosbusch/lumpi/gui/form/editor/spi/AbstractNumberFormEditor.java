/*
 Copyright 2013 Gunnar Kappei

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package io.github.moosbusch.lumpi.gui.form.editor.spi;

import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.TextInputContentListener;
import org.apache.pivot.wtk.validation.Validator;
import io.github.moosbusch.lumpi.gui.form.editor.validator.FormValidator;
import io.github.moosbusch.lumpi.gui.form.spi.AbstractDynamicForm;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractNumberFormEditor<V extends FormValidator>
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
            if (result instanceof FormValidator) {
                return (V) result;
            }
        }

        return null;
    }

    private class TextInputContentListenerImpl extends TextInputContentListener.Adapter {

        @Override
        public Vote previewInsertText(TextInput textInput, CharSequence text, int index) {
            String oldText = textInput.getText();
            String newText = new StringBuilder(oldText).insert(index, text).toString();

            if (textInput.getValidator().isValid(newText)) {
                return Vote.APPROVE;
            }

            return Vote.DENY;
        }
    }
}
