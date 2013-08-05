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
package org.moosbusch.lumPi.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.wtk.media.Image;
import org.moosbusch.lumPi.gui.renderer.LabelableComponentRenderer;

/**
 *
 * @author moosbusch
 */
public class RendererUtil {

    private RendererUtil() {
    }

    public static void initRenderer(LabelableComponentRenderer renderer) {
        if (renderer != null) {
            renderer.setIconHeight(renderer.getIconSize());
            renderer.setIconWidth(renderer.getIconSize());
        }
    }

    public static void renderData(LabelableComponentRenderer renderer, Object data) {
        if (renderer != null && data != null) {
            Image img = renderer.getIcon(data);
            String txt = renderer.getText(data);

            renderer.setShowIcon(img != null);

            renderer.getLabel().setVisible(renderer.getShowText());
            renderer.getImageView().setVisible(renderer.getShowIcon());

            if (img != null) {
                renderer.getImageView().setImage(img);
            } else {
                renderer.getImageView().clear();
            }

            if (StringUtils.isNotBlank(txt)) {
                renderer.getLabel().setText(txt);
            } else {
                renderer.getLabel().setText("");
            }
        }

    }
}
