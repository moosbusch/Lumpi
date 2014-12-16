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
package io.github.moosbusch.lumpi.gui.component.impl;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Orientation;
import io.github.moosbusch.lumpi.util.LumpiUtil;

/**
 *
 * @author moosbusch
 */
public class DefaultButtonBoxPane extends BoxPane {

    public DefaultButtonBoxPane() {
        this(Orientation.HORIZONTAL);
    }

    public DefaultButtonBoxPane(Orientation orientation) {
        super(orientation);
        init();
    }

    private void init() {
        LumpiUtil.setComponentStyle(this, "padding", 10);
        LumpiUtil.setComponentStyle(this, "horizontalAlignment", "right");
        LumpiUtil.setComponentStyle(this, "verticalAlignment", "bottom");
    }

}
