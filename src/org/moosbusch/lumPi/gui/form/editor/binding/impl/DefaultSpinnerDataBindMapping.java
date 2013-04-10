/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.impl;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.Spinner;
import org.moosbusch.lumPi.gui.form.editor.binding.spi.AbstractSpinnerFormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.util.PivotUtil;

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
        return PivotUtil.toListData(value);
    }

    @Override
    public Object valueOf(List<?> listData) {
        return getEditor().getComponent().getSelectedItem();
    }

}
