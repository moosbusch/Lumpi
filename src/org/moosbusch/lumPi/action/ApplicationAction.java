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

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.ActionListener;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.Labelable;

/**
 *
 * @author moosbusch
 */
public interface ApplicationAction extends Labelable, Bindable {

    public static final String ENABLED_PROPERTY_NAME = "enabled";

    public void perform();

    public void perform(Component evtSource);

    public void doPerform(Component evtSource);

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public ListenerList<ActionListener> getActionListeners();

}
