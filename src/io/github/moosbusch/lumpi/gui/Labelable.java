/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.moosbusch.lumpi.gui;

import java.net.URL;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.media.Image;
import io.github.moosbusch.lumpi.beans.PropertyChangeAware;

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

}
