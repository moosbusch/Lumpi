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

package io.github.moosbusch.lumpi.action.spi;

import java.util.Objects;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.HorizontalAlignment;
import org.apache.pivot.wtk.VerticalAlignment;
import org.apache.pivot.wtk.Window;
import io.github.moosbusch.lumpi.action.ChildWindowAction;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractChildWindowAction<T extends BindableWindow, V extends Window>
    extends AbstractApplicationAction implements ChildWindowAction<T, V> {

    @Autowired
    private T applicationWindow;
    private V childWindow;

    @Override
    public final void doPerform(Component evtSource) {
        V chldWindow = Objects.requireNonNull(getChildWindow());
        T appWindow = Objects.requireNonNull(getApplicationWindow());
        chldWindow.align(appWindow.getBounds(), HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        onChildWindowShowing(appWindow, chldWindow);
        openChildWindow(appWindow, chldWindow);
    }

    @Override
    public void onChildWindowShowing(T applicationWindow, V childWindow) {
    }

    @Override
    public void onChildWindowSubmitted(T applicationWindow, V childWindow) {
    }

    @Override
    public void onChildWindowCanceled(T applicationWindow, V childWindow) {
    }

    @Override
    public final T getApplicationWindow() {
        return applicationWindow;
    }

    @Override
    public V getChildWindow() {
        return childWindow;
    }

    @Override
    public void setChildWindow(V childWindow) {
        this.childWindow = childWindow;
        firePropertyChange(ChildWindowAction.CHILD_WINDOW_PROPERTY_NAME);
    }

}
