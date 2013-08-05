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
package org.moosbusch.lumPi.gui.dialog.spi;

import java.net.URL;
import org.apache.pivot.collections.Collection;
import org.apache.pivot.collections.LinkedQueue;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Queue;
import org.apache.pivot.collections.QueueListener;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.CardPane;
import org.apache.pivot.wtk.CardPaneListener;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TablePane.Column;
import org.apache.pivot.wtk.TablePane.Row;
import org.apache.pivot.wtk.skin.CardPaneSkin;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitableWizard<T extends Object>
        extends AbstractSubmitableSheet<T> {

    private final PushButton submitButton;
    private final PushButton cancelButton;
    private final PushButton previousButton;
    private final PushButton nextButton;
    private final BoxPane buttonBoxPane;
    private final TablePane tablePane;
    private final CardPane cardPane;
//    private final Header header;
    private final LinkedQueue<Form> formsQueue;
    private final Column cardPaneColumn;
//    private final Row headerRow;
    private final Row cardPaneRow;
    private final Row buttonBoxPaneRow;
    private Collection<?> forms;

    public AbstractSubmitableWizard() {
        this.formsQueue = new LinkedQueue<>();
        this.buttonBoxPane = new BoxPane();
        this.submitButton = new PushButton();
        this.cancelButton = new PushButton();
        this.previousButton = new PushButton();
        this.nextButton = new PushButton();
        this.cardPane = new CardPane();
//        this.header = new Header();
//        this.headerRow = new Row();
        this.cardPaneColumn = new Column();
        this.cardPaneRow = new Row();
        this.buttonBoxPaneRow = new Row();
        this.tablePane = new TablePane();

        formsQueue.getQueueListeners().add(new FormsListener());
        cardPane.getCardPaneListeners().add(new CardPaneListenerImpl());
    }

    private void initWizard() {
        previousButton.getButtonPressListeners().add(new PreviousButtonListener());
        previousButton.setEnabled(false);
        previousButton.setButtonData("<<");

        nextButton.getButtonPressListeners().add(new NextButtonListener());
        nextButton.setEnabled(canProceed(getValue()));
        nextButton.setButtonData(">>");

        submitButton.getButtonPressListeners().add(new SubmitButtonListener());
        submitButton.setEnabled(canSubmit(getValue()));
        submitButton.setButtonData("Ok");

        cancelButton.getButtonPressListeners().add(new CancelButtonListener());
        cancelButton.setButtonData("Cancel");

        cardPaneColumn.setWidth("1*");

        cardPaneRow.setHeight("1*");
//        headerRow.setHeight(100);
        buttonBoxPaneRow.setHeight(50, false);

//        headerRow.add(header);

        cardPaneRow.add(cardPane);
        buttonBoxPaneRow.add(buttonBoxPane);

        tablePane.getColumns().add(cardPaneColumn);

//        tablePane.getRows().add(headerRow);
        tablePane.getRows().add(cardPaneRow);
        tablePane.getRows().add(buttonBoxPaneRow);

        buttonBoxPane.add(previousButton);
        buttonBoxPane.add(nextButton);
        buttonBoxPane.add(submitButton);
        buttonBoxPane.add(cancelButton);

        PivotUtil.setComponentStyle(cardPane, "selectionChangeEffect",
                CardPaneSkin.SelectionChangeEffect.CROSSFADE);

        PivotUtil.setComponentStyle(buttonBoxPane, "horizontalAlignment", "right");
        PivotUtil.setComponentStyle(buttonBoxPane, "verticalAlignment", "bottom");

        setContent(tablePane);
    }

    private void insertForms(Collection<?> newForms) {
        formsQueue.clear();

        if (newForms != null) {
            for (Object obj : newForms) {
                if (obj != null) {
                    if (obj instanceof Form) {
                        insertForm((Form) obj);
                    }
                }
            }
        }
    }

    private void insertForm(Form newForm) {
        formsQueue.enqueue(newForm);
    }

    protected final void previous() {
        int maxIndex = cardPane.getLength() - 1;
        int previousSelectedIndex = cardPane.getSelectedIndex();
        int nextSelectedIndex = previousSelectedIndex - 1;

        if ((nextSelectedIndex >= 0) && (nextSelectedIndex <= maxIndex)) {
            cardPane.setSelectedIndex(nextSelectedIndex);
        }
    }

    protected final void next() {
        int maxIndex = cardPane.getLength() - 1;
        int previousSelectedIndex = cardPane.getSelectedIndex();
        int nextSelectedIndex = previousSelectedIndex + 1;

        if ((nextSelectedIndex >= 0) && (nextSelectedIndex <= maxIndex)) {
            cardPane.setSelectedIndex(nextSelectedIndex);
        }
    }

    public boolean canProceed(T value) {
        return true;
    }

    public void initializeExt(Map<String, Object> namespace,
            URL location, Resources resources) {
    }

    @Override
    public final void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        initializeExt(namespace, location, resources);
        initWizard();
    }

    public final Collection<?> getForms() {
        return forms;
    }

    public final void setForms(Collection<?> newForms) {
        this.forms = newForms;
        insertForms(forms);
    }

