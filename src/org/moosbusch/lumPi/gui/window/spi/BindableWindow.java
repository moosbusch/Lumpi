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
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Window;
import org.moosbusch.lumPi.beans.PropertyChangeAware;
import org.moosbusch.lumPi.beans.impl.PropertyChangeAwareAdapter;

/**
 *
 * @author moosbusch
 */
public abstract class BindableWindow extends Window implements Bindable, PropertyChangeAware {

    public static final String RESIZABLE_PROPERTY_NAME = "resizable";
    public static final String ICON_PROPERTY_NAME = "icon";
    public static final String TITLE_PROPERTY_NAME = "title";
    public static final String SIZE_PROPERTY_NAME = "size";
    public static final String PREFERRED_SIZE_PROPERTY_NAME = "preferredSize";
    private final PropertyChangeAware pca;
    private boolean resizable = true;

    public BindableWindow() {
        this(null);
    }

    public BindableWindow(Component content) {
        super(content);
        this.pca = new PropertyChangeAwareAdapter(this);
        init();
    }

    private void init() {
        setDoubleBuffered(true);
    }

    protected void setIconImpl(URL iconURL) {
        super.setIcon(iconURL);
    }

    protected void setTitleImpl(String title) {
        super.setTitle(title);
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        firePropertyChange(RESIZABLE_PROPERTY_NAME);
    }

    @Override
    public final void setIcon(String iconName) {
        super.setIcon(iconName);
    }

    @Override
    public final void setIcon(URL iconURL) {
        setIconImpl(iconURL);
        firePropertyChange(ICON_PROPERTY_NAME);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        firePropertyChange(TITLE_PROPERTY_NAME);
    }

    @Override
    public final void setPreferredSize(int preferredWidth, int preferredHeight) {
        super.setPreferredSize(preferredWidth, preferredHeight);
        firePropertyChange(PREFERRED_SIZE_PROPERTY_NAME);
    }

    @Override
    public final void setSize(int width, int height) {
        super.setSize(width, height);
        firePropertyChange(SIZE_PROPERTY_NAME);
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

}
