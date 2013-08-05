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

import java.net.URL;
import java.util.concurrent.ExecutorService;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.TaskAdapter;
import org.moosbusch.lumPi.beans.PropertyChangeAware;

/**
 *
 * @author moosbusch
 */
public interface ApplicationTask<T extends Object>
        extends Bindable, PropertyChangeAware {

    public T execute() throws TaskExecutionException;

    public void execute(TaskListener<T> taskListenerArgument);

    public T getResult();

    public Throwable getFault();

    public boolean isPending();

    public long getTimeout();

    public void setTimeout(long timeout);

    public void abort();

    public abstract class Adapter<T extends Object> extends Task<T>
            implements ApplicationTask<T> {

        private final boolean forwardEventsToEDT;
        private final List<TaskListener<T>> listenerList;
        private final PropertyChangeAware.Adapter pca;

        public Adapter() {
            this(true);
        }

        public Adapter(boolean forwardEventsToEDT) {
            this.forwardEventsToEDT = forwardEventsToEDT;
            this.listenerList = new ArrayList<>();
            this.pca = new PropertyChangeAware.Adapter();
        }

        public Adapter(ExecutorService executorService) {
            super(executorService);
            this.forwardEventsToEDT = true;
            this.listenerList = new ArrayList<>();
            this.pca = new PropertyChangeAware.Adapter();
        }

        public Adapter(ExecutorService executorService, boolean forwardEventsToEDT) {
            super(executorService);
            this.forwardEventsToEDT = forwardEventsToEDT;
            this.listenerList = new ArrayList<>();
            this.pca = new PropertyChangeAware.Adapter();
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
        public final synchronized void execute(TaskListener<T> taskListener,
                ExecutorService executorService) {
            super.execute(taskListener, executorService);
        }

        @Override
        public BeanMonitor getMonitor() {
            return pca.getMonitor();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            pca.addPropertyChangeListener(pcl);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            pca.removePropertyChangeListener(pcl);
        }

        @Override
        public void firePropertyChange(String propertyName) {
            pca.firePropertyChange(propertyName);
        }

        @Override
        public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        }
    }
}
