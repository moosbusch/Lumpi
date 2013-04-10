/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.ColorChooser;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractColorChooserFormEditorBindMapping<T extends ColorChooser>
        extends AbstractFormEditorBindMapping<T> implements
        ColorChooser.SelectedColorBindMapping {

    public AbstractColorChooserFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindColorChooser(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setSelectedColorBindType(getBindType());
        editorComponent.setSelectedColorBindMapping(this);
        bindColorChooser(editorComponent);
    }

}
