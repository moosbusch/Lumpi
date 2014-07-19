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

package org.moosbusch.lumPi.beans.spi;

import org.moosbusch.lumPi.beans.Submitable;
import org.moosbusch.lumPi.beans.spi.AbstractValueStore;
import org.apache.pivot.beans.PropertyChangeListener;
import org.moosbusch.lumPi.beans.impl.SmartBindableAdapter;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractSubmitable<T extends Object>
    extends AbstractValueStore<T> implements Submitable<T>, PropertyChangeListener {
    private final SmartBindableAdapter sba;
    private boolean canceled = false;
    private boolean submitted = false;

    public AbstractSubmitable() {
        this.sba = new SmartBindableAdapter(this);
        init();
    }

    private void init() {
        getMonitor().getPropertyChangeListeners().add(this);
    }

    @Override
    public final boolean isCanceled() {
        return canceled;
    }

    @Override
    public final void cancel() {
        setCanceled(true);
        setSubmitted(false);
    }

    @Override
    public final void submit() {
        final T val = modifyValueBeforeSubmit(getValue());
        setValue(val);

        if (canSubmit(val)) {
            setSubmitted(true);
            setCanceled(false);
        }
    }

    @Override
    public final void setCanceled(boolean canceled) {
        this.canceled = canceled;
        firePropertyChange(Submitable.CANCELED_PROPERTYNAME);
    }

    @Override
    public final boolean isSubmitted() {
        return submitted;
    }

    @Override
    public final void setSubmitted(boolean submitted) {
        this.submitted = submitted;
        firePropertyChange(Submitable.SUBMITTED_PROPERTYNAME);
    }

    @Override
    public boolean isInitialized() {
        return sba.isInitialized();
    }

    @Override
    public void setInitialized(boolean initialized) {
        sba.setInitialized(initialized);
    }

    @Override
    public final void propertyChanged(Object bean, String propertyName) {
        switch (propertyName) {
            case Submitable.SUBMITTED_PROPERTYNAME:
                if (isSubmitted()) {
                    onSubmit(getValue());
                }
                break;
            case Submitable.CANCELED_PROPERTYNAME:
                if (isCanceled()) {
                    onCancel();
                }
                break;
        }
    }

}
