/*

 *
 */
package org.moosbusch.lumPi.gui.dialog.spi;

import java.net.URL;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Dialog;
import org.moosbusch.lumPi.gui.component.Submitable;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitableDialog<T extends Object> extends Dialog
        implements Submitable<T> {

    private final BeanMonitor monitor;
    private boolean canceled = false;
    private T value = null;

    public AbstractSubmitableDialog(String title, Component content, boolean modal) {
        super(title, content, modal);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog(String title, Component content) {
        super(title, content);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog(Component content, boolean modal) {
        super(content, modal);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog(Component content) {
        super(content);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog(String title, boolean modal) {
        super(title, modal);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog(String title) {
        super(title);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog(boolean modal) {
        super(modal);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitableDialog() {
        this.monitor = new BeanMonitor(this);
    }

    protected abstract void onCancel();

    protected abstract void onSubmit(T value);

    @Override
    public BeanMonitor getMonitor() {
        return monitor;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T val) {
        this.value = val;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public final void cancel() {
        setCanceled(true);
        onCancel();
        close();
    }

    @Override
    public final void submit() {
        final T val = getValue();

        if (canSubmit(val)) {
            setCanceled(false);
            onSubmit(val);
            close();
        }
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }

}
