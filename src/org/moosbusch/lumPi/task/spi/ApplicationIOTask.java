/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.task.spi;

import java.util.concurrent.ExecutorService;
import org.apache.pivot.io.IOTask;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.TaskAdapter;

/**
 *
 * @author moosbusch
 */
public abstract class ApplicationIOTask<T extends Object> extends IOTask<T> implements TaskListener<T> {

    private boolean forwardEventsToEDT = false;

    public ApplicationIOTask(ExecutorService executorService) {
        super(executorService);
    }

    public ApplicationIOTask() {
    }

    public ApplicationIOTask(ExecutorService executorService, boolean forwardEventsToEDT) {
        super(executorService);
        this.forwardEventsToEDT = forwardEventsToEDT;
    }

    public ApplicationIOTask(boolean forwardEventsToEDT) {
        this.forwardEventsToEDT = forwardEventsToEDT;
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
}
