/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListView;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractListButtonFormEditorBindMapping<T extends ListButton>
        extends AbstractButtonFormEditorBindMapping<T>
        implements ListView.ListDataBindMapping, ListView.ItemBindMapping {

    public AbstractListButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    @Override
    protected final void bindButton(T editorComponent) {
        editorComponent.setListDataBindType(getBindType());
        editorComponent.setSelectedItemBindType(getBindType());
        editorComponent.setListDataBindMapping(this);
        editorComponent.setSelectedItemBindMapping(this);
        bindListButton(editorComponent);
    }

    protected void bindListButton(T editorComponent) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<?> toListData(Object value) {
        return PivotUtil.toListData(value);
    }

    @Override
    public Object valueOf(List<?> listData) {
        return getEditor().getComponent().getSelectedItem();
    }

    @Override
    public int indexOf(List<?> listData, Object value) {
        return PivotUtil.indexOf(listData, value);
    }

    @Override
    public Object get(List<?> listData, int index) {
        return PivotUtil.get(listData, index);
    }
}
