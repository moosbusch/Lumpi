/*

 *
 */
package org.moosbusch.lumPi.gui.renderer.spi;

import java.net.URL;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.moosbusch.lumPi.gui.renderer.LabelableComponentRenderer;
import org.moosbusch.lumPi.util.RendererUtil;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.content.ListButtonDataRenderer;
import org.apache.pivot.wtk.media.Image;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractListButtonDataRenderer extends ListButtonDataRenderer
        implements LabelableComponentRenderer {

    private final Map<URL, Image> icons;
    private boolean showText = true;
    private int iconSize = 16;

    public AbstractListButtonDataRenderer() {
        this.icons = new HashMap<>();
        init();
    }

    private void init() {
        RendererUtil.initRenderer(this);
    }

    @Override
    public Map<URL, Image> getIcons() {
        return icons;
    }

    @Override
    public final int getIconSize() {
        return iconSize;
    }

    @Override
    public final void setIconSize(int iconSize) {
        this.iconSize = iconSize;
        setIconWidth(iconSize);
        setIconHeight(iconSize);
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
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public Label getLabel() {
        return label;
    }

    @Override
    public Image getIcon(Object item) {
        URL iconUrl = getIconUrl(item);

        if (iconUrl != null) {
            Image result = getIcons().get(iconUrl);

            if (result != null) {
                getImageView().setImage(result);
                return result;
            } else {
                getImageView().setImage(iconUrl);
                return getImageView().getImage();
            }
        }

        return null;
    }

    @Override
    public String getText(Object item) {
        return item.toString();
    }

    @Override
    public void render(Object data, Button button, boolean highlight) {
        if (data != null) {
            RendererUtil.renderData(this, data);
        } else {
            super.render(data, button, highlight);
        }
    }
}
