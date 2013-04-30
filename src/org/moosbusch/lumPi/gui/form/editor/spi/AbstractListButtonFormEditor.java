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

//    private final MenuHandler menuHandler;

    public AbstractListButtonFormEditor() {
//        this.menuHandler = initMenuHandler();
//        init();
    }

//    private void init() {
//        getComponent().setMenuHandler(getMenuHandler());
//    }

//    private MenuHandler initMenuHandler() {
//        return createMenuHandler();
//    }

//    protected abstract MenuHandler createMenuHandler();

    @Override
    protected abstract StoreValueDelegate<T> createStoreValueDelegate();

//    public MenuHandler getMenuHandler() {
//        return menuHandler;
//    }

    @Override
    public String getDataBindingKeyPropertyName() {
        return LIST_DATA_KEY_PROPERTY;
    }
}
