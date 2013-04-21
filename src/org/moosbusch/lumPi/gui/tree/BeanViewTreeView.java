/*

 *
 */
package org.moosbusch.lumPi.gui.tree;

import java.net.URL;
import java.util.Objects;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.TreeViewSelectionListener;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.moosbusch.lumPi.gui.Selectable;
import static org.moosbusch.lumPi.gui.Selectable.SELECTION_PROPERTY;
import org.moosbusch.lumPi.gui.Selection;
import org.moosbusch.lumPi.gui.component.BeanViewComponent;
import org.moosbusch.lumPi.gui.component.spi.AbstractBeanView;
import org.moosbusch.lumPi.task.spi.ApplicationTask;
import org.moosbusch.lumPi.util.FormUtil;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public class BeanViewTreeView extends TreeView implements BeanViewComponent<Object>,
        Selectable<Object> {

    private static final long serialVersionUID = 6707884409040717259L;
    private final BeanMonitor monitor;
    private final AbstractBeanView<?, ?> beanView;
    private Selection<Object> selection;

    public BeanViewTreeView(AbstractBeanView<?, ?> beanView) {
        this.monitor = new BeanMonitor(this);
        this.beanView = Objects.requireNonNull(beanView);
        init();
    }

    private void init() {
        setSelectMode(SelectMode.SINGLE);
        getMonitor().getPropertyChangeListeners().add(new PropertyChangeListenerImpl());
    }

    private void fromBean(Object bean) {
        clear();

        new BuildViewTask(bean).runTask();
    }

    @Override
    public BeanMonitor getMonitor() {
        return monitor;
    }

    @Override
    public AbstractBeanView<?, ?> getBeanView() {
        return beanView;
    }

    @Override
    public Object getValue() {
        return getBeanView().getValue();
    }

    @Override
    public final void setValue(Object bean) {
        fromBean(getBeanView().getValue());
    }

    @Override
    public Selection<Object> getSelection() {
        return selection;
    }

    @Override
    public void setSelection(Selection<Object> selItem) {
        this.selection = selItem;
        PivotUtil.firePropertyChangeListeners(this,
                SELECTION_PROPERTY, getMonitor());
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (StringUtils.equalsIgnoreCase(propertyName, SELECTION_PROPERTY)) {
            }
        }
    }

    private class BuildViewTask extends ApplicationTask<List<TreeNode>> {

        private final Class<?> beanType;
        private final TreeBranch parent;

        public BuildViewTask(Object bean) {
            this.parent = new TreeBranch(bean.getClass().getName());
            this.beanType = bean.getClass();
            init();
        }

        private void init() {
            getTreeViewSelectionListeners().add(
                    new TreeViewSelectionListenerImpl());
        }

        private TreeBranch createTreeBranch(String propertyName,
                String propertyTypeName,
                java.util.Map<String, Class<?>> typeProperties) {
            TreeBranch result = new TreeBranch(propertyName);
            result.setUserData(propertyTypeName);

            for (String propName : typeProperties.keySet()) {
                Class<?> propType = typeProperties.get(propName);
                String propTypeName = propType.getName();
                final TreeBranch node;

                if (getBeanView().isExcludedProperty(propType, propName)
                        || getBeanView().isExcludedType(propType)
                        || PivotUtil.isPrimitiveTypeOrString(propType)) {
                    continue;
                } else {
                    node = createTreeBranch(propName, propTypeName,
                            FormUtil.getPropertyTypesMap(propType));
                }

                result.add(node);
            }

            return result;
        }

        @Override
        public List<TreeNode> execute() throws TaskExecutionException {
            final List<TreeNode> result = new ArrayList<>();

            if (ClassUtils.isPrimitiveOrWrapper(beanType)) {
                throw new IllegalArgumentException("Primitive-types are not allowed!");
            }

            java.util.Map<String, Class<?>> propsMap =
                    FormUtil.getPropertyTypesMap(beanType,
                    FormUtil.getDefaultIgnoredProperties());

            for (String propName : propsMap.keySet()) {
                synchronized (BeanViewTreeView.this) {
                    if (!getBeanView().isExcludedProperty(beanType, propName)) {
                        Class<?> propType = propsMap.get(propName);
                        String propTypeName = propType.getName();
                        TreeBranch node;

                        if ((PivotUtil.isPrimitiveTypeOrString(propType))
                                || (PivotUtil.isCollectionOrSequenceOrArrayOrEnum(propType))) {
                            continue;
                        } else {
                            java.util.Map<String, Class<?>> subPropsMap =
                                    FormUtil.getPropertyTypesMap(propType);

                            node = createTreeBranch(
                                    propName, propTypeName, subPropsMap);
                        }

                        result.add(node);
                    }
                }
            }

            return result;
        }

        @Override
        public void taskExecutedImpl(Task<List<TreeNode>> task) {
            TreeBranch rootNode = new TreeBranch();

            for (TreeNode node : task.getResult()) {
                parent.add(node);
            }

            if (parent.getParent() == null) {
                rootNode.add(parent);
                setTreeData(new ArrayList<>(rootNode));
            }
        }

        @Override
        public void executeFailedImpl(Task<List<TreeNode>> task) {
            clear();
        }

        @Override
        public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        }
    }

    private class TreeViewSelectionListenerImpl extends TreeViewSelectionListener.Adapter {

        private boolean isRootNode(TreeNode node) {
            return (node.getParent() == null);
        }

        private boolean isBeanNode(TreeNode node) {
            TreeNode parent = node.getParent();

            if (parent != null) {
                return isRootNode(parent);
            }

            return false;
        }

        private String getPropertyNameLazy(TreeNode node) {
            String result = node.getText();

            if (StringUtils.isBlank(result)) {
                throw new IllegalStateException("Property-name cannot be resolved!");
            } else {
                TreeNode parent = node.getParent();

                while (parent != null) {
                    if (!isBeanNode(parent)) {
                        String propName = parent.getText();

                        if (StringUtils.isBlank(propName)) {
                            throw new IllegalStateException("Property-name cannot be resolved!");
                        } else {
                            result = StringUtils.join(propName, ".", result);
                        }
                    } else {
                        break;
                    }

                    parent = parent.getParent();
                }
            }

            return result;
        }

        @Override
        public void selectedNodeChanged(TreeView treeView, Object previousSelectedNode) {
            Object selObj = treeView.getSelectedNode();

            if (selObj != null) {
                if (selObj instanceof TreeNode) {
                    TreeNode selNode = (TreeNode) selObj;

                    if (!isBeanNode(selNode)) {
                        String propertyName = getPropertyNameLazy(selNode);

                        getBeanView().setSelection(new Selection.Adapter<>(
                                treeView, getValue(), propertyName));
                    } else {
                        getBeanView().setSelection(new Selection.Adapter<>(
                                treeView, getValue(), ""));
                    }
                }
            }
        }
    }
}
