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
package org.moosbusch.lumPi.task;

import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.util.concurrent.TaskListener;
import org.moosbusch.lumPi.beans.SmartBindable;

/**
 *
 * @author moosbusch
 */
public interface ApplicationTask<T extends Object>
        extends SmartBindable {

    public T execute() throws TaskExecutionException;

    public void execute(TaskListener<T> taskListenerArgument);

    public T getResult();

    public Throwable getFault();

    public boolean isPending();

    public long getTimeout();

    public void setTimeout(long timeout);

    public void abort();

}
