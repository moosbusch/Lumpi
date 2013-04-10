/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    protected void onCancel() {
    }

    @Override
    protected void onSubmit(Object value) {
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
