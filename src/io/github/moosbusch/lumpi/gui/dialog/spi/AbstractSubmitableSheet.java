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
package io.github.moosbusch.lumpi.gui.dialog.spi;

import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Sheet;
import io.github.moosbusch.lumpi.beans.Submitable;
import io.github.moosbusch.lumpi.beans.spi.AbstractSubmitable;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitableSheet<T extends Object> extends Sheet
        implements Submitable<T>, Bindable, PropertyChangeListener {

    private final Submitable<T> submitable;

    public AbstractSubmitableSheet(Component content) {
        super(content);
        this.submitable = new SubmitableImpl();
    }

    public AbstractSubmitableSheet() {
        this.submitable = new SubmitableImpl();
    }

    @Override
    public T modifyValueBeforeSubmit(T value) {
        return value;
    }

    @Override
    public final void cancel() {
        submitable.cancel();
        close();
    }

    @Override
    public final void submit() {
        submitable.submit();
        close();
    }

    @Override
    public final T getValue() {
        return submitable.getValue();
    }

    @Override
    public final void setValue(T val) {
        submitable.setValue(val);
    }

    @Override
    public final boolean isSubmitted() {
        return submitable.isSubmitted();
    }

    @Override
    public final void setSubmitted(boolean submitted) {
        submitable.setSubmitted(submitted);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        submitable.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        submitable.removePropertyChangeListener(pcl);
    }

    @Override
    public final void firePropertyChange(String propertyName) {
        if (submitable != null) {
            submitable.firePropertyChange(propertyName);
        }
    }

    @Override
    public final BeanMonitor getMonitor() {
        return submitable.getMonitor();
    }

    @Override
    public final boolean isCanceled() {
        return submitable.isCanceled();
    }

    @Override
    public final void setCanceled(boolean canceled) {
        submitable.setCanceled(canceled);
    }

    @Override
    public final void propertyChanged(Object bean, String propertyName) {
        switch (propertyName) {
            case SUBMITTED_PROPERTYNAME: {
                onSubmit(getValue());
                break;
            }
            case CANCELED_PROPERTYNAME: {
                onCancel();
                break;
            }
            default: {
            }
        }
    }

    private class SubmitableImpl extends AbstractSubmitable<T> {

        @Override
        public boolean canSubmit(T value) {
            return AbstractSubmitableSheet.this.canSubmit(value);
        }

        @Override
        public void onCancel() {
            AbstractSubmitableSheet.this.onCancel();
        }

        @Override
        public void onSubmit(T value) {
            AbstractSubmitableSheet.this.onSubmit(value);
        }

        @Override
        public T modifyValueBeforeSubmit(T value) {
            return AbstractSubmitableSheet.this.modifyValueBeforeSubmit(value);
        }
    }
}
