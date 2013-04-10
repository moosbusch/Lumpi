/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.Calendar;
import org.apache.pivot.wtk.CalendarButton;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractCalendarButtonFormEditorBindMapping<T extends CalendarButton>
        extends AbstractButtonFormEditorBindMapping<T>
        implements Calendar.SelectedDateBindMapping {

    public AbstractCalendarButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    @Override
    protected final void bindButton(T editorComponent) {
        super.bindButton(editorComponent);
        editorComponent.setSelectedDateBindType(getBindType());
        editorComponent.setSelectedDateBindMapping(this);
        bindCalendarButton(editorComponent);
    }

    protected void bindCalendarButton(T editorComponent) {
    }

}
