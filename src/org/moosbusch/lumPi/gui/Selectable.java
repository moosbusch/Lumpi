/*

 *
 */
package org.moosbusch.lumPi.gui;

import org.moosbusch.lumPi.gui.component.ValueHolder;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public interface Selectable<T extends Object> extends ValueHolder<T> {

    public static final String SELECTION_PROPERTY = "selection";

    public Selection<T> getSelection();

    public void setSelection(Selection<T> selItem);

    public static class Adapter<T extends Object> extends ValueHolder.Adapter<T>
            implements Selectable<T> {

        private Selection<T> selItem;

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


    }
}
