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
package org.moosbusch.lumPi.action;

import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableSheet;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface SheetAction<T extends BindableWindow, V extends AbstractSubmitableSheet<?>>
    extends ChildWindowAction<T, V>, SheetCloseListener {

    public static abstract class Adapter<T extends BindableWindow, V extends AbstractSubmitableSheet<?>>
            extends ChildWindowAction.Adapter<T, V> implements SheetAction<T, V> {

        @Override
        public final void openChildWindow(T applicationWindow, V childWindow) {
            childWindow.open(applicationWindow, this);
        }

        @Override
        @SuppressWarnings("unchecked")
        public final void sheetClosed(Sheet sheet) {
            T parent = getApplicationWindow();
            V child = getChildWindow();

            if (child.isCanceled()) {
                onChildWindowCanceled(parent, child);
            } else if (child.isSubmitted()) {
                onChildWindowSubmitted(parent, child);
            }
        }
    }
}