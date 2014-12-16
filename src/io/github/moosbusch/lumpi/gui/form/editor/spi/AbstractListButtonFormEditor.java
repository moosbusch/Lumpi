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
package io.github.moosbusch.lumpi.gui.form.editor.spi;

import org.apache.pivot.wtk.ListButton;
import io.github.moosbusch.lumpi.gui.form.editor.io.StoreValueDelegate;

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
