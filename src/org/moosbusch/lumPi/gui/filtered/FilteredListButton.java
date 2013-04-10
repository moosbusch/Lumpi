/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.filtered;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.util.Filter;
import org.apache.pivot.wtk.ListButton;

/**
 *
 * @author moosbusch
 */
public class FilteredListButton extends ListButton {

    public FilteredListButton(Object buttonData, List<?> listData) {
        super(buttonData, listData);
    }

    public FilteredListButton(List<?> listData) {
        super(listData);
    }

    public FilteredListButton(Object buttonData) {
        super(buttonData);
    }

    public FilteredListButton() {
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
