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

package org.moosbusch.lumPi.gui.component.impl;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.PushButton;
import org.moosbusch.lumPi.action.ApplicationAction;
import org.moosbusch.lumPi.gui.renderer.impl.DefaultButtonDataRenderer;
import org.moosbusch.lumPi.util.LumPiUtil;

/**
 *
 * @author Gunnar Kappei
 */
public class DefaultPushButton extends PushButton {

    public DefaultPushButton() {
        init();
    }

    public DefaultPushButton(boolean toggleButton) {
        super(toggleButton);
        init();
    }

    public DefaultPushButton(Object buttonData) {
        super(buttonData);
        init();
    }

    public DefaultPushButton(boolean toggleButton, Object buttonData) {
        super(toggleButton, buttonData);
        init();
    }

    private void init() {
        setDataRenderer(new DefaultButtonDataRenderer());
    }

    @Override
    public void setAction(Action action) {
        super.setAction(action);

        if (LumPiUtil.isInstanceOf(action, ApplicationAction.class)) {
            ApplicationAction appAction = (ApplicationAction) action;
            setButtonData(appAction.getButtonData());
        }
    }

}
