/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.spi;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultTextInputBindMapping;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTextInputFormEditor<T extends TextInput> extends AbstractFormEditor<T> {

    @Override
    protected FormEditorBindMapping<T> createBindMapping() {
        return new DefaultTextInputBindMapping<>(this);
    }

    @Override
    public String getDataBindingKeyPropertyName() {
        return TEXT_KEY_PROPERTY;
    }

}
