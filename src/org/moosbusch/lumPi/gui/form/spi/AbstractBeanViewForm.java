/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.spi;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.PropertyChangeListener;
import org.moosbusch.lumPi.gui.Selection;
import org.moosbusch.lumPi.gui.component.BeanViewComponent;
import org.moosbusch.lumPi.gui.component.spi.AbstractBeanView;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractBeanViewForm<T extends Object>
    extends AbstractDynamicForm<T> implements BeanViewComponent<T> {

    private final AbstractBeanView<?, ?> beanView;
    private Selection<T> selItem;

    public AbstractBeanViewForm(AbstractBeanView<?, ?> beanView) {
        this.beanView = beanView;
        init();
    }

    private void init() {
        getMonitor().getPropertyChangeListeners().add(
                new PropertyChangeListenerImpl());
    }

    @Override
    public AbstractBeanView<?, ?> getBeanView() {
        return beanView;
    }

    @Override
    protected final boolean isExcludedProperty(Class<?> beanClass, String propertyName) {
        return getBeanView().isExcludedProperty(beanClass, propertyName);
    }

    @Override
    public Selection<T> getSelection() {
        return selItem;
    }

    @Override
    public void setSelection(Selection<T> selItem) {
        this.selItem = selItem;
        PivotUtil.firePropertyChangeListeners(this,
                SELECTION_PROPERTY, getMonitor());
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (StringUtils.equalsIgnoreCase(propertyName, SELECTION_PROPERTY)) {
                Selection<T> sel = getSelection();

                if (sel != null) {
                    try {
                        setValue(sel.evaluate());
                    } catch (Exception ex) {
                        Logger.getLogger(AbstractBeanViewForm.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
