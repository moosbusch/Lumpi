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

import java.util.Objects;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ScrollPane;

/**
 *
 * @author moosbusch
 */
public class FillScrollPane extends ScrollPane {

    public FillScrollPane() {
        super(ScrollPane.ScrollBarPolicy.FILL_TO_CAPACITY,
              ScrollPane.ScrollBarPolicy.FILL_TO_CAPACITY);
    }

    public FillScrollPane(Component content) {
        this();
        init(Objects.requireNonNull(content));
    }

    private void init(Component content) {
        setView(content);
    }

}
