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
import org.moosbusch.lumPi.beans.SmartBindable;

/**
 *
 * @author moosbusch
 */
public interface Submitable<T extends Object> extends ValueStore<T>, SmartBindable {

    public static final String CANCELED_PROPERTYNAME = "canceled";
    public static final String SUBMITTED_PROPERTYNAME = "submitted";

    public void submit();

    public void cancel();

    public boolean canSubmit(final T value);

    public boolean isCanceled();

    public void setCanceled(boolean canceled);

    public boolean isSubmitted();

    public void setSubmitted(boolean submitted);

    public void onCancel();

    public void onSubmit(T value);

    public T modifyValueBeforeSubmit(T value);

    public static abstract class Adapter<T extends Object> extends ValueStore.Adapter<T>
            implements Submitable<T>, PropertyChangeListener {

        private final SmartBindable.Adapter sba;
        private boolean canceled = false;
        private boolean submitted = false;

        public Adapter() {
            this.sba = new SmartBindable.Adapter(this);
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
            firePropertyChange(CANCELED_PROPERTYNAME);
        }

        @Override
        public final boolean isSubmitted() {
            return submitted;
        }

        @Override
        public final void setSubmitted(boolean submitted) {
            this.submitted = submitted;
            firePropertyChange(SUBMITTED_PROPERTYNAME);
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
                case SUBMITTED_PROPERTYNAME:
                    if (isSubmitted()) {
                        onSubmit(getValue());
                    }

                    break;
                case CANCELED_PROPERTYNAME:
                    if (isCanceled()) {
                        onCancel();
                    }

                    break;
            }
        }
    }
}
