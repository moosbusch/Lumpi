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
package org.moosbusch.lumPi.gui;

import org.moosbusch.lumPi.gui.component.ValueHolder;

/**
 *
 * @author moosbusch
 */
public interface Selectable<T extends Object> extends ValueHolder<T> {

    public static final String SELECTION_PROPERTY = "selection";

    public Selection<T> getSelection();

    public void setSelection(Selection<T> selItem);

    public static class Adapter<T extends Object> extends ValueHolder.Adapter<T>
            implements Selectable<T> {

        private Selection<T> selItem;

        @Override
        public Selection<T> getSelection() {
            return selItem;
        }

        @Override
        public void setSelection(Selection<T> selItem) {
            this.selItem = selItem;
            firePropertyChange(SELECTION_PROPERTY);
        }


    }
}
