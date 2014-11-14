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
package org.moosbusch.lumPi.gui.renderer.spi;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.content.ListButtonDataRenderer;
import org.apache.pivot.wtk.media.Image;
import org.moosbusch.lumPi.gui.renderer.LabelableComponentRenderer;
import org.moosbusch.lumPi.util.RendererUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractListButtonDataRenderer extends ListButtonDataRenderer
        implements LabelableComponentRenderer {

    private final Map<URI, Image> icons;
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
    public Map<URI, Image> getIcons() {
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
        URI iconUri = getIconUri(item);

        if (iconUri != null) {
            Image result = getIcons().get(iconUri);

            if (result != null) {
                getImageView().setImage(result);
                return result;
            } else {
                try {
                    getImageView().setImage(iconUri.toURL());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AbstractListButtonDataRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
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
            super.render(new ButtonData(), button, highlight);
        }
    }
}
