/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.io;

import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 */
public interface StoreValueDelegate<T extends Component> {

    public FormEditor<T> getFormEditor();

    public void storeValue(Object context);

}
