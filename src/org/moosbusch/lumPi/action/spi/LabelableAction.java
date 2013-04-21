/*

 *
 */
package org.moosbusch.lumPi.action.spi;

import java.net.URL;
import java.util.Objects;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.media.Image;

/**
 *
 * @author moosbusch
 */
public abstract class LabelableAction extends Action implements Bindable {

    private ButtonData buttonData;

    public LabelableAction(boolean enabled) {
        super(enabled);
        this.buttonData = createDefaultButtonData();
    }

    public LabelableAction() {
        this.buttonData = createDefaultButtonData();
    }

    public LabelableAction(ButtonData buttonData, boolean enabled) {
        super(enabled);
        this.buttonData = Objects.requireNonNull(buttonData);
    }

    public LabelableAction(ButtonData buttonData) {
        this.buttonData = Objects.requireNonNull(buttonData);
    }

    private ButtonData createDefaultButtonData() {
        ButtonData result = new ButtonData();
        result.setText(super.getDescription());
        return result;
    }

    public ButtonData getButtonData() {
        return buttonData;
    }

    public void setButtonData(ButtonData buttonData) {
        this.buttonData = Objects.requireNonNull(buttonData);
    }

    @Override
    public final String getDescription() {
        return getText();
    }

    public final void setUserData(Object userData) {
        getButtonData().setUserData(userData);
    }

    public final void setText(String text) {
        getButtonData().setText(text);
    }

    public final void setIcon(String iconName) {
        getButtonData().setIcon(iconName);
    }

    public final void setIcon(URL iconURL) {
        getButtonData().setIcon(iconURL);
    }

    public final void setIcon(Image icon) {
        getButtonData().setIcon(icon);
    }

    public final Object getUserData() {
        return getButtonData().getUserData();
    }

    public final String getText() {
        return getButtonData().getText();
    }

    public final Image getIcon() {
        return getButtonData().getIcon();
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    }

}
