/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.renderer.spi;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.moosbusch.lumPi.gui.renderer.LabelableComponentRenderer;
import org.moosbusch.lumPi.util.RendererUtil;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.content.ButtonDataRenderer;
import org.apache.pivot.wtk.media.Drawing;
import org.apache.pivot.wtk.media.Image;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractButtonDataRenderer extends ButtonDataRenderer
        implements LabelableComponentRenderer {

    private Image icon = null;
    private boolean showText = true;
    private int iconSize = 16;

    public AbstractButtonDataRenderer() {
        init();
    }

    private void init() {
        RendererUtil.initRenderer(this);
    }

    @Override
    public int getIconSize() {
        return iconSize;
    }

    @Override
    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
    }

    @Override
    public boolean getShowText() {
        return showText;
    }

    @Override
    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    @Override
    public void render(Object data, Button button, boolean highlighted) {
        if (data != null) {
            RendererUtil.renderData(this, data);
        } else {
            super.render(data, button, highlighted);
        }
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public Label getLabel() {
        return label;
    }

    @Override
    public Image getIcon(Object item) {
        if (icon == null) {
            if (item instanceof ButtonData) {
                Image img = ((ButtonData) item).getIcon();

                if (img instanceof Drawing) {
                    ((Drawing) img).setSize(getIconSize(), getIconSize());
                }

                return img;
            }

            URL iconUrl = getIconUrl(item);

            if (iconUrl != null) {
                try {
                    icon = Image.load(iconUrl);
                } catch (TaskExecutionException ex) {
                    Logger.getLogger(AbstractButtonDataRenderer.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
        }

        return icon;
    }

    @Override
    public String getText(Object item) {
        if (item != null) {
            if (item instanceof ButtonData) {
                return ((ButtonData) item).getText();
            } else {
                return item.toString();
            }
        }

        return null;
    }
}
