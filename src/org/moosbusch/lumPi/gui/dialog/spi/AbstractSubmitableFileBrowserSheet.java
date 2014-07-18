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

import java.io.File;
import java.util.Objects;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.collections.immutable.ImmutableList;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Filter;
import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.FileBrowser;
import org.apache.pivot.wtk.FileBrowserListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TablePane.Column;
import org.apache.pivot.wtk.TablePane.Row;
import org.moosbusch.lumPi.gui.component.Submitable;
import org.moosbusch.lumPi.util.LumPiUtil;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSubmitableFileBrowserSheet
        extends AbstractSubmitableSheet<Sequence<File>>
        implements Submitable<Sequence<File>> {

    private final TablePane tablePane;
    private FileBrowser fileBrowser;
    private final BoxPane buttonBoxPane;
    private final Column column;
    private final Row buttonRow;
    private final Row fileBrowserRow;
    private final PushButton okButton;
    private final PushButton cancelButton;
    private FileBrowserSheet.Mode mode = FileBrowserSheet.Mode.OPEN;

    public AbstractSubmitableFileBrowserSheet() {
        this.fileBrowser = new FileBrowser();
        this.tablePane = new TablePane();
        this.buttonBoxPane = new BoxPane();
        this.column = new Column();
        this.buttonRow = new Row();
        this.fileBrowserRow = new Row();
        this.okButton = new PushButton("Ok");
        this.cancelButton = new PushButton("Cancel");
        init();
    }

    private void init() {
        okButton.getButtonPressListeners().add(new OkButtonListener());
        cancelButton.getButtonPressListeners().add(new CancelButtonListener());

        buttonBoxPane.setOrientation(Orientation.HORIZONTAL);
        buttonBoxPane.add(okButton);
        buttonBoxPane.add(cancelButton);
        LumPiUtil.setComponentStyle(buttonBoxPane, "horizontalAlignment", "right");
        LumPiUtil.setComponentStyle(buttonBoxPane, "verticalAlignment", "bottom");

        fileBrowser.setPreferredHeight(300);
        column.setWidth("1*");

        fileBrowserRow.setHeight(300);
        fileBrowserRow.add(fileBrowser);

        buttonRow.setHeight(50);
        buttonRow.add(buttonBoxPane);

        tablePane.getRows().add(fileBrowserRow);
        tablePane.getRows().add(buttonRow);
        tablePane.getColumns().add(column);
        setContent(tablePane);

        addPropertyChangeListener(new PropertyCangeListenerImpl());
    }

    @Override
    public Sequence<File> modifyValueBeforeSubmit(Sequence<File> value) {
        return value;
    }

    public FileBrowser getFileBrowser() {
        return fileBrowser;
    }

    public void setFileBrowser(FileBrowser fileBrowser) {
        this.fileBrowser = fileBrowser;
    }

    public final File getRootDirectory() {
        return getFileBrowser().getRootDirectory();
    }

    public final void setRootDirectory(File rootDirectory) {
        getFileBrowser().setRootDirectory(rootDirectory);
    }

    public final boolean addSelectedFile(File file) {
        return getFileBrowser().addSelectedFile(file);
    }

    public final boolean removeSelectedFile(File file) {
        return getFileBrowser().removeSelectedFile(file);
    }

    public final ImmutableList<File> getSelectedFiles() {
        return getFileBrowser().getSelectedFiles();
    }

    public final Sequence<File> setSelectedFiles(Sequence<File> selectedFiles) {
        return getFileBrowser().setSelectedFiles(selectedFiles);
    }

    public final void clearSelection() {
        getFileBrowser().clearSelection();
    }

    public final boolean isFileSelected(File file) {
        return getFileBrowser().isFileSelected(file);
    }

    public final boolean isMultiSelect() {
        return getFileBrowser().isMultiSelect();
    }

    public final void setMultiSelect(boolean multiSelect) {
        getFileBrowser().setMultiSelect(multiSelect);
    }

    public final ListenerList<FileBrowserListener> getFileBrowserListeners() {
        return getFileBrowser().getFileBrowserListeners();
    }

    public final File getSelectedFile() {
        return getFileBrowser().getSelectedFile();
    }

    public final void setSelectedFile(File f) {
        getFileBrowser().setSelectedFile(f);
    }

    public final Filter<File> getDisabledFileFilter() {
        return getFileBrowser().getDisabledFileFilter();
    }

    public final void setDisabledFileFilter(Filter<File> f) {
        getFileBrowser().setDisabledFileFilter(f);
    }

    public final StyleDictionary getFileBrowserStyles() {
        return getFileBrowser().getStyles();
    }

    public final void setFileBrowserStyles(String styles) throws SerializationException {
        getFileBrowser().setStyles(styles);
    }

    public FileBrowserSheet.Mode getMode() {
        return mode;
    }

    public void setMode(FileBrowserSheet.Mode mode) {
        this.mode = Objects.requireNonNull(mode);
    }

    private class PropertyCangeListenerImpl implements PropertyChangeListener {

        @Override
        public void propertyChanged(Object bean, String propertyName) {
            if (propertyName.equalsIgnoreCase(VALUE_PROPERTY)) {
                setSelectedFiles(getValue());
            }
        }

    }

    private class OkButtonListener implements ButtonPressListener {

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
}
