/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.impl;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.editor.binding.spi.AbstractTextInputFormEditorBindMapping;

/**
 *
 * @author moosbusch
 */
public class DefaultTextInputBindMapping<T extends TextInput>
        extends AbstractTextInputFormEditorBindMapping<T> {

    public DefaultTextInputBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    @Override
    public String toString(Object value) {
        if (value != null) {
            return value.toString();
        }

        return new String();
    }

    @Override
    public Object valueOf(String text) {
        if (text != null) {
            return text;
        }

        return new String();
    }
}
