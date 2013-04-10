/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.ImageView;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractImageViewFormEditorBindMapping<T extends ImageView>
        extends AbstractFormEditorBindMapping<T>
        implements ImageView.ImageBindMapping {

    public AbstractImageViewFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindImageView(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setImageBindType(getBindType());
        editorComponent.setImageBindMapping(this);
        bindImageView(editorComponent);
    }

}
