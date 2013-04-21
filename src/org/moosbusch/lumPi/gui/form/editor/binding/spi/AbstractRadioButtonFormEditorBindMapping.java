/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.RadioButton;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractRadioButtonFormEditorBindMapping<T extends RadioButton>
        extends AbstractButtonFormEditorBindMapping<T> {

    public AbstractRadioButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }
}
