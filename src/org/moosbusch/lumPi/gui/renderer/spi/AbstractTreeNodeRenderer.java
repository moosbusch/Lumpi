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

import java.net.URL;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence.Tree.Path;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.TreeView.NodeCheckState;
import org.apache.pivot.wtk.content.TreeViewNodeRenderer;
import org.apache.pivot.wtk.media.Image;
import org.moosbusch.lumPi.gui.renderer.LabelableComponentRenderer;
import org.moosbusch.lumPi.util.RendererUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractTreeNodeRenderer
        extends TreeViewNodeRenderer implements LabelableComponentRenderer {

    private final Map<URL, Image> icons;
    private boolean showText = true;
    private int iconSize = 16;

    public AbstractTreeNodeRenderer() {
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
    public void render(Object node, Path path, int rowIndex, TreeView treeView,
            boolean expanded, boolean selected, NodeCheckState checkState,
            boolean highlighted, boolean disabled) {
        if (node != null) {
            RendererUtil.renderData(this, node);
        } else {
            super.render(node, path, rowIndex, treeView, expanded, selected,
                    checkState, highlighted, disabled);
        }
    }
}
