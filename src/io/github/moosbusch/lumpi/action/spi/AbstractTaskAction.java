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

package io.github.moosbusch.lumpi.action.spi;

import java.util.Objects;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.content.ButtonData;
import io.github.moosbusch.lumpi.action.TaskAction;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractTaskAction<T extends Object> extends AbstractApplicationAction implements TaskAction<T> {

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

    @Override
    public void taskExecuted(Task<T> task) {
    }

    @Override
    public void executeFailed(Task<T> task) {
    }

    @Override
    public void initializeTask(Task<T> task) {
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public final void doPerform(Component source) {
        Task<T> tsk = Objects.requireNonNull(getTask());
        initializeTask(tsk);
        tsk.execute(this);
    }

}
