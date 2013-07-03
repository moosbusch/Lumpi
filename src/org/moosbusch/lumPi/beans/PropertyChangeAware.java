/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.beans;

import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;

/**
 *
 * @author moosbusch
 */
public interface PropertyChangeAware {

    public BeanMonitor getMonitor();

    public void addPropertyChangeListener(PropertyChangeListener pcl);

    public void removePropertyChangeListener(PropertyChangeListener pcl);

    public void firePropertyChange(String propertyName);

    public static class Adapter implements PropertyChangeAware {

        private final BeanMonitor monitor;

        public Adapter(Object bean) {
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
            for (PropertyChangeListener pcl :getMonitor().getPropertyChangeListeners()) {
                pcl.propertyChanged(this, propertyName);
            }
        }

    }
}
