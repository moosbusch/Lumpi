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
package io.github.moosbusch.lumpi.gui.form.editor.spi;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.validation.Validator;
import io.github.moosbusch.lumpi.gui.form.editor.FormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.binding.FormEditorBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.io.StoreValueDelegate;
import io.github.moosbusch.lumpi.gui.form.spi.AbstractDynamicForm;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractFormEditor<T extends Component>
        implements FormEditor<T> {

    private String fieldName;
    private String propertyType;
    private final T component;
    private final StoreValueDelegate<T> storeValueDelegate;
    private final FormEditorBindMapping<T> bindMapping;
    private Object value;

    public AbstractFormEditor() {
        this.component = initComponent();
        this.storeValueDelegate = initStoreValueDelegate();
        this.bindMapping = initBindMapping();
        init();
    }

    private void init() {
        getBindMapping().bind(getComponent());
    }

    private T initComponent() {
        return Objects.requireNonNull(createComponent());
    }

    private StoreValueDelegate<T> initStoreValueDelegate() {
        return createStoreValueDelegate();
    }

    private FormEditorBindMapping<T> initBindMapping() {
        return Objects.requireNonNull(createBindMapping());
    }

    protected abstract T createComponent();

    protected abstract FormEditorBindMapping<T> createBindMapping();

    protected StoreValueDelegate<T> createStoreValueDelegate() {
        return null;
    }

    @Override
    public void loadValue(Object val) {
        getComponent().load(val);
    }

    @Override
    public void storeValue(Object val) {
        getComponent().store(val);
    }

    @Override
    public T getComponent() {
        return component;
    }

    @Override
    public FormEditorBindMapping<T> getBindMapping() {
        return bindMapping;
    }

    @Override
    public StoreValueDelegate<T> getStoreValueDelegate() {
        return storeValueDelegate;
    }

    @Override
    public Validator getValidator(AbstractDynamicForm<? extends Object> form) {
        Class<? extends Validator> validatorClass =
                form.getValidator(getFormFieldName());

        if (validatorClass == null) {
            validatorClass = form.getValidator(getPropertyTypeName());
        }

        if (validatorClass != null) {
            try {
                return (Validator) Class.forName(validatorClass.getName()).newInstance();
            } catch (ClassNotFoundException | InstantiationException |
                    IllegalAccessException ex) {
                Logger.getLogger(AbstractFormEditor.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    @Override
    public boolean isValid(AbstractDynamicForm<? extends Object> form) {
        Validator vali = getValidator(form);

        if (vali != null) {
            return vali.isValid(getValueAsText());
        }

        return true;
    }

    @Override
    public final Object getValue() {
        storeValue(value);
        return value;
    }

    @Override
    public final void setValue(Object val) {
        this.value = val;
        loadValue(val);
    }

    @Override
    public String getValueAsText() {
        Object val = getValue();

        if (val != null) {
            if (val instanceof Byte) {
                return Byte.toString((Byte) val);
            } else if (val instanceof Short) {
                return Short.toString((Short) val);
            } else if (val instanceof Long) {
                return Long.toString((Long) val);
            } else if (val instanceof Integer) {
                return Integer.toString((Integer) val);
            } else if (val instanceof Double) {
                return Double.toString((Double) val);
            } else if (val instanceof Float) {
                return Float.toString((Float) val);
            }

            return val.toString();
        }

        return null;
    }

    @Override
    public boolean isScrollable() {
        return false;
    }

    @Override
    public String getFormFieldName() {
        return fieldName;
    }

    @Override
    public void setFormFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getPropertyTypeName() {
        return propertyType;
    }

    @Override
    public void setPropertyTypeName(String propertyType) {
        this.propertyType = propertyType;
    }

}
