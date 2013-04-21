/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.spi;

import org.apache.pivot.wtk.ListButton;
import org.moosbusch.lumPi.gui.form.editor.io.StoreValueDelegate;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractListButtonFormEditor<T extends ListButton>
        extends AbstractFormEditor<T> {

    @Override
    protected abstract StoreValueDelegate<T> createStoreValueDelegate();

    @Override
    public String getDataBindingKeyPropertyName() {
        return LIST_DATA_KEY_PROPERTY;
    }
}
