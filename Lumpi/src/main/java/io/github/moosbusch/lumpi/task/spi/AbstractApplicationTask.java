/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.moosbusch.lumpi.task.spi;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.TaskAdapter;
import io.github.moosbusch.lumpi.task.ApplicationTask;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractApplicationTask<T extends Object> extends Task<T> implements ApplicationTask<T> {
    private final boolean forwardEventsToEDT;

    public AbstractApplicationTask() {
        this(true);
    }

    public AbstractApplicationTask(boolean forwardEventsToEDT) {
        this.forwardEventsToEDT = true;
    }

    public AbstractApplicationTask(ExecutorService executorService) {
        super(executorService);
        this.forwardEventsToEDT = true;
    }

    public AbstractApplicationTask(ExecutorService executorService, boolean forwardEventsToEDT) {
        super(executorService);
        this.forwardEventsToEDT = forwardEventsToEDT;
    }

    @Override
    public final synchronized void execute(TaskListener<T> taskListener) {
        if (forwardEventsToEDT) {
            super.execute(new TaskAdapter<>(taskListener));
        } else {
            super.execute(taskListener);
        }
    }

    @Override
    public final synchronized void execute(TaskListener<T> taskListener, ExecutorService executorService) {
        super.execute(taskListener, executorService);
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }

}
