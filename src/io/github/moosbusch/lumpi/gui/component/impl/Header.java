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
package io.github.moosbusch.lumpi.gui.component.impl;

import java.awt.Font;
import java.net.URL;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.Theme;
import org.apache.pivot.wtk.media.Image;
import io.github.moosbusch.lumpi.util.LumpiUtil;

/**
 *
 * @author moosbusch
 */
public class Header extends TablePane {

    private final Column imageColumn;
    private final Column textColumn;
    private final Row headingRow;
    private final Row messageRow;
    private final Label headingLabel;
    private final Label messageLabel;
    private final ImageView imageView;

    public Header() {
        this.imageColumn = new Column();
        this.textColumn = new Column();
        this.headingRow = new Row();
        this.messageRow = new Row();
        this.headingLabel = new Label();
        this.messageLabel = new Label();
        this.imageView = new ImageView();
        init();
    }

    private void init() {
        Font labelFont = Theme.getTheme().getFont();
        LumpiUtil.setComponentStyle(headingLabel,
                "font", labelFont.deriveFont(Font.BOLD));
        imageView.setPreferredSize(48, 48);

        imageColumn.setWidth(48);
        textColumn.setWidth("1*");

        headingRow.setHeight(24);
        messageRow.setHeight(48);

        headingRow.add(new TablePane.Filler());
        headingRow.add(headingLabel);

        messageRow.add(imageView);
        messageRow.add(messageLabel);

        getColumns().add(imageColumn);
        getColumns().add(textColumn);

        getRows().add(headingRow);
        getRows().add(messageRow);
    }

    protected final Label getHeadingLabel() {
        return headingLabel;
    }

    protected final Label getMessageLabel() {
        return messageLabel;
    }

    protected final ImageView getImageView() {
        return imageView;
    }

    public final String getHeading() {
        return getHeadingLabel().getText();
    }

    public final void setHeading(String text) {
        getHeadingLabel().setText(text);
    }

    public final String getText() {
        return getMessageLabel().getText();
    }

    public final void setText(String text) {
        getMessageLabel().setText(text);
    }

    public final Image getImage() {
        return getImageView().getImage();
    }

    public final void setImage(Image image) {
        getImageView().setImage(image);
    }

    public final void setImage(URL imageURL) {
        getImageView().setImage(imageURL);
    }

    public final void setImage(String imageName) {
        getImageView().setImage(imageName);
    }

}
