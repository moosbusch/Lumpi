/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.pivot.wtk.Calendar;
import org.moosbusch.lumPi.gui.form.editor.binding.spi.AbstractFormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractCalendarFormEditorBindMapping<T extends Calendar>
        extends AbstractFormEditorBindMapping<T>
        implements Calendar.SelectedDateBindMapping {

    public AbstractCalendarFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindCalendar(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setSelectedDateBindType(getBindType());
        editorComponent.setSelectedDateBindMapping(this);
        bindCalendar(editorComponent);
    }
}
