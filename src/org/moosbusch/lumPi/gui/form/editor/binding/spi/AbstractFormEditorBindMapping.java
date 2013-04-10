/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.BindType;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractFormEditorBindMapping<T extends Component>
        implements FormEditorBindMapping<T> {

    private final FormEditor<T> editor;
    private final BindType bindType;

    public AbstractFormEditorBindMapping(FormEditor<T> editor, BindType bindType) {
        this.editor = editor;
        this.bindType = bindType;
    }

    public AbstractFormEditorBindMapping(FormEditor<T> editor) {
        this(editor, BindType.BOTH);
    }

    @Override
    public FormEditor<T> getEditor() {
        return editor;
    }

    @Override
    public BindType getBindType() {
        return bindType;
    }

}
