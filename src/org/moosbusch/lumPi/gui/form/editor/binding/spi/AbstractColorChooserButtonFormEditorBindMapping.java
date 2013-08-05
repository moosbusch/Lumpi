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

import org.apache.pivot.wtk.ColorChooser;
import org.apache.pivot.wtk.ColorChooserButton;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractColorChooserButtonFormEditorBindMapping
        <T extends ColorChooserButton>
        extends AbstractButtonFormEditorBindMapping<T> implements
        ColorChooser.SelectedColorBindMapping {

    public AbstractColorChooserButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    @Override
    protected final void bindButton(T editorComponent) {
        editorComponent.setSelectedColorBindType(getBindType());
        editorComponent.setSelectedColorBindMapping(this);
        ColorChooserButton(editorComponent);
    }

    protected void ColorChooserButton(T editorComponent) {
    }

}
