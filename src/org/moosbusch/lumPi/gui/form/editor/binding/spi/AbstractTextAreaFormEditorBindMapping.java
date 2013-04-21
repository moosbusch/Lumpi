/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.TextArea;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTextAreaFormEditorBindMapping<T extends TextArea>
        extends AbstractFormEditorBindMapping<T>
        implements TextArea.TextBindMapping {

    public AbstractTextAreaFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindTextArea(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setTextBindType(getBindType());
        editorComponent.setTextBindMapping(this);
        bindTextArea(editorComponent);
    }
}