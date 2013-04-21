/*

 *
 */
package org.moosbusch.lumPi.gui.renderer.spi;

import java.net.URL;
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

    private Image icon = null;
    private boolean showText = true;
    private int iconSize = 16;

    public AbstractTreeNodeRenderer() {
        init();
    }

    private void init() {
        RendererUtil.initRenderer(this);
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
