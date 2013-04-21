/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.impl;

import org.apache.pivot.wtk.Button;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.editor.binding.spi.AbstractButtonFormEditorBindMapping;

/**
 *
 * @author moosbusch
 */
public class DefaultButtonFormEditorBindMapping<T extends Button>
        extends AbstractButtonFormEditorBindMapping<T> {

    public DefaultButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }
}
