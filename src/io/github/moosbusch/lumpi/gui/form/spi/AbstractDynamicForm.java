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
package io.github.moosbusch.lumpi.gui.form.spi;

import io.github.moosbusch.lumpi.gui.component.impl.FillScrollPane;
import io.github.moosbusch.lumpi.gui.form.editor.FormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.CheckboxBooleanFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultByteFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultCharacterFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultCollectionFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultDoubleFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultFloatFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultIntegerFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultShortFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultStringFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.io.StoreValueDelegate;
import io.github.moosbusch.lumpi.gui.form.editor.spi.AbstractNumberFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.validator.impl.BooleanValidator;
import io.github.moosbusch.lumpi.gui.form.editor.validator.impl.ByteValidator;
import io.github.moosbusch.lumpi.gui.form.editor.validator.impl.DoubleValidator;
import io.github.moosbusch.lumpi.gui.form.editor.validator.impl.FloatValidator;
import io.github.moosbusch.lumpi.gui.form.editor.validator.impl.IntegerValidator;
import io.github.moosbusch.lumpi.gui.form.editor.validator.impl.ShortValidator;
import io.github.moosbusch.lumpi.util.FormUtil;
import io.github.moosbusch.lumpi.util.LumpiUtil;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
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

    protected final Form.Section createSection(Class<?> beanClass, PropertyDescriptor[] propDescs)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Form.Section result = new Form.Section();
        String sectHeading = getSectionHeading();
        Map<String, Class<?>> propertyMap = new HashMap<>();
        propertyMap.setComparator(Comparator.naturalOrder());

        if ((isShowSectionHeading()) && (StringUtils.isNotBlank(sectHeading))) {
            result.setHeading(sectHeading);
        }

        for (PropertyDescriptor propDesc : propDescs) {
            propertyMap.put(propDesc.getName(), propDesc.getPropertyType());
        }

        for (String propertyName : propertyMap) {
            Class<?> propertyClass = ClassUtils.primitiveToWrapper(
                    propertyMap.get(propertyName));

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

    protected final void removeSections() {
        LumpiUtil.clearSequence(getSections());
    }

    protected final Map<String, FormEditor<? extends Component>> getEditors() {
        return editors;
    }

    protected final Map<String, Class<? extends FormEditor<? extends Component>>> getEditorsMap() {
        return editorsMap;
    }

    protected boolean isExcludedProperty(Class<?> beanClass, String propertyName) {
        return FormUtil.isExcludedProperty(beanClass, propertyName, FormUtil.getDefaultIgnoredProperties());
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
        putValidator(Integer.class.getName(), IntegerValidator.class);
        putValidator(Byte.class.getName(), ByteValidator.class);
        putValidator(Short.class.getName(), ShortValidator.class);
        putValidator(Float.class.getName(), FloatValidator.class);
        putValidator(Double.class.getName(), DoubleValidator.class);
    }

    protected ScrollPane createScrollPane() {
        return new FillScrollPane();
    }

    protected PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass) {
        return PropertyUtils.getPropertyDescriptors(beanClass);
    }

    public final FormEditor<? extends Component> getEditor(String beanClassName,
            String propertyClassName, String propertyName) {
        String formFieldName = AbstractDynamicForm.createFormFieldName(
                beanClassName, propertyName);
        FormEditor<? extends Component> result = null;
        Class<? extends FormEditor<? extends Component>> editorClass
                = lookupEditorClass(formFieldName, propertyClassName);

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
        Class<? extends FormEditor<? extends Component>> result
                = getEditorsMap().get(formFieldName);

        if (result == null) {
            result = getEditorsMap().get(propertyClassName);
        }

        if (result == null) {
            java.util.Set<Class<?>> superTypes = null;

            try {
                superTypes = LumpiUtil.getSuperTypes(
                        Class.forName(propertyClassName), false, true, true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AbstractDynamicForm.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                if (superTypes != null) {
                    for (Class<?> superType : superTypes) {
                        result = getEditorsMap().get(superType.getName());

                        if (result != null) {
                            break;
                        }
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

        if (editor instanceof AbstractNumberFormEditor<?>) {
            AbstractNumberFormEditor<?> numberEditor
                    = (AbstractNumberFormEditor<?>) editor;
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
                section = createSection(context.getClass(),
                        getPropertyDescriptors(context.getClass()));
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
                StoreValueDelegate<? extends Component> storeValueDelegate
                        = editor.getStoreValueDelegate();
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
