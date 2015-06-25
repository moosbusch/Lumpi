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
package io.github.moosbusch.lumpi.task;

import java.util.concurrent.ExecutorService;
import org.apache.pivot.io.IOTask;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.TaskAdapter;
import io.github.moosbusch.lumpi.beans.PropertyChangeAware;

/**
 *
 * @author moosbusch
 */
public interface ApplicationIOTask<T extends Object> extends TaskListener<T>, PropertyChangeAware {

    public void runTask(ExecutorService executorService);

    public void runTask(TaskListener<T> taskListener);

    public void runTask(TaskListener<T> taskListener, ExecutorService executorService);

    public void runTask();

}