//    public final String getHeading() {
//        return header.getHeading();
//    }
//
//    public final void setHeading(String text) {
//        header.setHeading(text);
//    }
//
//    public final String getHeaderText() {
//        return header.getText();
//    }
//
//    public final void setHeaderText(String text) {
//        header.setText(text);
//    }
//
//    public final Image getHeaderImage() {
//        return header.getImage();
//    }
//
//    public final void setHeaderImage(Image image) {
//        header.setImage(image);
//    }
//
//    public final void setHeaderImage(URL imageURL) {
//        header.setImage(imageURL);
//    }
//
//    public final void setHeaderImage(String imageName) {
//        header.setImage(imageName);
//    }

    private class PreviousButtonListener implements ButtonPressListener {

        @Override
        public void buttonPressed(Button button) {
            previous();
        }
    }

    private class NextButtonListener implements ButtonPressListener {

        @Override
        public void buttonPressed(Button button) {
            next();
        }
    }

    private class SubmitButtonListener implements ButtonPressListener {

        @Override
        public void buttonPressed(Button button) {
            submit();
        }
    }

    private class CancelButtonListener implements ButtonPressListener {

        @Override
        public void buttonPressed(Button button) {
            cancel();
        }
    }

    private class CardPaneListenerImpl extends CardPaneListener.Adapter {

        @Override
        public Vote previewSelectedIndexChange(CardPane cardPane, int selectedIndexArgument) {
            if ((selectedIndexArgument != -1)
                    && (selectedIndexArgument < cardPane.getLength())) {
                int previousSelectedIndex = cardPane.getSelectedIndex();

                if (selectedIndexArgument > previousSelectedIndex) {
                    if (canProceed(getValue())) {
                        return Vote.APPROVE;
                    }
                }
            }

            return Vote.DENY;
        }

        @Override
        public void selectedIndexChanged(CardPane cardPane, int previousSelection) {
            updateButtons(cardPane);
        }

        private void updateButtons(CardPane cardPane) {
            int selIndex = cardPane.getSelectedIndex();
            previousButton.setEnabled(selIndex > 0);
            nextButton.setEnabled((selIndex < cardPane.getLength() - 1)
                    && (canProceed(getValue())));
        }
    }

    private class FormsListener extends QueueListener.Adapter<Form> {

        @Override
        public void itemEnqueued(Queue<Form> queue, Form item) {
            FillPane fp = new FillPane();
            fp.add(item);
            cardPane.add(fp);
        }

        @Override
        public void itemDequeued(Queue<Form> queue, Form item) {
            cardPane.remove(item);
        }

        @Override
        public void queueCleared(Queue<Form> queue) {
            cardPane.removeAll();
        }
    }
}
