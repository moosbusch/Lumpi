/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.Checkbox;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractCheckboxFormEditorBindMapping<T extends Checkbox>
        extends AbstractButtonFormEditorBindMapping<T> {

    public AbstractCheckboxFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }
}
