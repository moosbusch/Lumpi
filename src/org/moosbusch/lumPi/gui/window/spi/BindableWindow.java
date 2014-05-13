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

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.WindowStateListener;
import org.moosbusch.lumPi.beans.PropertyChangeAware;

/**
 *
 * @author moosbusch
 */
public abstract class BindableWindow extends Window implements Bindable, PropertyChangeAware {

    public static final String RESIZABLE_PROPERTY_NAME = "resizable";
    private final PropertyChangeAware.Adapter pca;
    private boolean resizable = true;
    private Display display;

    public BindableWindow() {
        this(null);
    }

    public BindableWindow(Component content) {
        super(content);
        this.pca = new PropertyChangeAware.Adapter(this);
        init();
    }

    private void init() {
        setDoubleBuffered(true);
    }

    public void open() {
        super.open(getDisplay());
    }

    @Override
    public final Display getDisplay() {
        if (display != null) {
            return display;
        }

        return super.getDisplay();
    }

    public final void setDisplay(Display display) {
        this.display = display;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        firePropertyChange(RESIZABLE_PROPERTY_NAME);
    }

    public final java.awt.Window getHostWindow() {
        return getDisplay().getHostWindow();
    }

    @Override
    public final BeanMonitor getMonitor() {
        return pca.getMonitor();
    }

    @Override
    public final void addPropertyChangeListener(PropertyChangeListener pcl) {
        pca.addPropertyChangeListener(pcl);
    }

    @Override
    public final void removePropertyChangeListener(PropertyChangeListener pcl) {
        pca.removePropertyChangeListener(pcl);
    }

    @Override
    public final void firePropertyChange(String propertyName) {
        pca.firePropertyChange(propertyName);
    }

    @Override
    public final void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        ResizeListener resizeListener = new ResizeListener();
        getWindowStateListeners().add(resizeListener);
        addPropertyChangeListener(resizeListener);
        initializeWindow(namespace, location, resources);
    }

    public void initializeWindow(Map<String, Object> namespace, URL location, Resources resources) {
    }

    private class ResizeListener extends WindowStateListener.Adapter
            implements ComponentListener, PropertyChangeListener {

        private void enableResizing(Window window) {
            java.awt.Window hostWindow = window.getDisplay().getHostWindow();

            if (hostWindow instanceof java.awt.Frame) {
                java.awt.Frame hostFrame = (java.awt.Frame) hostWindow;
                hostFrame.setResizable(true);
            }
        }

        private void disableResizing(Window window) {
            java.awt.Window hostWindow = window.getDisplay().getHostWindow();

            if (hostWindow instanceof java.awt.Frame) {
                java.awt.Frame hostFrame = (java.awt.Frame) hostWindow;
                hostFrame.setResizable(false);
            }
        }

        @Override
        public Vote previewWindowOpen(Window window) {
            getHostWindow().addComponentListener(ResizeListener.this);
            return super.previewWindowOpen(window);
        }

        @Override
        public void windowOpened(Window window) {
            DesktopApplicationContext.sizeHostToFit(window);

            if (!isResizable()) {
                disableResizing(window);
            } else {
                enableResizing(window);
            }
        }

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (propertyName.equals(RESIZABLE_PROPERTY_NAME)) {
                if (!isResizable()) {
                    disableResizing(BindableWindow.this);
                } else {
                    enableResizing(BindableWindow.this);
                }
            }
        }

        @Override
        public void componentResized(ComponentEvent e) {
            java.awt.Component cmp = e.getComponent();

            if (cmp instanceof java.awt.Window) {
                java.awt.Window hostWindow = (java.awt.Window) cmp;
                java.awt.Insets insets = hostWindow.getInsets();

                synchronized (hostWindow.getTreeLock()) {
                    setPreferredSize(
                            cmp.getWidth() - insets.left - insets.right,
                            cmp.getHeight() - insets.top - insets.bottom);
                }
            }
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }

    }
}
