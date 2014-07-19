/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.moosbusch.lumPi.action.spi;

import org.apache.pivot.wtk.FileBrowserSheet;
import org.moosbusch.lumPi.action.FileBrowserAction;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractFileBrowserAction<T extends BindableWindow,
        V extends AbstractSubmitableFileBrowserSheet> extends AbstractSheetAction<T, V>
        implements FileBrowserAction<T, V> {

    private final FileBrowserSheet.Mode sheetMode;

    public AbstractFileBrowserAction(FileBrowserSheet.Mode sheetMode) {
        this.sheetMode = sheetMode;
    }

    public FileBrowserSheet.Mode getSheetMode() {
        return sheetMode;
    }

    @Override
    public final void onChildWindowSubmitted(T applicationWindow, V sheet) {
        getChildWindow().setMode(getSheetMode());
        processFiles(getSheetMode(), sheet.getSelectedFiles());
    }

}
