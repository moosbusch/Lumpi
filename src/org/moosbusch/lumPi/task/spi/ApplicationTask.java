/*

 *
 */
package org.moosbusch.lumPi.task.spi;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.TaskAdapter;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class ApplicationTask<T extends Object> extends Task<T>
    implements TaskListener<T>, Bindable {

    private boolean forwardEventsToEDT = false;
    private final List<TaskListener<T>> listenerList;

    public ApplicationTask(ExecutorService executorService) {
        super(executorService);
        this.listenerList = new ArrayList<>();
    }

    public ApplicationTask() {
        this(true);
    }

    public ApplicationTask(ExecutorService executorService, boolean forwardEventsToEDT) {
        super(executorService);
        this.forwardEventsToEDT = forwardEventsToEDT;
        this.listenerList = new ArrayList<>();
    }

    public ApplicationTask(boolean forwardEventsToEDT) {
        this.forwardEventsToEDT = forwardEventsToEDT;
        this.listenerList = new ArrayList<>();
    }

    public abstract void taskExecutedImpl(final Task<T> task);

    public abstract void executeFailedImpl(final Task<T> task);


    public final List<TaskListener<T>> getListenerList() {
        synchronized(listenerList) {
            return listenerList;
        }
    }

    public final void runTask(ExecutorService executorService) {
        if (forwardEventsToEDT) {
            execute(new TaskAdapter<>(this), executorService);
        } else {
            execute(this, executorService);
        }
    }

    public final void runTask() {
        if (forwardEventsToEDT) {
            execute(new TaskAdapter<>(this));
        } else {
            execute(this);
        }
    }

    @Override
    public final void taskExecuted(final Task<T> task) {
        final List<TaskListener<T>> listeners = this.getListenerList();

        synchronized(listeners) {
            for (TaskListener<T> listener : listeners) {
                listener.taskExecuted(task);
            }

            taskExecutedImpl(task);
        }
    }

    @Override
    public final void executeFailed(final Task<T> task) {
        final List<TaskListener<T>> listeners = this.getListenerList();

        synchronized(listeners) {
            for (TaskListener<T> listener : listeners) {
                listener.executeFailed(task);
            }

            PivotUtil.logOnEDT(getClass(), Level.SEVERE, task.getFault());
            executeFailedImpl(task);
        }
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }
}
