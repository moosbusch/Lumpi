/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.beans;

import org.apache.pivot.beans.BeanAdapter;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.MapListener;

/**
 *
 * @author Gunnar Kappei
 */
public interface Bean extends PropertyChangeAware {

    public BeanAdapter getAdapter();

    public Object get(String key);

    public Object set(String key, Object value);

    public static class Adapter implements Bean {

        private final PropertyChangeAware.Adapter pca;
        private final BeanAdapter adapter;

        public Adapter() {
            this.pca = new PropertyChangeAware.Adapter(this);
            this.adapter = new BeanAdapter(this);
            init();
        }

        private void init() {
            getAdapter().getMapListeners().add(new MapListenerImpl());
        }

        @Override
        public final synchronized Object get(String key) {
            return getAdapter().get(key);
        }

        @Override
        public final synchronized Object set(String key, Object value) {
            return getAdapter().put(key, value);
        }

        @Override
        public BeanAdapter getAdapter() {
            return adapter;
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

        private class MapListenerImpl extends MapListener.Adapter<String, Object> {

            @Override
            public void valueUpdated(Map<String, Object> map, String key, Object previousValue) {
                firePropertyChange(key);
            }

        }
    }
}
