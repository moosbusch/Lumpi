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
package io.github.moosbusch.lumpi.gui.window.swing.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.ApplicationContext.DisplayHost;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.WindowStateListener;
import org.apache.pivot.wtk.media.Image;
import io.github.moosbusch.lumpi.application.LumpiApplication;
import io.github.moosbusch.lumpi.application.LumpiApplicationContext;
import io.github.moosbusch.lumpi.beans.PropertyChangeAware;
import io.github.moosbusch.lumpi.beans.impl.PropertyChangeAwareAdapter;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;
import io.github.moosbusch.lumpi.util.LumpiUtil;

/**
 *
 * @author Gunnar Kappei
 */
public class HostFrame extends JFrame implements PropertyChangeAware {

    private static final long serialVersionUID = 3785740183919711739L;
    public static final String WINDOW_PROPERTY_NAME = "window";
    private final DisplayHost displayHost;
    private transient final PropertyChangeAware pca;
    private transient final Reference<LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow>> appRef;
    private transient final WindowListener windowListener;
    private final JLayer<DisplayHost> layer;
    private transient BindableWindow window;

    public HostFrame(LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow> application) {
        this.appRef = new WeakReference<>(application);
        this.pca = new PropertyChangeAwareAdapter(this);
        this.windowListener = new WindowListener();
        this.displayHost = initDisplayHost();
        this.layer = new JLayer<>();
        init();
    }

    private void init() {
        addPropertyChangeListener(new PropertyChangeListenerImpl());
        addComponentListener(windowListener);
        initWindow();
        initSplashScreen();
    }

    private void initSplashScreen() {
        final SplashScreen splash = SplashScreen.getSplashScreen();

        if (splash != null) {
            Graphics2D g = splash.createGraphics();

            if (g != null) {
                return;
            }

            splash.close();
        }
    }

    private void initWindow() {
        getLayer().setDoubleBuffered(true);
        getLayer().setView(getDisplayHost());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(getLayer(), BorderLayout.CENTER);
    }

    private DisplayHost initDisplayHost() {
        return createDisplayHost();
    }

    protected DisplayHost createDisplayHost() {
        return new DisplayHostImpl();
    }

    protected final LumpiApplication<? extends LumpiApplicationContext, ? extends BindableWindow> getApplication() {
        return appRef.get();
    }

