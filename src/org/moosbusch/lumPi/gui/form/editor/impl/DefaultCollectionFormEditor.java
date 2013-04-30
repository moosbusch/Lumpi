/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuHandler;
import org.moosbusch.lumPi.action.spi.LabelableAction;
import org.moosbusch.lumPi.collections.CollectionHandler;
import org.moosbusch.lumPi.gui.component.impl.BeanViewBreadCrumbBar;
import org.moosbusch.lumPi.gui.component.spi.AbstractBeanView;
import org.moosbusch.lumPi.gui.form.editor.binding.FormEditorBindMapping;
import org.moosbusch.lumPi.gui.form.editor.binding.impl.DefaultListButtonBindMapping;
import org.moosbusch.lumPi.gui.form.editor.io.StoreValueDelegate;
import org.moosbusch.lumPi.gui.form.editor.io.spi.ListButtonStoreValueDelegate;
import org.moosbusch.lumPi.gui.form.editor.spi.AbstractListButtonFormEditor;
import org.moosbusch.lumPi.util.PivotUtil;

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
