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

package org.moosbusch.lumPi.beans.impl;

import java.net.URL;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.ApplicationContext;
import org.moosbusch.lumPi.beans.SmartBindable;
import org.moosbusch.lumPi.beans.impl.PropertyChangeAwareAdapter;

/**
 *
 * @author Gunnar Kappei
 */
public class SmartBindableAdapter extends PropertyChangeAwareAdapter implements SmartBindable {

    private boolean initialized = false;

    public SmartBindableAdapter(Bindable bean) {
        super(bean);
    }

    @Override
    public final void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        if (!isInitialized()) {
            ApplicationContext.queueCallback(() -> {
                Bindable bean = (Bindable) getMonitor().getBean();
                bean.initialize(namespace, location, resources);
                setInitialized(true);
            });
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
        firePropertyChange(INITIALIZED_PROPERTY_NAME);
    }

}
