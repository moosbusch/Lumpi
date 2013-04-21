/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.impl;

import org.apache.pivot.wtk.Checkbox;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultButtonFormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractFormEditor;

/**
 *
 * @author moosbusch
 */
public class CheckboxBooleanFormEditor extends AbstractFormEditor<Checkbox> {

    @Override
    protected Checkbox createComponent() {
        return new Checkbox();
    }

    @Override
    public String getDataBindingKeyPropertyName() {
        return SELECTED_KEY_PROPERTY;
    }

    @Override
    public String getValueAsText() {
        return getValue().toString();
    }

    @Override
    protected FormEditorBindMapping<Checkbox> createBindMapping() {
        return new DefaultButtonFormEditorBindMapping<>(this);
    }

}
