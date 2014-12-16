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

package io.github.moosbusch.lumpi.gui.impl;

import java.net.URL;
import java.util.Objects;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.media.Image;
import io.github.moosbusch.lumpi.beans.PropertyChangeAware;
import io.github.moosbusch.lumpi.beans.impl.PropertyChangeAwareAdapter;
import io.github.moosbusch.lumpi.gui.Labelable;

/**
 *
 * @author Gunnar Kappei
 */
public class LabelableAdapter implements Labelable {
    private ButtonData buttonData;
    private final PropertyChangeAware pca;

    public LabelableAdapter(ButtonData buttonData) {
        this.buttonData = buttonData;
        this.pca = new PropertyChangeAwareAdapter(this);
    }

    public LabelableAdapter() {
        this(new ButtonData());
    }

    public LabelableAdapter(Image icon) {
        this(new ButtonData(icon));
    }

    public LabelableAdapter(String text) {
        this(new ButtonData(text));
    }

    public LabelableAdapter(Image icon, String text) {
        this(new ButtonData(icon, text));
    }

    @Override
    public final String getDescription() {
        return getText();
    }

    @Override
    public ButtonData getButtonData() {
        return buttonData;
    }

    @Override
    public void setButtonData(ButtonData buttonData) {
        this.buttonData = Objects.requireNonNull(buttonData);
        firePropertyChange(BUTTON_DATA_PROPERTY_NAME);
    }

    @Override
    public final Object getUserData() {
        return getButtonData().getUserData();
    }

    @Override
    public final void setUserData(Object userData) {
        getButtonData().setUserData(userData);
        firePropertyChange(USER_DATA_PROPERTY_NAME);
    }

    @Override
    public final String getText() {
        return getButtonData().getText();
    }

    @Override
    public final void setText(String text) {
        getButtonData().setText(text);
        firePropertyChange(TEXT_PROPERTY_NAME);
    }

    @Override
    public final Image getIcon() {
        return getButtonData().getIcon();
    }

    @Override
    public final void setIcon(String iconName) {
        getButtonData().setIcon(iconName);
        firePropertyChange(ICON_PROPERTY_NAME);
    }

    @Override
    public final void setIcon(URL iconURL) {
        getButtonData().setIcon(iconURL);
        firePropertyChange(ICON_PROPERTY_NAME);
    }

    @Override
    public final void setIcon(Image icon) {
        getButtonData().setIcon(icon);
        firePropertyChange(ICON_PROPERTY_NAME);
    }

    @Override
    public BeanMonitor getMonitor() {
        return pca.getMonitor();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pca.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pca.removePropertyChangeListener(pcl);
    }

    @Override
    public void firePropertyChange(String propertyName) {
        if (pca != null) {
            pca.firePropertyChange(propertyName);
        }
    }

}
