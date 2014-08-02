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
package org.moosbusch.lumPi.gui.form.spi;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.beans.Submitable;
import org.moosbusch.lumPi.beans.spi.AbstractSubmitable;
import org.moosbusch.lumPi.util.LumPiUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitableForm<T extends Object> extends Form
        implements Submitable<T> {

    private final Map<String, String> helpFlags;
    private final Map<String, String> errorFlags;
    private final Map<String, Class<? extends Validator>> validators;
    private final Submitable<T> submitable;
    private boolean storeOldValue = true;
    private boolean clearOnCancel = false;
    private boolean clearOnSubmit = false;

    public AbstractSubmitableForm() {
        this.submitable = new SubmitableImpl();
        this.helpFlags = new HashMap<>();
        this.errorFlags = new HashMap<>();
        this.validators = new HashMap<>();
        initStyles();
    }

    private void initStyles() {
        LumPiUtil.setComponentStyle(this, "fill", "true");
        LumPiUtil.setComponentStyle(this, "leftAlignLabels", "true");
        LumPiUtil.setComponentStyle(this, "horizontalSpacing", "10");
        LumPiUtil.setComponentStyle(this, "verticalSpacing", "10");
        LumPiUtil.setComponentStyle(this, "padding", "10");
    }

    public static String createFormFieldName(Class<?> beanClass, String propertyName) {
        return createFormFieldName(beanClass.getName(), propertyName);
    }

    public static String createFormFieldName(String beanClassName, String propertyName) {
        if (StringUtils.isNotBlank(propertyName)) {
            return beanClassName + "." + propertyName.toLowerCase();
        }

        return beanClassName;
    }

    @Override
    public T modifyValueBeforeSubmit(T value) {
        return value;
    }

    @Override
    public final void cancel() {
        submitable.cancel();

        if (isClearOnCancel()) {
            clear();
        }
    }

    @Override
    public final void submit() {
        submitable.submit();

        if (isClearOnSubmit()) {
            clear();
        }
    }

    @Override
    public final T getValue() {
        T value = submitable.getValue();

        if (isStoreCurrentValue()) {
            if (value != null) {
                store(value);
            }
        }

        return value;
    }

    @Override
    public final void setValue(T val) {
        T value = submitable.getValue();

        if (isStoreCurrentValue()) {
            if (value != null) {
                store(value);
            }
        }

        submitable.setValue(val);
        load(val);
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
    public final void firePropertyChange(String propertyName) {
        submitable.firePropertyChange(propertyName);
    }

    @Override
    public final BeanMonitor getMonitor() {
        return submitable.getMonitor();
    }

    @Override
    public boolean isCanceled() {
        return submitable.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        submitable.setCanceled(canceled);
    }

    public boolean isStoreCurrentValue() {
        return storeOldValue;
    }

    public void setStoreCurrentValue(boolean storeOldValue) {
        this.storeOldValue = storeOldValue;
    }

    public String getErrorFlag(String fieldName) {
        return errorFlags.get(fieldName);
    }

    public String putErrorFlag(String fieldName, String message) {
        return errorFlags.put(fieldName, message);
    }

    public String removeErrorFlag(String fieldName) {
        return errorFlags.remove(fieldName);
    }

    public String getHelpFlag(String fieldName) {
        return helpFlags.get(fieldName);
    }

    public String putHelpFlag(String fieldName, String message) {
        return helpFlags.put(fieldName, message);
    }

    public String removeHelpFlag(String fieldName) {
        return helpFlags.remove(fieldName);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        submitable.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        submitable.removePropertyChangeListener(pcl);
    }

    public Class<? extends Validator> getValidator(String fieldName) {
        return validators.get(fieldName);
    }

    public Class<? extends Validator> putValidator(String fieldName,
            Class<? extends Validator> validator) {
        return validators.put(fieldName, validator);
    }

    public Class<? extends Validator> removeValidator(String fieldName) {
        return validators.remove(fieldName);
    }

    public boolean isClearOnCancel() {
        return clearOnCancel;
    }

    public void setClearOnCancel(boolean clearOnCancel) {
        this.clearOnCancel = clearOnCancel;
    }

    public boolean isClearOnSubmit() {
        return clearOnSubmit;
    }

    public void setClearOnSubmit(boolean clearOnSubmit) {
        this.clearOnSubmit = clearOnSubmit;
    }

    private class SubmitableImpl extends AbstractSubmitable<T> {

        @Override
        public boolean canSubmit(T value) {
            return AbstractSubmitableForm.this.canSubmit(value);
        }

        @Override
        public void onCancel() {
            AbstractSubmitableForm.this.onCancel();
        }

        @Override
        public void onSubmit(T value) {
            AbstractSubmitableForm.this.onSubmit(value);
        }

        @Override
        public T modifyValueBeforeSubmit(T value) {
            return AbstractSubmitableForm.this.modifyValueBeforeSubmit(value);
        }
    }
}
