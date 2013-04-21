/*

 *
 */
package org.moosbusch.lumPi.gui.component;

import org.apache.pivot.beans.Bindable;

/**
 *
 * @author moosbusch
 */
public interface Submitable<T extends Object> extends ValueHolder<T>, Bindable {

    public void submit();

    public void cancel();

    public boolean canSubmit(final T value);

    public boolean isCanceled();

    public void setCanceled(boolean canceled);

}
