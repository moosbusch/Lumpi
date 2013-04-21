/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.io.StoreValueDelegate;
import org.moosbusch.lumPi.gui.form.spi.AbstractDynamicForm;

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
