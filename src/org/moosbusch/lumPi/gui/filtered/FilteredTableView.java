/*

 *
 */
package org.moosbusch.lumPi.gui.filtered;

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
