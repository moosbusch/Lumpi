/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moosbusch.lumPi.beans.spring.impl;

import org.apache.pivot.wtk.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PivotCoreComponentsBean {

    @Bean
    public TextInput createTextInput() {
        return new TextInput();
    }

    @Bean
    public TextArea createTextArea() {
        return new TextArea();
    }

    @Bean
    public TextPane createTextPane() {
        return new TextPane();
    }

    @Bean
    public RadioButtonGroup createRadioButtonGroup() {
        return new RadioButtonGroup();
    }

    @Bean
    public ButtonGroup createButtonGroup() {
        return new ButtonGroup();
    }

    @Bean
    public PushButton createPushButton() {
        return new PushButton();
    }

    @Bean
    public LinkButton createLinkButton() {
        return new LinkButton();
    }

    @Bean
    public ListButton createListButton() {
        return new ListButton();
    }

    @Bean
    public Rollup createRollup() {
        return new Rollup();
    }

    @Bean
    public RadioButton createRadioButton() {
        return new RadioButton();
    }

    @Bean
    public ListView createListView() {
        return new ListView();
    }

    @Bean
    public TableView createTableView() {
        return new TableView();
    }

    @Bean
    public TreeView createTreeView() {
        return new TreeView();
    }

    @Bean
    public MenuButton createMenuButton() {
        return new MenuButton();
    }

    @Bean
    public Menu createMenu() {
        return new Menu();
    }

    @Bean
    public Menu.Item createMenuItem() {
        return new Menu.Item();
    }

    @Bean
    public Menu.Section createMenuSection() {
        return new Menu.Section();
    }

    @Bean
    public MenuBar createMenuBar() {
        return new MenuBar();
    }

    @Bean
    public MenuBar.Item createMenuBarItem() {
        return new MenuBar.Item();
    }

    @Bean
    public MenuPopup createMenuPopup() {
        return new MenuPopup();
    }

    @Bean
    public SuggestionPopup createSuggestionPopup() {
        return new SuggestionPopup();
    }

    @Bean
    public Meter createMeter() {
        return new Meter();
    }

    @Bean
    public CalendarButton createCalendarButton() {
        return new CalendarButton();
    }

    @Bean
    public Calendar createCalendar() {
        return new Calendar();
    }

    @Bean
    public ColorChooserButton createColorChooserButton() {
        return new ColorChooserButton();
    }

    @Bean
    public ColorChooser createColorChooser() {
        return new ColorChooser();
    }

    @Bean
    public Checkbox createCheckbox() {
        return new Checkbox();
    }

    @Bean
    public Label createLabel() {
        return new Label();
    }

    @Bean
    public CardPane createCardPane() {
        return new CardPane();
    }

    @Bean
    public GridPane createGridPane() {
        return new GridPane();
    }

    @Bean
    public GridPane.Row createGridPaneRow() {
        return new GridPane.Row();
    }

    @Bean
    public TablePane createTablePane() {
        return new TablePane();
    }

    @Bean
    public TablePane.Column createTablePaneColumn() {
        return new TablePane.Column();
    }

    @Bean
    public TablePane.Row createTablePaneRow() {
        return new TablePane.Row();
    }

    @Bean
    public TabPane createTabPane() {
        return new TabPane();
    }

    @Bean
    public BoxPane createBoxPane() {
        return new BoxPane();
    }

    @Bean
    public SplitPane createSplitPane() {
        return new SplitPane();
    }

    @Bean
    public StackPane createStackPane() {
        return new StackPane();
    }

    @Bean
    public FillPane createFillPane() {
        return new FillPane();
    }

    @Bean
    public Border createBorder() {
        return new Border();
    }

    @Bean
    public Panel createPanel() {
        return new Panel();
    }

    @Bean
    public Palette createPalette() {
        return new Palette();
    }

    @Bean
    public Accordion createAccordion() {
        return new Accordion();
    }

    @Bean
    public ActivityIndicator createActivityIndicator() {
        return new ActivityIndicator();
    }

    @Bean
    public Alert createAlert() {
        return new Alert(new String());
    }

    @Bean
    public Dialog createDialog() {
        return new Dialog();
    }

    @Bean
    public Form createForm() {
        return new Form();
    }

    @Bean
    public Form.Section createFormSection() {
        return new Form.Section();
    }

    @Bean
    public Frame createFrame() {
        return new Frame();
    }

    @Bean
    public ImageView createImageView() {
        return new ImageView();
    }

    @Bean
    public Expander createExpander() {
        return new Expander();
    }

    @Bean
    public FileBrowser createFileBrowser() {
        return new FileBrowser();
    }

    @Bean
    public FileBrowserSheet createFileBrowserSheet() {
        return new FileBrowserSheet();
    }

    @Bean
    public ScrollBar createScrollBar() {
        return new ScrollBar();
    }

    @Bean
    public ScrollPane createScrollPane() {
        return new ScrollPane();
    }

    @Bean
    public Separator createSeparator() {
        return new Separator();
    }

    @Bean
    public Slider createSlider() {
        return new Slider();
    }

    @Bean
    public Spinner createSpinner() {
        return new Spinner();
    }

    @Bean
    public Tooltip createTooltip() {
        return new Tooltip();
    }

    @Bean
    public Window createWindow() {
        return new Window();
    }

    @Bean
    public Sheet createSheet() {
        return new Sheet();
    }

    @Bean
    public Prompt createPrompt() {
        return new Prompt();
    }

    @Bean
    public Panorama createPanorama() {
        return new Panorama();
    }
}
