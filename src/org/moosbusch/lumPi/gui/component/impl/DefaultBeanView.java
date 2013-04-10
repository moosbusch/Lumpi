/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.component.impl;

import org.moosbusch.lumPi.gui.component.spi.AbstractBeanView;
import org.moosbusch.lumPi.gui.form.impl.DefaultBeanViewForm;
import org.moosbusch.lumPi.gui.form.spi.AbstractBeanViewForm;
import org.moosbusch.lumPi.gui.tree.BeanViewTreeView;

/**
 *
 * @author moosbusch
 */
public class DefaultBeanView extends AbstractBeanView<BeanViewTreeView, AbstractBeanViewForm<Object>> {

    @Override
    protected BeanViewTreeView createBeanTreeView() {
        return new BeanViewTreeView(this);
    }

    @Override
    protected AbstractBeanViewForm<Object> createBeanForm() {
        return new DefaultBeanViewForm(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V createBeanInstance(Class<?> type) throws Exception {
        return (V) Class.forName(type.getName()).newInstance();
    }
}