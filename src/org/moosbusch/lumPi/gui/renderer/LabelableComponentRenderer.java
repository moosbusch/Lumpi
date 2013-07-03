/*

 *
 */
package org.moosbusch.lumPi.gui.renderer;

import java.net.URL;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.media.Image;

/**
 *
 * @author moosbusch
 */
public interface LabelableComponentRenderer extends LabelableRenderer {

    public ImageView getImageView();

    public Label getLabel();

    public boolean getShowText();

    public void setShowText(boolean showText);

    public void setSize(int width, int height);

    public int getIconWidth();

    public void setIconWidth(int iconWidth);

    public int getIconHeight();

    public void setIconHeight(int iconHeight);

    public boolean getShowIcon();

    public void setShowIcon(boolean showIcon);

    public boolean getFillIcon();

    public void setFillIcon(boolean fillIcon);

    public Map<URL, Image> getIcons();


    }
