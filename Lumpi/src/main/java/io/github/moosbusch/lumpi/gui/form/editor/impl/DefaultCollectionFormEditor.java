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
package io.github.moosbusch.lumpi.gui.form.editor.impl;

import org.apache.pivot.wtk.ListButton;
import io.github.moosbusch.lumpi.gui.form.editor.binding.FormEditorBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.binding.impl.DefaultListButtonBindMapping;
import io.github.moosbusch.lumpi.gui.form.editor.io.StoreValueDelegate;
import io.github.moosbusch.lumpi.gui.form.editor.io.spi.ListButtonStoreValueDelegate;
import io.github.moosbusch.lumpi.gui.form.editor.spi.AbstractListButtonFormEditor;

/**
 *
 * @author moosbusch
 */
public class DefaultCollectionFormEditor extends AbstractListButtonFormEditor<ListButton> {

    @Override
    protected ListButton createComponent() {
        return new ListButton();
    }

//    @Override
//    protected MenuHandler createMenuHandler() {
//        return new MenuHandlerImpl();
//    }

    @Override
    protected StoreValueDelegate<ListButton> createStoreValueDelegate() {
        return new ListButtonStoreValueDelegate(this);
    }

    @Override
    protected FormEditorBindMapping<ListButton> createBindMapping() {
        return new DefaultListButtonBindMapping<>(this);
    }

//    private class MenuHandlerImpl extends MenuHandler.Adapter {
//
//        @Override
//        public boolean configureContextMenu(Component component, Menu menu, int x, int y) {
//            Menu.Section section = new Menu.Section();
//            Menu.Item addItem = new Menu.Item("Add item");
//            Menu.Item removeItem = new Menu.Item("Remove item");
//            addItem.setAction(new AddNewCollectionItemAction(getFormFieldName()));
//            removeItem.setAction(new RemoveCollectionItemAction(getFormFieldName(), getComponent().getSelectedIndex()));
//            section.add(addItem);
//            section.add(removeItem);
//            menu.getSections().add(section);
//
//            return super.configureContextMenu(component, menu, x, y);
//        }
//
//    }

//    private class RemoveCollectionItemAction extends LabelableAction {
//
//        private final String expression;
//        private final int index;
//
//        public RemoveCollectionItemAction(String expression, int index) {
//            this.expression = PivotUtil.requireNotBlank(expression);
//            this.index = index;
//        }
//
//        @Override
//        public void perform(Component source) {
//            Object val = getValue();
//            Object propertyValue = null;
//
//            try {
//                propertyValue = PropertyUtils.getProperty(val, expression);
//            } catch (IllegalAccessException |
//                    InvocationTargetException |
//                    NoSuchMethodException ex) {
//                Logger.getLogger(BeanViewBreadCrumbBar.class.getName()).log(
//                        Level.SEVERE, null, ex);
//            } finally {
//                if (propertyValue != null) {
//                    Class<?> propertyType = propertyValue.getClass();
//
//                    if (PivotUtil.isCollection(propertyType)) {
//                        new CollectionHandler.Adapter<Object, Void>() {
//                            @Override
//                            public void process(List<Object> list) {
//                                list.remove(index);
//                            }
//
//                            @Override
//                            public void process(Sequence<Object> seq) {
//                                seq.remove(index, 1);
//                            }
//                        }.processCollectionObject(propertyValue);
//                    }
//                }
//            }
//        }
//    }
//
//    private class AddNewCollectionItemAction extends LabelableAction {
//
//        private final String expression;
//
//        public AddNewCollectionItemAction(String expression) {
//            this.expression = PivotUtil.requireNotBlank(expression);
//        }
//
//        @Override
//        public void perform(Component source) {
//            Object val = getValue();
//            Object propertyValue = null;
//
//            try {
//                propertyValue = PropertyUtils.getProperty(val, expression);
//            } catch (IllegalAccessException |
//                    InvocationTargetException |
//                    NoSuchMethodException ex) {
//                Logger.getLogger(BeanViewBreadCrumbBar.class.getName()).log(
//                        Level.SEVERE, null, ex);
//            } finally {
//                if (propertyValue != null) {
//                    Class<?> propertyType = propertyValue.getClass();
//
//                    if (PivotUtil.isCollection(propertyType)) {
//                        Collection<Type> types =
//                                PivotUtil.getParameterTypesForCollection(propertyType);
//                        Object instance = null;
//                        Class<?> typeClass = types.toArray(new Class<?>[0])[0];
//
//                        try {
//                            instance = typeClass.newInstance();
//                        } catch (Exception ex) {
//                            Logger.getLogger(AbstractBeanView.class.getName()).log(
//                                    Level.SEVERE, null, ex);
//                        } finally {
//                            try {
//                                MethodUtils.invokeMethod(propertyValue, "add", instance);
//                            } catch (NoSuchMethodException | IllegalAccessException |
//                                    InvocationTargetException ex) {
//                                Logger.getLogger(AbstractBeanView.class.getName()).log(
//                                        Level.SEVERE, null, ex);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
