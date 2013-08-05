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
package org.moosbusch.lumPi.gui.form.impl;

import java.net.URL;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.spi.AbstractDynamicForm;

/**
 *
 * @author moosbusch
 */
public class DefaultDynamicForm extends AbstractDynamicForm<Object> {

    @Override
    public void onCancel() {
    }

    @Override
    public void onSubmit(Object value) {
    }

    @Override
    public boolean canSubmit(Object value) {
        for (String formField : getEditors()) {
            FormEditor<? extends Component> editor = getEditors().get(formField);
            if (!editor.isValid(this)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }
}
