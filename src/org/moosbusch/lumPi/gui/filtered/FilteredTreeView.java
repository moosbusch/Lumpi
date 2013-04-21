/*

 *
 */
package org.moosbusch.lumPi.gui.filtered;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.util.Filter;
import org.apache.pivot.wtk.TreeView;

/**
 *
 * @author moosbusch
 */
public class FilteredTreeView extends TreeView {

    public FilteredTreeView(List<?> treeData) {
        super(treeData);
    }

    public FilteredTreeView() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setTreeData(List<?> treeData) {
        Filter<Object> filter = (Filter<Object>) getDisabledNodeFilter();

        if (filter != null) {
            List<Object> filteredTreeData = new ArrayList<>();

            for (Object item : treeData) {
                if (!filter.include(item)) {
                    filteredTreeData.add(item);
                }
            }

            super.setTreeData(filteredTreeData);
        } else {
            super.setTreeData(treeData);
        }
    }
}
