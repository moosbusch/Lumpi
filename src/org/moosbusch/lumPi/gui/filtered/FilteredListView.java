/*

 *
 */
package org.moosbusch.lumPi.gui.filtered;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.util.Filter;
import org.apache.pivot.wtk.ListView;

/**
 *
 * @author moosbusch
 */
public class FilteredListView extends ListView {

    public FilteredListView(List<?> listData) {
        super(listData);
    }

    public FilteredListView() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setListData(List<?> listData) {
        Filter<Object> filter = (Filter<Object>) getDisabledItemFilter();

        if (filter != null) {
            List<Object> filteredListData = new ArrayList<>();

            for (Object item : listData) {
                if (!filter.include(item)) {
                    filteredListData.add(item);
                }
            }

            super.setListData(filteredListData);
        } else {
            super.setListData(listData);
        }
    }
}
