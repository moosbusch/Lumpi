/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.spi;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Keyboard;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TextInput;
import org.moosbusch.lumPi.gui.component.impl.FillScrollPane;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.CheckboxBooleanFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultByteFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultCharacterFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultCollectionFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultDoubleFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultFloatFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultIntegerFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultShortFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultStringFormEditor;
import org.moosbusch.lumPi.gui.form.editor.io.StoreValueDelegate;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractNumberFormEditor;
import org.moosbusch.lumPi.gui.form.editor.validator.impl.BooleanValidator;
import org.moosbusch.lumPi.gui.form.editor.validator.impl.ByteNumberValidator;
import org.moosbusch.lumPi.gui.form.editor.validator.impl.DoubleNumberValidator;
import org.moosbusch.lumPi.gui.form.editor.validator.impl.FloatNumberValidator;
import org.moosbusch.lumPi.gui.form.editor.validator.impl.IntegerNumberValidator;
import org.moosbusch.lumPi.gui.form.editor.validator.impl.ShortNumberValidator;
import org.moosbusch.lumPi.util.FormUtil;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractDynamicForm<T extends Object> extends AbstractSubmitableForm<T> {

    private final Map<String, Class<? extends FormEditor<? extends Component>>> editorsMap;
    private final Map<String, FormEditor<? extends Component>> editors;
    private String sectionHeading;
    private boolean showHeading = false;

    public AbstractDynamicForm() {
        this.editors = new HashMap<>();
        this.editorsMap = new HashMap<>();
        initDefaults();
    }

    private void initDefaults() {
        initDefaultValidators();
        initDefaultEditors();
    }

    protected void initDefaultEditors() {
        putEditor(Boolean.class.getName(), CheckboxBooleanFormEditor.class);
        putEditor(org.apache.pivot.collections.Collection.class.getName(),
                DefaultCollectionFormEditor.class);
        putEditor(java.util.Collection.class.getName(), DefaultCollectionFormEditor.class);
        putEditor(Integer.class.getName(), DefaultIntegerFormEditor.class);
        putEditor(Byte.class.getName(), DefaultByteFormEditor.class);
        putEditor(Short.class.getName(), DefaultShortFormEditor.class);
        putEditor(Float.class.getName(), DefaultFloatFormEditor.class);
        putEditor(Double.class.getName(), DefaultDoubleFormEditor.class);
        putEditor(Character.class.getName(), DefaultCharacterFormEditor.class);
        putEditor(String.class.getName(), DefaultStringFormEditor.class);
    }

    protected void initDefaultValidators() {
        putValidator(Boolean.class.getName(), BooleanValidator.class);
        putValidator(Integer.class.getName(), IntegerNumberValidator.class);
        putValidator(Byte.class.getName(), ByteNumberValidator.class);
        putValidator(Short.class.getName(), ShortNumberValidator.class);
        putValidator(Float.class.getName(), FloatNumberValidator.class);
        putValidator(Double.class.getName(), DoubleNumberValidator.class);
    }

    protected ScrollPane createScrollPane() {
        return new FillScrollPane();
    }

    protected final Form.Section createSection(Class<?> beanClass)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Form.Section result = new Form.Section();
        PropertyDescriptor[] propDescs =
                PropertyUtils.getPropertyDescriptors(beanClass);
        String sectHeading = getSectionHeading();

        if ((isShowSectionHeading()) && (StringUtils.isNotBlank(sectHeading))) {
            result.setHeading(sectHeading);
        }

        for (PropertyDescriptor propDesc : propDescs) {
            Class<?> propertyClass = ClassUtils.primitiveToWrapper(
                    propDesc.getPropertyType());
            String propertyName = propDesc.getName();
            FormEditor<? extends Component> editor;

            if (!isExcludedProperty(beanClass, propertyName)) {
                editor = getEditor(beanClass.getName(),
                        propertyClass.getName(), propertyName);

                if (editor != null) {
                    Component cmp = editor.getComponent();
                    PropertyUtils.setProperty(cmp,
                            editor.getDataBindingKeyPropertyName(),
                            propertyName);
                    setLabel(cmp, StringUtils.capitalize(propertyName));

                    if (editor.isScrollable()) {
                        ScrollPane scroll = Objects.requireNonNull(
                                createScrollPane());
                        scroll.setView(cmp);
                        result.add(scroll);
                    } else {
                        result.add(cmp);
                    }
                }
            }
        }

        return result;
    }

    protected boolean isExcludedProperty(Class<?> beanClass, String propertyName) {
        return FormUtil.isExcludedProperty(beanClass, propertyName, FormUtil.getDefaultIgnoredProperties());
    }

    protected final void removeSections() {
        PivotUtil.clearSequence(getSections());
    }

    protected final Map<String, FormEditor<? extends Component>> getEditors() {
        return editors;
    }

    protected final Map<String, Class<? extends FormEditor<? extends Component>>> getEditorsMap() {
        return editorsMap;
    }

    public final FormEditor<? extends Component> getEditor(String beanClassName,
            String propertyClassName, String propertyName) {
        String formFieldName = AbstractDynamicForm.createFormFieldName(
                beanClassName, propertyName);
        FormEditor<? extends Component> result = null;
        Class<? extends FormEditor<? extends Component>> editorClass =
                lookupEditorClass(formFieldName, propertyClassName);

        if (editorClass != null) {
            try {
                result = instantiateEditor(editorClass);
            } catch (ClassNotFoundException | InstantiationException |
                    IllegalAccessException ex) {
                Logger.getLogger(AbstractDynamicForm.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                if (result != null) {
                    initializeEditor(result, formFieldName, propertyClassName);
                    getEditors().put(formFieldName, result);
                }
            }
        }

        return result;
    }

    protected Class<? extends FormEditor<? extends Component>> lookupEditorClass(
            String formFieldName, String propertyClassName) {
        Class<? extends FormEditor<? extends Component>> result =
                getEditorsMap().get(formFieldName);

        if (result == null) {
            result = getEditorsMap().get(propertyClassName);
        }

        if (result == null) {
            java.util.Set<Class<?>> superTypes = null;

            try {
                superTypes = PivotUtil.getSuperTypes(
                        Class.forName(propertyClassName), false, true, true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AbstractDynamicForm.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                for (Class<?> superType : superTypes) {
                    result = getEditorsMap().get(superType.getName());

                    if (result != null) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    protected FormEditor<? extends Component> instantiateEditor(
            Class<? extends FormEditor<? extends Component>> editorClass)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return (FormEditor<? extends Component>) Class.forName(
                editorClass.getName()).newInstance();
    }

    protected void initializeEditor(FormEditor<? extends Component> editor,
            String formFieldName, String propertyClassName) {
        editor.setFormFieldName(formFieldName);
        editor.setPropertyTypeName(propertyClassName);
        editor.getComponent().getComponentKeyListeners().add(
                new HelpListener(formFieldName));

        if (editor instanceof AbstractNumberFormEditor<?, ?>) {
            AbstractNumberFormEditor<?, ?> numberEditor =
                    (AbstractNumberFormEditor<?, ?>) editor;
            TextInput numberTextInput = numberEditor.getComponent();
            numberTextInput.setValidator(numberEditor.getValidator(this));
        }
    }

    public final Class<? extends FormEditor<? extends Component>> putEditor(
            String fieldName, Class<? extends FormEditor<? extends Component>> editorType) {
        return getEditorsMap().put(fieldName, editorType);
    }

    @Override
    public final void load(Object context) {
        getEditors().clear();
        removeSections();

        if (context != null) {
            Form.Section section = null;

            try {
                section = createSection(context.getClass());
            } catch (IllegalAccessException | InvocationTargetException |
                    NoSuchMethodException ex) {
                Logger.getLogger(AbstractDynamicForm.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                if (section != null) {
                    if (section.getForm() == null) {
                        getSections().add(section);
                    }

                    for (String formField : getEditors()) {
                        FormEditor<? extends Component> editor = getEditors().get(formField);
                        editor.setValue(context);
                    }
                }
            }


        }
    }

    @Override
    public final void store(Object context) {
        if (context != null) {
            for (String formField : getEditors()) {
                FormEditor<? extends Component> editor = getEditors().get(formField);
                StoreValueDelegate<? extends Component> storeValueDelegate =
                        editor.getStoreValueDelegate();
                if (storeValueDelegate != null) {
                    storeValueDelegate.storeValue(context);
                } else {
                    editor.storeValue(context);
                }
            }
        }
    }

    public final Object store() {
        return getValue();
    }

    public String getSectionHeading() {
        return sectionHeading;
    }

    public void setSectionHeading(String heading) {
        this.sectionHeading = heading;
    }

    public boolean isShowSectionHeading() {
        return showHeading;
    }

    public void setShowSectionHeading(boolean showHeading) {
        this.showHeading = showHeading;
    }

    private class HelpListener extends ComponentKeyListener.Adapter {

        private final String fieldName;

        public HelpListener(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public boolean keyPressed(Component component, int keyCode,
                Keyboard.KeyLocation keyLocation) {
            if (keyCode == Keyboard.KeyCode.F1) {
                String helpFlag = getHelpFlag(fieldName);

                if (StringUtils.isNotBlank(helpFlag)) {
                    Form.setFlag(component, new Flag(MessageType.INFO, helpFlag));
                }

                return true;
            } else if (keyCode == Keyboard.KeyCode.F2) {

                if (!getEditors().get(fieldName).isValid(AbstractDynamicForm.this)) {
                    String errorFlag = getErrorFlag(fieldName);

                    if (StringUtils.isNotBlank(errorFlag)) {
                        Form.setFlag(component, new Flag(
                                MessageType.ERROR, errorFlag));
                    }
                }

                return true;
            }

            return super.keyPressed(component, keyCode, keyLocation);
        }
    }
}
