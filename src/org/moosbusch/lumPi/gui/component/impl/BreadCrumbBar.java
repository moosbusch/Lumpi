/*

 *
 */
package org.moosbusch.lumPi.gui.component.impl;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuButton;
import org.apache.pivot.wtk.MenuHandler;
import org.apache.pivot.wtk.Mouse;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.content.MenuButtonDataRenderer;
import org.moosbusch.lumPi.gui.Selectable;
import org.moosbusch.lumPi.gui.Selection;
import org.moosbusch.lumPi.gui.component.ValueHolder;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public class BreadCrumbBar<T extends Object> extends TablePane
        implements ValueHolder<T>, Selectable<T> {

    private final ComponentMouseButtonListener breadCrumbButtonListener;
    private final BeanMonitor monitor;
    private final Row row;
    private final MenuButton rootMenuButton;
    private T value;
    private Selection<T> selection;

    public BreadCrumbBar() {
        this.monitor = new BeanMonitor(this);
        this.breadCrumbButtonListener = new MouseButtonListenerImpl();
        this.rootMenuButton = new MenuButton();
        this.row = new Row();
        init();
    }

    private void init() {
        PivotUtil.setComponentStyle(this, "horizontalSpacing", 5);
        monitor.getPropertyChangeListeners().add(new PropertyChangeListenerImpl());
        setMenuHandler(initMenuHandler());
        MenuButtonDataRenderer dataRenderer =
                Objects.requireNonNull(createValueDataRenderer());
        rootMenuButton.setDataRenderer(dataRenderer);
        rootMenuButton.getComponentMouseButtonListeners().add(
                breadCrumbButtonListener);
        row.add(rootMenuButton);
        getColumns().add(new Column());
        getRows().add(row);
    }

    private MenuHandler initMenuHandler() {
        return Objects.requireNonNull(createMenuHandler());
    }

    private void clearRow() {
        row.remove(1, row.getLength() - 1);
    }

    private void clearColumns() {
        getColumns().remove(1, getColumns().getLength() - 1);
    }

    private void buildUI() {
        Selection<T> sel = getSelection();
        clearColumns();
        clearRow();

        for (String exprToken : sel.getExpression()) {
            int subTokenIndex = sel.getExpression().indexOf(exprToken) + 1;
            MenuButton expressionButton = Objects.requireNonNull(
                    initBreadCrumbButton(subTokenIndex));
            MenuButtonDataRenderer dataRenderer = Objects.requireNonNull(
                    createBreadCrumbDataRenderer(subTokenIndex));

            expressionButton.getComponentMouseButtonListeners().add(
                    breadCrumbButtonListener);
            expressionButton.setDataRenderer(dataRenderer);
            Menu popupMenu = createBreadCrumbPopupMenu(subTokenIndex);

            if (popupMenu != null) {
                expressionButton.setMenu(popupMenu);
                getMenuHandler().configureContextMenu(expressionButton,
                        popupMenu, expressionButton.getX(),
                        expressionButton.getY() + expressionButton.getHeight());
            }

            getColumns().add(new Column());
            row.add(expressionButton);
        }

        rootMenuButton.setMenu(createValuePopupMenu(getValue()));
    }

    private MenuButton initBreadCrumbButton(int subTokenIndex) {
        MenuButton result = createBreadCrumbButton(subTokenIndex);
        result.setButtonData(new ButtonData(getSelection().getExpression().toString()));
        return result;
    }

    protected MenuHandler createMenuHandler() {
        return new MenuHandler.Adapter();
    }

    protected MenuButtonDataRenderer createValueDataRenderer() {
        return new MenuButtonDataRenderer();
    }

    protected MenuButtonDataRenderer createBreadCrumbDataRenderer(int subTokenIndex) {
        return new MenuButtonDataRenderer() {
            @Override
            public void render(Object data, Button button, boolean highlight) {
                int tokenIndex = row.indexOf(button) - 1;
                String caption = getSelection().getExpression().get(tokenIndex);
                super.render(caption, button, highlight);
            }
        };
    }

    protected MenuButton createBreadCrumbButton(int subTokenIndex) {
        return new MenuButton();
    }

    protected Menu createBreadCrumbPopupMenu(int subTokenIndex) {
        return null;
    }

    protected Menu createValuePopupMenu(T val) {
        return null;
    }

    @Override
    public BeanMonitor getMonitor() {
        return monitor;
    }

    @Override
    public final T getValue() {
        return value;
    }

    @Override
    public final void setValue(T value) {
        this.value = value;
        PivotUtil.firePropertyChangeListeners(this, VALUE_PROPERTY, monitor);
    }

    @Override
    public Selection<T> getSelection() {
        return selection;
    }

    @Override
    public void setSelection(Selection<T> selItem) {
        this.selection = selItem;
        PivotUtil.firePropertyChangeListeners(this,
                SELECTION_PROPERTY, monitor);
    }

    private class MouseButtonListenerImpl extends ComponentMouseButtonListener.Adapter {

        @Override
        public boolean mouseClick(Component component, Mouse.Button button,
                int x, int y, int count) {
            int buttonIndex = row.indexOf(component);
            int buttonCount = row.getLength();

            if (buttonIndex < buttonCount) {
                row.remove(buttonIndex + 1, buttonCount - buttonIndex - 1);
            }

            Selection<T> sel = getSelection();

            if (sel != null) {
                if (buttonIndex > 0) {
                    String newExpr = sel.getExpression().getSubExpression(buttonIndex);
                    setSelection(new Selection.Adapter<>(BreadCrumbBar.this,
                            getValue(), new Selection.Expression(newExpr)));
                } else {
                    setSelection(new Selection.Adapter<>(BreadCrumbBar.this,
                            getValue(), new Selection.Expression()));
                }
            }

            return true;
        }
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (StringUtils.equalsIgnoreCase(propertyName, SELECTION_PROPERTY)) {
                buildUI();
            } else if (StringUtils.equalsIgnoreCase(propertyName, VALUE_PROPERTY)) {
                if (getValue() == null) {
                    rootMenuButton.setButtonData(new ButtonData());
                } else {
                    rootMenuButton.setButtonData(
                            new ButtonData(getValue().toString()));
                }
            }
        }
    }
}
