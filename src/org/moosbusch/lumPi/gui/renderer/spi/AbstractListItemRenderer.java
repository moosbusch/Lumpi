/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.renderer.spi;

import java.net.URL;
import org.moosbusch.lumPi.gui.renderer.LabelableComponentRenderer;
import org.moosbusch.lumPi.util.RendererUtil;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.content.ListViewItemRenderer;
import org.apache.pivot.wtk.media.Image;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractListItemRenderer
        extends ListViewItemRenderer implements LabelableComponentRenderer {

    private Image icon = null;
    private boolean showText = true;
    private int iconSize = 16;

    public AbstractListItemRenderer() {
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
            URL iconUrl = getIconUrl(item);

            if (iconUrl != null) {
                getImageView().setImage(iconUrl);
                icon = getImageView().getImage();
            }
        }

        return icon;
    }

    @Override
    public String getText(Object item) {
        return item.toString();
    }

    @Override
    public void render(Object item, int index, ListView listView, boolean selected,
            boolean checked, boolean highlighted, boolean disabled) {
        renderStyles(listView, selected, highlighted, disabled);

        if (item != null) {
            RendererUtil.renderData(this, item);
        } else {
            super.render(item, index, listView, selected, checked, highlighted, disabled);
        }
    }
}
