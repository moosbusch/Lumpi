/*

 *
 */
package org.moosbusch.lumPi.gui.component.impl;

import java.net.URL;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.media.Image;

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
        imageColumn.setWidth(100);
        textColumn.setWidth("1*");

        headingRow.setHeight(35);
        messageRow.setHeight("1*");

        imageView.setPreferredSize(100, 100);

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
