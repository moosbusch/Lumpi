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
package io.github.moosbusch.lumpi.gui.form.editor.binding.spi;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.BindType;
import org.apache.pivot.wtk.Spinner;
import io.github.moosbusch.lumpi.gui.form.editor.FormEditor;

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
