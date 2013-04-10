/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.component;

import org.moosbusch.lumPi.gui.Selectable;
import org.moosbusch.lumPi.gui.component.spi.AbstractBeanView;

/**
 *
 * @author moosbusch
 */
public interface BeanViewComponent<T extends Object> extends ValueHolder<T>,
        Selectable<T> {

    public AbstractBeanView<?, ?> getBeanView();

}