    @Override
    protected final void processWindowStateEvent(WindowEvent event) {
        super.processWindowStateEvent(event);

        switch (event.getID()) {
            case WindowEvent.WINDOW_ICONIFIED: {
                try {
                    getApplication().suspend();
                } catch (Exception ex) {
                    Logger.getLogger(HostFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
            case WindowEvent.WINDOW_DEICONIFIED: {
                try {
                    getApplication().resume();
                } catch (Exception ex) {
                    Logger.getLogger(HostFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            default: {
            }
        }

        processWindowStateEventExt(event);
    }

    protected void processWindowStateEventExt(WindowEvent event) {
    }

    @Override
    public final void processWindowEvent(WindowEvent event) {
        super.processWindowEvent(event);

        switch (event.getID()) {
            case WindowEvent.WINDOW_OPENED: {
                ApplicationContext.getDisplays().add(getDisplay());
                break;
            }
            case WindowEvent.WINDOW_CLOSING: {
                try {
                    getApplication().shutdown(true);
                } catch (Exception ex) {
                    Logger.getLogger(HostFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case WindowEvent.WINDOW_CLOSED: {
                ApplicationContext.getDisplays().remove(getDisplay());
                System.exit(0);
                break;
            }
        }

        processWindowEventExt(event);
    }

    public final JLayer<DisplayHost> getLayer() {
        return layer;
    }

    public final DisplayHost getDisplayHost() {
        return displayHost;
    }

    public final Display getDisplay() {
        return getDisplayHost().getDisplay();
    }

    public final DisplayHost getView() {
        return getLayer().getView();
    }

    public final void setUI(LayerUI<? super DisplayHost> ui) {
        getLayer().setUI(ui);
    }

    public final LayerUI<? super DisplayHost> getUI() {
        return getLayer().getUI();
    }

    public final BindableWindow getWindow() {
        return window;
    }

    public final void setWindow(BindableWindow window) {
        if (this.window == null) {
            this.window = window;
            firePropertyChange(WINDOW_PROPERTY_NAME);
        }
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

    public void processWindowEventExt(WindowEvent event) {
    }

    private class PropertyChangeListenerImpl implements PropertyChangeListener {

        private void initWindow(BindableWindow bindableWindow) {
            bindableWindow.getWindowStateListeners().add(windowListener);
            bindableWindow.addPropertyChangeListener(windowListener);
        }

        private void initFromBindableWindow(BindableWindow bindableWindow) {
            Dimension prefSize = new Dimension(bindableWindow.getPreferredWidth(),
                    bindableWindow.getPreferredHeight());
            int iconCount = window.getIcons().getLength();

            setPreferredSize(prefSize);
            getDisplayHost().setPreferredSize(prefSize);
            setTitle(bindableWindow.getTitle());
            setResizable(bindableWindow.isResizable());

            if (iconCount > 0) {
                List<java.awt.Image> iconImages = new ArrayList<>();

                for (Image icon : window.getIcons()) {
                    iconImages.add(LumpiUtil.toBufferedImage(icon));
                }

                setIconImages(iconImages);
            }

            pack();
            setLocationRelativeTo(null);
        }

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (propertyName.equals(WINDOW_PROPERTY_NAME)) {
                final BindableWindow bindableWindow = getWindow();
                initWindow(bindableWindow);
                initFromBindableWindow(bindableWindow);
                bindableWindow.open(getDisplay());

                setVisible(true);
            }
        }

    }

    private class WindowListener extends WindowStateListener.Adapter
            implements ComponentListener, PropertyChangeListener {

        private void enableResizing() {
            setResizable(true);
        }

        private void disableResizing() {
            setResizable(false);
        }

        @Override
        public void windowOpened(Window window) {
            if (!isResizable()) {
                disableResizing();
            } else {
                enableResizing();
            }
        }

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            switch (propertyName) {
                case BindableWindow.RESIZABLE_PROPERTY_NAME: {
                    if (!isResizable()) {
                        disableResizing();
                    } else {
                        enableResizing();
                    }

                    break;
                }
                case BindableWindow.ICON_PROPERTY_NAME: {
                    if (bean instanceof BindableWindow) {
                        BindableWindow window = (BindableWindow) bean;
                        int iconCount = window.getIcons().getLength();

                        if (iconCount > 0) {
                            List<java.awt.Image> iconImages = new ArrayList<>();

                            for (Image icon : window.getIcons()) {
                                iconImages.add(LumpiUtil.toBufferedImage(icon));
                            }

                            setIconImages(iconImages);
                        }
                    }

                    break;
                }
                case BindableWindow.TITLE_PROPERTY_NAME: {
                    if (bean instanceof BindableWindow) {
                        BindableWindow bindableWindow = (BindableWindow) bean;
                        setTitle(bindableWindow.getTitle());
                    }

                    break;
                }
                case BindableWindow.SIZE_PROPERTY_NAME: {
                    if (bean instanceof BindableWindow) {
                        BindableWindow bindableWindow = (BindableWindow) bean;
                        Dimension prefSize = new Dimension(bindableWindow.getPreferredWidth(),
                                bindableWindow.getPreferredHeight());

                        setPreferredSize(prefSize);
                    }

                    break;
                }
                case BindableWindow.PREFERRED_SIZE_PROPERTY_NAME: {
                    if (bean instanceof BindableWindow) {
                        BindableWindow bindableWindow = (BindableWindow) bean;
                        Dimension prefSize = new Dimension(bindableWindow.getPreferredWidth(),
                                bindableWindow.getPreferredHeight());

                        setPreferredSize(prefSize);
                    }

                    break;
                }
                default: {
                }
            }
        }

        @Override
        public void componentResized(ComponentEvent e) {
            java.awt.Component cmp = e.getComponent();

            if (cmp == HostFrame.this) {
                java.awt.Insets insets = getInsets();

                synchronized (getTreeLock()) {
                    int width = cmp.getWidth() - insets.left - insets.right;
                    int height = cmp.getHeight() - insets.top - insets.bottom;
                    getWindow().setPreferredSize(width, height);
//                    getDisplayHost().setPreferredSize(new Dimension(
//                            cmp.getWidth() - insets.left - insets.right,
//                            cmp.getHeight() - insets.top - insets.bottom));
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

    private class DisplayHostImpl extends DisplayHost {

        private static final long serialVersionUID = -3402423450515561011L;

        public DisplayHostImpl() {
            initDisplayHost();
        }

        private void initDisplayHost() {
            setBufferedImagePaintEnabled(true);
            setVolatileImagePaintEnabled(false);
        }

        @Override
        public final void setVolatileImagePaintEnabled(boolean enabled) {
            super.setVolatileImagePaintEnabled(false);
        }

        @Override
        public void setBufferedImagePaintEnabled(boolean enabled) {
            super.setBufferedImagePaintEnabled(true);
        }

    }

}
