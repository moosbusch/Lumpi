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
package io.github.moosbusch.lumpi.gui.menu.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;

/**
 *
 * @author moosbusch
 */
public class ConfigurableMenuHandler extends MenuHandler.Adapter {

    public static final String ACTION_PROPERTY = "action";
    public static final String BUTTON_DATA_PROPERTY = "buttonData";
    public static final String DATA_RENDERER_PROPERTY = "dataRenderer";
    private final Map<Component, Menu> popupMenuMap;
    private final MenuPopup menuPopup;
    private boolean ignoreViewports = true;
    private Reference<Menu> popupMenuRef;

    public ConfigurableMenuHandler() {
        this.popupMenuMap = new HashMap<>();
        this.menuPopup = new MenuPopup();
    }

    public ConfigurableMenuHandler(Menu defaultPopupMenu) {
        this();
        this.popupMenuRef = new WeakReference<>(defaultPopupMenu);
    }

    private Component getEventOrigin(Component component, int x, int y) {
        if (component instanceof Window) {
            Window win = (Window) component;
            Component cmp = win.getDescendantAt(x, y);

            if (cmp != null) {
                if (cmp instanceof Viewport) {
                    if (isIgnoreViewports()) {
                        Viewport view = (Viewport) cmp;
                        cmp = view.getView();
                    }
                }

                return cmp;
            }
        }

        return component;
    }

    protected boolean configureContextMenuImpl(Component component, Menu menu, int x, int y) {
        return false;
    }

    public boolean isIgnoreViewports() {
        return ignoreViewports;
    }

    public void setIgnoreViewports(boolean ignoreViewports) {
        this.ignoreViewports = ignoreViewports;
    }

    public final Menu getDefaultPopupMenu() {
        if (popupMenuRef != null) {
            return popupMenuRef.get();
        }

        return null;
    }

    public final void setDefaultPopupMenu(Menu popupMenu) {
        if (popupMenu != null) {
            this.popupMenuRef = new WeakReference<>(popupMenu);
        }

        this.popupMenuRef = null;
    }

    public final void setPopupMenuForComponent(Menu popupMenu, Component component) {
        if (component != null) {
            popupMenuMap.put(component, popupMenu);
        }
    }

    public final Menu getPopupMenuForComponent(Component component) {
        if (component != null) {
            Menu popup = popupMenuMap.get(component);

            if (popup != null) {
                return popup;
            }
        }

        return getDefaultPopupMenu();
    }

    @Override
    public final boolean configureContextMenu(Component component, Menu menu, int x, int y) {
        Component cmp = getEventOrigin(component, x, y);
        Menu popup = getPopupMenuForComponent(cmp);

        if (popup != null) {
            menuPopup.setMenu(popup);
            menuPopup.open(component.getDisplay(), x, y);

            return configureContextMenuImpl(component, menu, x, y);
        }

        return super.configureContextMenu(component, menu, x, y);
    }
}
