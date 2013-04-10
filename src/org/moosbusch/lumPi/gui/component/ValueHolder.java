/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.component;

import org.moosbusch.lumPi.gui.Monitorable;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public interface ValueHolder<T extends Object> extends Monitorable {

    public static final String VALUE_PROPERTY = "value";

    public T getValue();

    public void setValue(T val);

    public abstract class Adapter<T extends Object> extends Monitorable.Adapter
    implements ValueHolder<T> {

        private T value;

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public final void setValue(T value) {
            this.value = value;
            PivotUtil.firePropertyChangeListeners(
                    this, VALUE_PROPERTY, getMonitor());
        }
    }
}
