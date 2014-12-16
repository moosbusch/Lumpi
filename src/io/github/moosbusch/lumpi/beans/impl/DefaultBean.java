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

import org.apache.pivot.beans.BeanAdapter;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.MapListener;
import io.github.moosbusch.lumpi.beans.Bean;
import io.github.moosbusch.lumpi.beans.impl.PropertyChangeAwareAdapter;

/**
 *
 * @author Gunnar Kappei
 */
public class DefaultBean implements Bean {
    private final PropertyChangeAwareAdapter pca;
    private final BeanAdapter adapter;

    public DefaultBean() {
        this.pca = new PropertyChangeAwareAdapter(this);
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
