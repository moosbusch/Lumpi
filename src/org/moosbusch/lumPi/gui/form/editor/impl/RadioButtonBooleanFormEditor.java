/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.impl;

import org.apache.pivot.wtk.RadioButton;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultButtonFormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractFormEditor;

/**
 *
 * @author moosbusch
 */
public class RadioButtonBooleanFormEditor extends AbstractFormEditor<RadioButton> {

    @Override
    protected RadioButton createComponent() {
        return new RadioButton();
    }

    @Override
    public String getDataBindingKeyPropertyName() {
        return SELECTED_KEY_PROPERTY;
    }

    @Override
    protected FormEditorBindMapping<RadioButton> createBindMapping() {
        return new DefaultButtonFormEditorBindMapping<>(this);
    }

}