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
package org.moosbusch.lumPi.gui.window.spi;

import java.net.URL;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Frame;

/**
 *
 * @author moosbusch
 */
public abstract class BindableFrame extends Frame implements Bindable {

    public BindableFrame() {
    }

    public BindableFrame(String title) {
        super(title);
    }

    public BindableFrame(Component content) {
        super(content);
    }

    public BindableFrame(String title, Component content) {
        super(title, content);
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }

}
