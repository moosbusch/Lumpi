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
package io.github.moosbusch.lumpi.gui.filtered;

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
