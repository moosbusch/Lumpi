/*

 *
 */
package org.moosbusch.lumPi.gui.component.spi;

import org.moosbusch.lumPi.gui.component.impl.FillScrollPane;
import java.util.Objects;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.SplitPane;
import org.apache.pivot.wtk.TablePane;
import org.moosbusch.lumPi.gui.Selectable;
import org.moosbusch.lumPi.gui.Selection;
import org.moosbusch.lumPi.gui.component.ValueHolder;
import org.moosbusch.lumPi.gui.component.impl.BeanViewBreadCrumbBar;
import org.moosbusch.lumPi.gui.component.impl.BreadCrumbBar;
import org.moosbusch.lumPi.gui.form.spi.AbstractBeanViewForm;
import org.moosbusch.lumPi.gui.tree.BeanViewTreeView;
import org.moosbusch.lumPi.util.FormUtil;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractBeanView<T extends BeanViewTreeView, F extends AbstractBeanViewForm<Object>>
        extends TablePane implements ValueHolder<Object>, Selectable<Object> {

    private final BeanMonitor monitor;
    private final T beanTreeView;
    private final F form;
    private final BoxPane buttonBoxPane;
    private final SplitPane splitPane;
    private final BeanViewBreadCrumbBar breadCrumbBar;
    private Object value;
    private Selection<Object> selItem;

    public AbstractBeanView() {
        this.monitor = new BeanMonitor(this);
        this.form = Objects.requireNonNull(createForm());
        this.beanTreeView = Objects.requireNonNull(createTreeView());
        this.buttonBoxPane = new BoxPane();
        this.breadCrumbBar = new BeanViewBreadCrumbBar(this);
        this.splitPane = new SplitPane(Orientation.HORIZONTAL);
        init();
    }

    private void init() {
        Column col = new Column();
        Row boxPaneRow = new Row();
        Row splitPaneRow = new Row();

        monitor.getPropertyChangeListeners().add(new PropertyChangeListenerImpl());

        splitPane.setResizeMode(SplitPane.ResizeMode.SPLIT_RATIO);
        splitPane.setLeft(createWrapper(beanTreeView));
        splitPane.setRight(createWrapper(form));
        splitPane.setSplitRatio(0.3f);


        PivotUtil.setComponentStyle(buttonBoxPane, "padding", 10);
        PivotUtil.setComponentStyle(buttonBoxPane, "horizontalAlignment", "left");
        PivotUtil.setComponentStyle(buttonBoxPane, "verticalAlignment", "bottom");
        buttonBoxPane.add(breadCrumbBar);

        boxPaneRow.add(createWrapper(buttonBoxPane));
        boxPaneRow.setHeight("-1");
        splitPaneRow.add(splitPane);
        splitPaneRow.setHeight("1*");

        col.setWidth("1*");
        getColumns().add(col);
        getRows().add(boxPaneRow);
        getRows().add(splitPaneRow);
    }

    private F createForm() {
        return createBeanForm();
    }

    private T createTreeView() {
        return createBeanTreeView();
    }

    private Border createWrapper(Component view) {
        Border result = new Border();
        ScrollPane scroll = new FillScrollPane();
        scroll.setView(view);
        result.setContent(scroll);
        return result;
    }

    protected abstract T createBeanTreeView();

    protected abstract F createBeanForm();

    public abstract <V> V createBeanInstance(Class<?> type) throws Exception;

    protected final T getBeanTreeView() {
        return beanTreeView;
    }

    protected final F getForm() {
        return form;
    }

    protected final BoxPane getButtonBoxPane() {
        return buttonBoxPane;
    }

    protected final BeanViewBreadCrumbBar getBreadCrumbBar() {
        return breadCrumbBar;
    }

    protected final SplitPane getSplitPane() {
        return splitPane;
    }

    @Override
    public BeanMonitor getMonitor() {
        return monitor;
    }

    public Object getPropertyValueLazy(Object bean, Selection.Expression expr)
            throws Exception {
        String currentProperty = new String();

        for (String propName : expr) {
            if (StringUtils.isBlank(currentProperty)) {
                currentProperty = propName;
            } else {
                currentProperty = currentProperty + "." + propName;
            }

            Class<?> propType = PropertyUtils.getPropertyType(bean, currentProperty);
            Object propValue = PropertyUtils.getProperty(bean, currentProperty);

            if (propValue == null) {
                propValue = Objects.requireNonNull(createBeanInstance(propType));
                PropertyUtils.setProperty(bean, currentProperty, propValue);
            }
        }

        return PropertyUtils.getProperty(bean, expr.toString());
    }

    public boolean isExcludedType(Class<?> beanClass) {
        return ArrayUtils.contains(FormUtil.getDefaultIgnoredTypes(), beanClass);
    }

    public boolean isExcludedProperty(Class<?> beanClass, String propertyName) {
        return FormUtil.isExcludedProperty(beanClass, propertyName,
                FormUtil.getDefaultIgnoredProperties());
    }

    @Override
    public Selection<Object> getSelection() {
        return selItem;
    }

    @Override
    public void setSelection(Selection<Object> selItem) {
        this.selItem = selItem;
        PivotUtil.firePropertyChangeListeners(this, SELECTION_PROPERTY, monitor);
    }

    public final void submit() {
        getForm().submit();
    }

    public final void cancel() {
        getForm().cancel();
    }

    @Override
    public final Object getValue() {
        return value;
    }

    @Override
    public final void setValue(Object value) {
        this.value = value;
        PivotUtil.firePropertyChangeListeners(this, VALUE_PROPERTY, monitor);
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            BreadCrumbBar<Object> bread = getBreadCrumbBar();
            T tree = getBeanTreeView();
            F form = getForm();

            if (StringUtils.equals(propertyName, VALUE_PROPERTY)) {
                bread.setValue(getValue());
                tree.setValue(getValue());
                form.setValue((Object) getValue());
            } else if (StringUtils.equals(propertyName, SELECTION_PROPERTY)) {
                Selection<Object> sel = getSelection();
                Component evtSrc = sel.getEventSource();

                if (!(evtSrc == bread)) {
                    bread.setSelection(sel);
                }

                if (!(evtSrc == form)) {
                    form.setSelection(sel);
                }

                if (!(evtSrc == tree)) {
                    tree.setSelection(sel);
                }
            }
        }
    }
}
