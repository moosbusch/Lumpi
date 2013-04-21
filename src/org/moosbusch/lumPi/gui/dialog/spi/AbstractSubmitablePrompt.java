/*

 *
 */
package org.moosbusch.lumPi.gui.dialog.spi;

import java.net.URL;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.moosbusch.lumPi.gui.component.Submitable;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitablePrompt<T extends Object> extends Prompt
        implements Submitable<T> {

    private final BeanMonitor monitor;
    private T value = null;
    private boolean canceled = true;

    public AbstractSubmitablePrompt() {
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitablePrompt(String message) {
        super(message);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitablePrompt(MessageType messageType, String message,
            Sequence<?> options) {
        super(messageType, message, options);
        this.monitor = new BeanMonitor(this);
    }

    public AbstractSubmitablePrompt(MessageType messageType, String message,
            Sequence<?> options, Component body) {
        super(messageType, message, options, body);
        this.monitor = new BeanMonitor(this);
    }

    protected abstract void onCancel();

    protected abstract void onSubmit(T value);

    @Override
    public BeanMonitor getMonitor() {
        return monitor;
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
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T val) {
        this.value = val;
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
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
}
