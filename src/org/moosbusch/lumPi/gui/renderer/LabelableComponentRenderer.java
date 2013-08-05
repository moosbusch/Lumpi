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
