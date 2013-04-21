/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.ColorChooser;
import org.apache.pivot.wtk.ColorChooserButton;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractColorChooserButtonFormEditorBindMapping
        <T extends ColorChooserButton>
        extends AbstractButtonFormEditorBindMapping<T> implements
        ColorChooser.SelectedColorBindMapping {

    public AbstractColorChooserButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    @Override
    protected final void bindButton(T editorComponent) {
        editorComponent.setSelectedColorBindType(getBindType());
        editorComponent.setSelectedColorBindMapping(this);
        ColorChooserButton(editorComponent);
    }

    protected void ColorChooserButton(T editorComponent) {
    }

}
