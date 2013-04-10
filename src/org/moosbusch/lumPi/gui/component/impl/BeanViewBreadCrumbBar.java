/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.component.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuHandler;
import org.apache.pivot.wtk.content.MenuButtonDataRenderer;
import org.moosbusch.lumPi.action.spi.LabelableAction;
import org.moosbusch.lumPi.collections.CollectionHandler;
import static org.moosbusch.lumPi.gui.Selectable.SELECTION_PROPERTY;
import org.moosbusch.lumPi.gui.Selection;
import org.moosbusch.lumPi.gui.component.BeanViewComponent;
import org.moosbusch.lumPi.gui.component.spi.AbstractBeanView;
import org.moosbusch.lumPi.util.FormUtil;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public class BeanViewBreadCrumbBar extends BreadCrumbBar<Object> implements BeanViewComponent<Object> {

    private final AbstractBeanView<?, ?> beanView;

    public BeanViewBreadCrumbBar(AbstractBeanView<?, ?> beanView) {
        this.beanView = Objects.requireNonNull(beanView);
        init();
    }

    private void init() {
        getMonitor().getPropertyChangeListeners().add(
                new PropertyChangeListenerImpl());
    }

    @Override
    protected MenuButtonDataRenderer createValueDataRenderer() {
        return new MenuButtonDataRenderer() {
            @Override
            public void render(Object data, Button button, boolean highlight) {
                Object val = getValue();

                if (val != null) {
                    super.render(getValue().getClass().getSimpleName(), button, highlight);
                } else {
                    super.render(data, button, highlight);
                }
            }
        };
    }

    @Override
    protected MenuHandler createMenuHandler() {
        return new MenuHandlerImpl();
    }

    protected boolean isComplexExpressionToken(int subTokenIndex) {
        boolean result = false;
        Object bean = getValue();

        if (bean != null) {
            Class<?> propType = bean.getClass();
            String expr = getSelection().getExpression().getSubExpression(subTokenIndex);

            if (!PivotUtil.isPrimitiveTypeOrString(propType)) {
                Object subPropertyValue = null;

                try {
                    subPropertyValue = PropertyUtils.getProperty(bean,
                            expr);
                } catch (IllegalAccessException |
                        InvocationTargetException |
                        NoSuchMethodException ex) {
                    Logger.getLogger(BeanViewBreadCrumbBar.class.getName()).log(
                            Level.SEVERE, null, ex);
                } finally {
                    if (subPropertyValue != null) {
                        if (PivotUtil.isPrimitiveTypeOrString(subPropertyValue.getClass())) {
                            return false;
                        } else {
                            result = PivotUtil.isCollection(subPropertyValue.getClass());
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    protected Menu createValuePopupMenu(Object val) {
        if (val != null) {
            Menu result = new Menu();
            Menu.Section section = new Menu.Section();
            Class<?> propertyType = val.getClass();
            java.util.Map<String, Class<?>> propertiesMap =
                    FormUtil.getPropertyTypesMap(propertyType,
                    FormUtil.getDefaultIgnoredProperties());
            Set<String> subPropertyNames =
                    new TreeSet<>(propertiesMap.keySet());

            for (String subPropertyName : subPropertyNames) {
                if (!getBeanView().isExcludedProperty(propertyType, subPropertyName)) {
                    Class<?> subPropertyType = PivotUtil.getPropertyType(
                            propertyType, subPropertyName);

                    if (subPropertyType != null) {
                        if (!PivotUtil.isPrimitiveTypeOrString(subPropertyType)) {
                            Menu.Item propertyItem = new Menu.Item(subPropertyName);

                            if (PivotUtil.isCollection(subPropertyType)) {
                                propertyItem.setMenu(new CollectionMenu(getSelection().getExpression().toString()));
                            }

                            section.add(propertyItem);
                        }
                    }
                }
            }


            result.getSections().add(section);

            return result;
        }

        return super.createValuePopupMenu(val);
    }

    @Override
    protected Menu createBreadCrumbPopupMenu(int subTokenIndex) {
        Menu result = new Menu();
        Menu.Section section = new Menu.Section();
        Selection<Object> sel = getSelection();

        if (isComplexExpressionToken(subTokenIndex)) {
            return new CollectionMenu(sel.getExpression().toString());
        } else {
            Object propertyValue = null;

            try {
                propertyValue = sel.evaluate();
            } catch (Exception ex) {
                Logger.getLogger(BeanViewBreadCrumbBar.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                if (propertyValue != null) {
                    Class<?> propertyType = propertyValue.getClass();
                    java.util.Map<String, Class<?>> propertiesMap =
                            FormUtil.getPropertyTypesMap(propertyType,
                            FormUtil.getDefaultIgnoredProperties());
                    Set<String> subPropertyNames =
                            new TreeSet<>(propertiesMap.keySet());

                    for (String subPropertyName : subPropertyNames) {
                        if (!getBeanView().isExcludedProperty(propertyType, subPropertyName)) {
                            Class<?> subPropertyType = PivotUtil.getPropertyType(
                                    propertyType, subPropertyName);

                            if (subPropertyType != null) {
                                if (!PivotUtil.isPrimitiveTypeOrString(subPropertyType)) {
                                    Menu.Item propertyItem = new Menu.Item(subPropertyName);

                                    if (PivotUtil.isCollection(subPropertyType)) {
                                        propertyItem.setMenu(
                                                new CollectionMenu(
                                                sel.getExpression().toString()));
                                    }

                                    section.add(propertyItem);
                                }
                            }
                        }
                    }
                }
            }
        }

        result.getSections().add(section);

        return result;
    }

    @Override
    public AbstractBeanView<?, ?> getBeanView() {
        return beanView;
    }

    private class MenuHandlerImpl extends MenuHandler.Adapter {

        @Override
        public boolean configureContextMenu(Component component, Menu menu, int x, int y) {
            return false;
        }
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (StringUtils.equalsIgnoreCase(propertyName, SELECTION_PROPERTY)) {
                Selection<Object> sel = getSelection();
                sel.setEventSource(BeanViewBreadCrumbBar.this);
                getBeanView().setSelection(sel);
            }
        }
    }

    private class CollectionItemMenu extends Menu {

        private final String expression;

        public CollectionItemMenu(String expression) {
            this.expression = PivotUtil.requireNotBlank(expression);
            init();
        }

        private void init() {
        }
    }

    private class CollectionMenu extends Menu {

        private final String expression;

        public CollectionMenu(String expression) {
            this.expression = PivotUtil.requireNotBlank(expression);
            init();
        }

        private void init() {
            Menu.Section section = new Menu.Section();
            Menu.Item addItem = new Menu.Item("Add item");
            Menu.Item removeItem = new Menu.Item("Remove item");
            addItem.setAction(new AddNewCollectionItemAction(expression));
            removeItem.setMenu(new CollectionItemMenu(expression));
            section.add(addItem);
            section.add(removeItem);
            getSections().add(section);
        }
    }

    private class RemoveCollectionItemAction extends LabelableAction {

        private final String expression;
        private final int index;

        public RemoveCollectionItemAction(String expression, int index) {
            this.expression = PivotUtil.requireNotBlank(expression);
            this.index = index;
        }

        @Override
        public void perform(Component source) {
            Object val = getValue();
            Object propertyValue = null;

            try {
                propertyValue = PropertyUtils.getProperty(val, expression);
            } catch (IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException ex) {
                Logger.getLogger(BeanViewBreadCrumbBar.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                if (propertyValue != null) {
                    Class<?> propertyType = propertyValue.getClass();

                    if (PivotUtil.isCollection(propertyType)) {
                        new CollectionHandler.Adapter<Object, Void>() {
                            @Override
                            public void process(List<Object> list) {
                                list.remove(index);
                            }

                            @Override
                            public void process(Sequence<Object> seq) {
                                seq.remove(index, 1);
                            }
                        }.processCollectionObject(propertyValue);
                    }
                }
            }
        }
    }

    private class AddNewCollectionItemAction extends LabelableAction {

        private final String expression;

        public AddNewCollectionItemAction(String expression) {
            this.expression = PivotUtil.requireNotBlank(expression);
        }

        @Override
        public void perform(Component source) {
            Object val = getValue();
            Object propertyValue = null;

            try {
                propertyValue = PropertyUtils.getProperty(val, expression);
            } catch (IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException ex) {
                Logger.getLogger(BeanViewBreadCrumbBar.class.getName()).log(
                        Level.SEVERE, null, ex);
            } finally {
                if (propertyValue != null) {
                    Class<?> propertyType = propertyValue.getClass();

                    if (PivotUtil.isCollection(propertyType)) {
                        Collection<Type> types =
                                PivotUtil.getParameterTypesForCollection(propertyType);
                        Object instance = null;
                        Class<?> typeClass = types.toArray(new Class<?>[0])[0];

                        try {
                            instance = getBeanView().createBeanInstance(typeClass);
                        } catch (Exception ex) {
                            Logger.getLogger(AbstractBeanView.class.getName()).log(
                                    Level.SEVERE, null, ex);
                        } finally {
                            try {
                                MethodUtils.invokeMethod(propertyValue, "add", instance);
                            } catch (NoSuchMethodException | IllegalAccessException |
                                    InvocationTargetException ex) {
                                Logger.getLogger(AbstractBeanView.class.getName()).log(
                                        Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }
}
