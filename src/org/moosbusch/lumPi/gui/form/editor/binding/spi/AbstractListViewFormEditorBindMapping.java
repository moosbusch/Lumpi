/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.ListView;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractListViewFormEditorBindMapping<T extends ListView>
        extends AbstractFormEditorBindMapping<T>
        implements ListView.ListDataBindMapping, ListView.ItemBindMapping {

    public AbstractListViewFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindListView(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setListDataBindType(getBindType());
        editorComponent.setSelectedItemBindType(getBindType());
        editorComponent.setSelectedItemsBindType(getBindType());
        editorComponent.setListDataBindMapping(this);
        editorComponent.setSelectedItemBindMapping(this);
        editorComponent.setSelectedItemsBindMapping(this);
        bindListView(editorComponent);
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
