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
package org.moosbusch.lumPi.gui.form.editor.impl;

import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultTextInputBindMapping;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractTextInputFormEditor;

/**
 *
 * @author moosbusch
 */
public class DefaultCharacterFormEditor extends AbstractTextInputFormEditor<TextInput> {

    @Override
    protected TextInput createComponent() {
        TextInput result = new TextInput();
        result.setMaximumLength(1);
        return result;
    }

    @Override
    protected FormEditorBindMapping<TextInput> createBindMapping() {
        return new DefaultTextInputBindMapping<>(this);
    }

}
