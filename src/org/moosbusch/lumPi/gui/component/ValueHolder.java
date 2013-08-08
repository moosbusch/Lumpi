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
package org.moosbusch.lumPi.gui.component;

import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.moosbusch.lumPi.beans.PropertyChangeAware;

/**
 *
 * @author moosbusch
 */
public interface ValueHolder<T extends Object> extends PropertyChangeAware {

    public static final String VALUE_PROPERTY = "value";

    public T getValue();

    public void setValue(T val);

    public abstract class Adapter<T extends Object>
            implements ValueHolder<T> {

        private T value;
        private final PropertyChangeAware pca;

        public Adapter() {
            this.pca = new PropertyChangeAware.Adapter(this);
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public final void setValue(T value) {
            this.value = value;
            firePropertyChange(VALUE_PROPERTY);
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
            if (pca != null) {
                pca.firePropertyChange(propertyName);
            }
        }

    }
}
