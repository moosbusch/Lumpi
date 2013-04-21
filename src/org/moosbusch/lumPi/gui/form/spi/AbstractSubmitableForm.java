/*

 *
 */
package org.moosbusch.lumPi.gui.form.spi;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.gui.Monitorable;
import org.moosbusch.lumPi.gui.component.Submitable;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitableForm<T extends Object> extends Form
        implements Submitable<T> {

    private final BeanMonitor monitor;
    private final Map<String, String> helpFlags;
    private final Map<String, String> errorFlags;
    private final Map<String, Class<? extends Validator>> validators;
    private boolean storeOldValue = true;
    private boolean canceled = false;
    private T value;

    public AbstractSubmitableForm() {
        this.monitor = new BeanMonitor(this);
        this.helpFlags = new HashMap<>();
        this.errorFlags = new HashMap<>();
        this.validators = new HashMap<>();
        initStyles();
    }

    private void initStyles() {
        PivotUtil.setComponentStyle(this, "fill", "true");
        PivotUtil.setComponentStyle(this, "leftAlignLabels", "true");
        PivotUtil.setComponentStyle(this, "horizontalSpacing", "10");
        PivotUtil.setComponentStyle(this, "verticalSpacing", "10");
        PivotUtil.setComponentStyle(this, "padding", "10");
    }

    protected abstract void onCancel();

    protected abstract void onSubmit(T value);

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
    public final void cancel() {
        setCanceled(true);
        onCancel();
        clear();
    }

    @Override
    public final void submit() {
        final T val = getValue();

        if (canSubmit(val)) {
            store(val);
            setCanceled(false);
            onSubmit(val);
            clear();
        }
    }

    @Override
    public final T getValue() {
        if (isStoreCurrentValue()) {
            if (value != null) {
                store(value);
            }
        }

        return value;
    }

    @Override
    public final void setValue(T val) {
        if (isStoreCurrentValue()) {
            if (value != null) {
                store(value);
            }
        }

        this.value = val;
        load(val);
        PivotUtil.firePropertyChangeListeners(this, VALUE_PROPERTY, getMonitor());
    }

    @Override
    public final BeanMonitor getMonitor() {
        return monitor;
    }

    public boolean isStoreCurrentValue() {
        return storeOldValue;
    }

    public void setStoreCurrentValue(boolean storeOldValue) {
        this.storeOldValue = storeOldValue;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
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
}
