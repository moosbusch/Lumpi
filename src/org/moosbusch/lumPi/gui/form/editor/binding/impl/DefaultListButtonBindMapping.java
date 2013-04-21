/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.impl;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.ListButton;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.editor.binding.spi.AbstractListButtonFormEditorBindMapping;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public class DefaultListButtonBindMapping<T extends ListButton> extends
        AbstractListButtonFormEditorBindMapping<T> {

    public DefaultListButtonBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    @Override
    public List<?> toListData(Object value) {
        return PivotUtil.toListData(value);
    }

    @Override
    public Object valueOf(List<?> listData) {
        return getEditor().getComponent().getSelectedItem();
    }

}
