/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding;

import org.apache.pivot.wtk.BindType;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public interface FormEditorBindMapping<T extends Component> {

    public FormEditor<T> getEditor();

    public BindType getBindType();

    public void bind(T editorComponent);

}
