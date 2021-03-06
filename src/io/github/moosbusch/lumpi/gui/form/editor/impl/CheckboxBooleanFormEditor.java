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
package io.github.moosbusch.lumpi.gui.form.editor.impl;

import org.apache.pivot.wtk.Checkbox;
import io.github.moosbusch.lumpi.gui.form.editor.binding.FormEditorBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.binding.impl.DefaultButtonFormEditorBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.spi.AbstractFormEditor;

/**
 *
 * @author moosbusch
 */
public class CheckboxBooleanFormEditor extends AbstractFormEditor<Checkbox> {

    @Override
    protected Checkbox createComponent() {
        return new Checkbox();
    }

    @Override
    public String getDataBindingKeyPropertyName() {
        return SELECTED_KEY_PROPERTY;
    }

    @Override
    public String getValueAsText() {
        return getValue().toString();
    }

    @Override
    protected FormEditorBindMapping<Checkbox> createBindMapping() {
        return new DefaultButtonFormEditorBindMapping<>(this);
    }

}
