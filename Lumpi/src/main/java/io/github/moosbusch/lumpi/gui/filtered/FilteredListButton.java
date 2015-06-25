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
