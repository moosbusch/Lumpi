/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.impl;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultTextInputBindMapping;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractTextInputFormEditor;

/**
 *
 * @author moosbusch
 */
public class DefaultCharacterFormEditor extends AbstractTextInputFormEditor<TextInput> {

    @Override
    protected TextInput createComponent() {
        TextInput result = new TextInput();
        result.setMaximumLength(1);
        return result;
    }

    @Override
    protected FormEditorBindMapping<TextInput> createBindMapping() {
        return new DefaultTextInputBindMapping<>(this);
    }

}
