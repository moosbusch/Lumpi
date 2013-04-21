/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.Label;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractLabelFormEditorBindMapping<T extends Label>
        extends AbstractFormEditorBindMapping<T>
        implements Label.TextBindMapping {

    public AbstractLabelFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindLabel(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setTextBindType(getBindType());
        editorComponent.setTextBindMapping(this);
        bindLabel(editorComponent);
    }
}
