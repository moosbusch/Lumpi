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
package io.github.moosbusch.lumpi.gui.form.editor.binding.impl;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.Spinner;
import io.github.moosbusch.lumpi.gui.form.editor.binding.spi.AbstractSpinnerFormEditorBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.FormEditor;
import io.github.moosbusch.lumpi.util.LumpiUtil;

/**
 *
 * @author moosbusch
 */
public class DefaultSpinnerDataBindMapping extends AbstractSpinnerFormEditorBindMapping<Spinner> {

    public DefaultSpinnerDataBindMapping(FormEditor<Spinner> editor) {
        super(editor);
    }

    @Override
    public List<?> toSpinnerData(Object value) {
        return LumpiUtil.toListData(value);
    }

    @Override
    public Object valueOf(List<?> listData) {
        return getEditor().getComponent().getSelectedItem();
    }

}
