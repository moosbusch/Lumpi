/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.TableView;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTableViewFormEditorBindMapping<T extends TableView>
        extends AbstractFormEditorBindMapping<T>
        implements TableView.TableDataBindMapping, TableView.SelectedRowBindMapping {

    public AbstractTableViewFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindTableView(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setTableDataBindType(getBindType());
        editorComponent.setSelectedRowBindType(getBindType());
        editorComponent.setSelectedRowsBindType(getBindType());
        editorComponent.setTableDataBindMapping(this);
        editorComponent.setSelectedRowBindMapping(this);
        editorComponent.setSelectedRowsBindMapping(this);
        bindTableView(editorComponent);
    }
}
