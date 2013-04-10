/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTextInputFormEditorBindMapping<T extends TextInput>
        extends AbstractFormEditorBindMapping<T>
        implements TextInput.TextBindMapping {

    public AbstractTextInputFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindTextInput(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setTextBindType(getBindType());
        editorComponent.setTextBindMapping(this);
        bindTextInput(editorComponent);
    }
}
