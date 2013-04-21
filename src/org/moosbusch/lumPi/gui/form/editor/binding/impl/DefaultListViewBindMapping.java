/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.impl;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.ListView;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.editor.binding.spi.AbstractListViewFormEditorBindMapping;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public class DefaultListViewBindMapping extends AbstractListViewFormEditorBindMapping<ListView> {

    public DefaultListViewBindMapping(FormEditor<ListView> editor) {
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
