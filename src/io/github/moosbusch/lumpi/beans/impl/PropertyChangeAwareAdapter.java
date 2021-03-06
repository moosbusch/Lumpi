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

package io.github.moosbusch.lumpi.beans.impl;

import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import io.github.moosbusch.lumpi.beans.PropertyChangeAware;

/**
 *
 * @author Gunnar Kappei
 */
public class PropertyChangeAwareAdapter implements PropertyChangeAware {

        private final BeanMonitor monitor;

        public PropertyChangeAwareAdapter(Object bean) {
            this.monitor = new BeanMonitor(bean);
        }

        @Override
        public BeanMonitor getMonitor() {
            return monitor;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            getMonitor().getPropertyChangeListeners().add(pcl);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            getMonitor().getPropertyChangeListeners().remove(pcl);
        }

        @Override
        public void firePropertyChange(String propertyName) {
            for (PropertyChangeListener pcl : getMonitor().getPropertyChangeListeners()) {
                pcl.propertyChanged(getMonitor().getBean(), propertyName);
            }
        }

}
