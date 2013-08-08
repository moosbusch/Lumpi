/*
 Copyright 2013 Gunnar Kappei

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.moosbusch.lumPi.action;

import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.content.ButtonData;

/**
 *
 * @author moosbusch
 */
public interface TaskAction<T extends Object>
        extends ApplicationAction, TaskListener<T> {

    public Task<T> getTask();

    public static abstract class Adapter<T extends Object>
            extends ApplicationAction.Adapter implements TaskAction<T> {

        public Adapter(boolean enabled) {
            super(enabled);
        }

        public Adapter() {
        }

        public Adapter(ButtonData buttonData, boolean enabled) {
            super(buttonData, enabled);
        }

        public Adapter(ButtonData buttonData) {
            super(buttonData);
        }

        @Override
        public void taskExecuted(Task<T> task) {
        }

        @Override
        public void executeFailed(Task<T> task) {
        }

        @Override
        @SuppressWarnings("unchecked")
        public final void doPerform(Component source) {
            getTask().execute(this);
        }
    }
}
