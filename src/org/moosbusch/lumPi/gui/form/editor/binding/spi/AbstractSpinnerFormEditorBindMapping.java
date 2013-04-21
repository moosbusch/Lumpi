/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.BindType;
import org.apache.pivot.wtk.Spinner;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSpinnerFormEditorBindMapping<T extends Spinner>
        extends AbstractFormEditorBindMapping<T>
        implements Spinner.SpinnerDataBindMapping, Spinner.ItemBindMapping {

    public AbstractSpinnerFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindSpinner(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setSpinnerDataBindType(BindType.BOTH);
        editorComponent.setSelectedItemBindType(BindType.BOTH);
        editorComponent.setSelectedItemBindMapping(this);
        editorComponent.setSpinnerDataBindMapping(this);
        bindSpinner(editorComponent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int indexOf(List<?> spinnerData, Object value) {
        if (spinnerData != null) {
            List<Object> data = (List<Object>) spinnerData;

            return data.indexOf(value);
        }

        return -1;
    }

    @SuppressWarnings("unchecked")
            @Override
    public Object get(List<?> spinnerData, int index) {
        if (spinnerData != null) {
            List<Object> data = (List<Object>) spinnerData;

            if ((index >= 0) && (index < spinnerData.getLength())) {
                return data.get(index);
            }
        }

        return null;
    }
}
