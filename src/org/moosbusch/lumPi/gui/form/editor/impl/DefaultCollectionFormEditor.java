/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.impl;

import org.apache.pivot.wtk.ListButton;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultListButtonBindMapping;
import org.moosbusch.lumPi.gui.form.editor.io.StoreValueDelegate;
import org.moosbusch.lumPi.gui.form.editor.io.spi.ListButtonStoreValueDelegate;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractListButtonFormEditor;

/**
 *
 * @author moosbusch
 */
public class DefaultCollectionFormEditor extends AbstractListButtonFormEditor<ListButton> {

    @Override
    protected ListButton createComponent() {
        return new ListButton();
    }

    @Override
    protected StoreValueDelegate<ListButton> createStoreValueDelegate() {
        return new ListButtonStoreValueDelegate(this);
    }

    @Override
    protected FormEditorBindMapping<ListButton> createBindMapping() {
        return new DefaultListButtonBindMapping<>(this);
    }
}
