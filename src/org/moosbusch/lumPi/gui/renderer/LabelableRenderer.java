/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.renderer;

import java.net.URL;
import org.apache.pivot.wtk.media.Image;

/**
 *
 * @author moosbusch
 */
public interface LabelableRenderer {

    public URL getIconUrl(Object item);

    public int getIconSize();

    public void setIconSize(int iconSize);

    public Image getIcon(Object item);

    public String getText(Object item);

}
