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
import org.apache.pivot.wtk.TableView;

/**
 *
 * @author moosbusch
 */
public class FilteredTableView extends TableView {

    public FilteredTableView(List<?> tableData) {
        super(tableData);
    }

    public FilteredTableView() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setTableData(List<?> tableData) {
        Filter<Object> filter = (Filter<Object>) getDisabledRowFilter();

        if (filter != null) {
            List<Object> filteredTableData = new ArrayList<>();

            for (Object item : tableData) {
                if (!filter.include(item)) {
                    filteredTableData.add(item);
                }
            }

            super.setTableData(filteredTableData);
        } else {
            super.setTableData(tableData);
        }
    }
}
