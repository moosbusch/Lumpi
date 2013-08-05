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

import org.apache.pivot.wtk.BindType;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractFormEditorBindMapping<T extends Component>
        implements FormEditorBindMapping<T> {

    private final FormEditor<T> editor;
    private final BindType bindType;

    public AbstractFormEditorBindMapping(FormEditor<T> editor, BindType bindType) {
        this.editor = editor;
        this.bindType = bindType;
    }

    public AbstractFormEditorBindMapping(FormEditor<T> editor) {
        this(editor, BindType.BOTH);
    }

    @Override
    public FormEditor<T> getEditor() {
        return editor;
    }

    @Override
    public BindType getBindType() {
        return bindType;
    }

}
