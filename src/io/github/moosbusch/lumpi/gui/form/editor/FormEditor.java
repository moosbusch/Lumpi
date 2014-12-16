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
package io.github.moosbusch.lumpi.gui.form.editor;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.validation.Validator;
import io.github.moosbusch.lumpi.gui.form.editor.binding.FormEditorBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.io.StoreValueDelegate;
import io.github.moosbusch.lumpi.gui.form.spi.AbstractDynamicForm;

/**
 *
 * @author moosbusch
 */
public interface FormEditor<T extends Component> {

    public static final String STATE_KEY_PROPERTY = "stateKey";
    public static final String SELECTED_KEY_PROPERTY = "selectedKey";
    public static final String BUTTON_DATA_KEY_PROPERTY = "buttonDataKey";
    public static final String TEXT_KEY_PROPERTY = "textKey";
    public static final String LIST_DATA_KEY_PROPERTY = "listDataKey";
    public static final String TREE_DATA_KEY_PROPERTY = "treeDataKey";
    public static final String SPINNER_DATA_KEY_PROPERTY = "spinnerDataKey";
    public static final String IMAGE_KEY_PROPERTY = "imageKey";
    public static final String SELECTED_DATE_KEY_PROPERTY = "selectedDateKey";
    public static final String SELECTED_COLOR_KEY_ROPERTY = "selectedColorKey";

    public String getFormFieldName();

    public void setFormFieldName(String fieldName);

    public String getPropertyTypeName();

    public void setPropertyTypeName(String propertyType);

    public T getComponent();

    public StoreValueDelegate<T> getStoreValueDelegate();

    public FormEditorBindMapping<T> getBindMapping();

    public String getDataBindingKeyPropertyName();

    public Object getValue();

    public void setValue(Object val);

    public String getValueAsText();

    public void loadValue(Object val);

    public void storeValue(Object val);

    public Validator getValidator(AbstractDynamicForm<? extends Object> form);

    public boolean isValid(AbstractDynamicForm<? extends Object> form);

    public boolean isScrollable();

}
