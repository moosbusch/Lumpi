/*
Copyright 2013 Gunnar Kappei

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
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
