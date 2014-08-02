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
package org.moosbusch.lumPi.gui.dialog.impl;

import java.io.File;
import java.net.URL;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableFileBrowserSheet;

/**
 *
 * @author moosbusch
 */
public class DefaultSubmitableFileBrowserSheet extends AbstractSubmitableFileBrowserSheet {

    @Override
    public void onCancel() {
    }

    @Override
    public void onSubmit(Sequence<File> value) {
    }

    @Override
    public boolean canSubmit(Sequence<File> value) {
        return ((getSelectedFiles() != null) || (getSelectedFile() != null));
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }

}
