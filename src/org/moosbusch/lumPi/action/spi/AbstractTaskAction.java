/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.content.ButtonData;
import org.moosbusch.lumPi.task.spi.ApplicationTask;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTaskAction<T extends Object, V extends ApplicationTask<T>>
        extends LabelableAction implements TaskListener<T> {

    private ExecutorService executorService;
    private V task;

    public AbstractTaskAction(boolean enabled) {
        super(enabled);
    }

    public AbstractTaskAction() {
    }

    public AbstractTaskAction(ButtonData buttonData, boolean enabled) {
        super(buttonData, enabled);
    }

    public AbstractTaskAction(ButtonData buttonData) {
        super(buttonData);
    }

    public abstract void taskExecuting(Task<T> task);

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public V getTask() {
        return task;
    }

    public void setTask(V task) {
        this.task = task;
    }

    public final void perform() {
        perform(null);
    }

    @Override
    public final void perform(Component source) {
        V tsk = Objects.requireNonNull(getTask());
        tsk.getListenerList().add(this);

        taskExecuting(tsk);

        ExecutorService execService = getExecutorService();

        if (execService != null) {
            tsk.runTask(execService);
        } else {
            tsk.runTask();
        }
    }
}
