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

import java.net.URL;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;

/**
 *
 * @author moosbusch
 */
public interface Submitable<T extends Object> extends ValueHolder<T>, Bindable {

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

    public static abstract class Adapter<T extends Object> extends ValueHolder.Adapter<T>
            implements Submitable<T>, PropertyChangeListener {

        private boolean canceled = false;
        private boolean submitted = false;

        public Adapter() {
            init();
        }

        private void init() {
            getMonitor().getPropertyChangeListeners().add(this);
        }

        @Override
        public boolean isCanceled() {
            return canceled;
        }

        @Override
        public void setCanceled(boolean canceled) {
            this.canceled = canceled;
            firePropertyChange(CANCELED_PROPERTYNAME);
        }

        @Override
        public boolean isSubmitted() {
            return submitted;
        }

        @Override
        public void setSubmitted(boolean submitted) {
            this.submitted = submitted;
            firePropertyChange(SUBMITTED_PROPERTYNAME);
        }

        @Override
        public final void cancel() {
            setCanceled(true);
            setSubmitted(false);
        }

        @Override
        public final void submit() {
            final T val = getValue();

            if (canSubmit(val)) {
                setSubmitted(true);
                setCanceled(false);
            }
        }

        @Override
        public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        }

        @Override
        public final void propertyChanged(Object bean, String propertyName) {
            switch (propertyName) {
                case SUBMITTED_PROPERTYNAME:
                    onSubmit(getValue());
                    break;
                case CANCELED_PROPERTYNAME:
                    onCancel();
                    break;
            }
        }
    }
}
