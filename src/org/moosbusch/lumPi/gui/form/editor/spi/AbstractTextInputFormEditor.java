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
package org.moosbusch.lumPi.gui.form.editor.spi;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultTextInputBindMapping;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTextInputFormEditor<T extends TextInput> extends AbstractFormEditor<T> {

    @Override
    protected FormEditorBindMapping<T> createBindMapping() {
        return new DefaultTextInputBindMapping<>(this);
    }

    @Override
    public String getDataBindingKeyPropertyName() {
        return TEXT_KEY_PROPERTY;
    }

}
