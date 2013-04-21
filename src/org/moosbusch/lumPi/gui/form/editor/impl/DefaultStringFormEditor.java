/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.impl;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractTextInputFormEditor;

/**
 *
 * @author moosbusch
 */
public class DefaultStringFormEditor extends AbstractTextInputFormEditor<TextInput> {

    @Override
    protected TextInput createComponent() {
        return new TextInput();
    }

}
