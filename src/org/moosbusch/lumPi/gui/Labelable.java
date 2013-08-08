/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui;

import java.net.URL;
import java.util.Objects;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.media.Image;
import org.moosbusch.lumPi.beans.PropertyChangeAware;

/**
 *
 * @author moosbusch
 */
public interface Labelable extends PropertyChangeAware {

    public static final String ICON_PROPERTY_NAME = "icon";
    public static final String TEXT_PROPERTY_NAME = "text";
    public static final String BUTTON_DATA_PROPERTY_NAME = "buttonData";
    public static final String USER_DATA_PROPERTY_NAME = "userData";

    public ButtonData getButtonData();

    public void setButtonData(ButtonData buttonData);

    public String getDescription();

    public void setUserData(Object userData);

    public void setText(String text);

    public void setIcon(String iconName);

    public void setIcon(URL iconURL);

    public void setIcon(Image icon);

    public Object getUserData();

    public String getText();

    public Image getIcon();

    public static abstract class Adapter implements Labelable {

        private ButtonData buttonData;
        private final PropertyChangeAware pca;

        public Adapter(ButtonData buttonData) {
            this.buttonData = buttonData;
            this.pca = new PropertyChangeAware.Adapter(this);
        }

        public Adapter() {
            this(new ButtonData());
        }

        public Adapter(Image icon) {
            this(new ButtonData(icon));
        }

        public Adapter(String text) {
            this(new ButtonData(text));
        }

        public Adapter(Image icon, String text) {
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
}
