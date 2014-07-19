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
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Frame;
import org.moosbusch.lumPi.beans.SmartBindable;
import org.moosbusch.lumPi.beans.impl.SmartBindableAdapter;

/**
 *
 * @author moosbusch
 */
public abstract class BindableFrame extends Frame implements SmartBindable {

    private final SmartBindableAdapter sba;

    public BindableFrame() {
        this.sba = new SmartBindableAdapter(this);
    }

    public BindableFrame(String title) {
        super(title);
        this.sba = new SmartBindableAdapter(this);
    }

    public BindableFrame(Component content) {
        super(content);
        this.sba = new SmartBindableAdapter(this);
    }

    public BindableFrame(String title, Component content) {
        super(title, content);
        this.sba = new SmartBindableAdapter(this);
    }

    @Override
    public boolean isInitialized() {
        return sba.isInitialized();
    }

    @Override
    public void setInitialized(boolean initialized) {
        sba.setInitialized(initialized);
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }

}
