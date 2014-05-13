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

import org.moosbusch.lumPi.action.impl.DefaultDialogAction;
import org.moosbusch.lumPi.action.impl.DefaultQuitAction;
import org.moosbusch.lumPi.gui.component.impl.FillScrollPane;
import org.moosbusch.lumPi.gui.dialog.impl.DefaultSubmitableFileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.impl.DirectoryFileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableDialog;
import org.moosbusch.lumPi.gui.filtered.FilteredListButton;
import org.moosbusch.lumPi.gui.filtered.FilteredListView;
import org.moosbusch.lumPi.gui.filtered.FilteredTableView;
import org.moosbusch.lumPi.gui.filtered.FilteredTreeView;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultByteFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultCharacterFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultCollectionFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultDoubleFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultFloatFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultIntegerFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultShortFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.DefaultStringFormEditor;
import org.moosbusch.lumPi.gui.form.editor.impl.RadioButtonBooleanFormEditor;
import org.moosbusch.lumPi.gui.form.impl.DefaultDynamicForm;
import org.moosbusch.lumPi.gui.menu.ConfigurableMenuHandler;
import org.moosbusch.lumPi.gui.renderer.impl.DefaultButtonDataRenderer;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class LumPiMiscBean  {

    @Bean
    public DefaultQuitAction createDefaultQuitAction() {
        return new DefaultQuitAction();
    }

    @Bean
    public DefaultDialogAction<? extends BindableWindow, ? extends AbstractSubmitableDialog<?>>
         createDefaultDialogAction() {
        return new DefaultDialogAction<>();
    }

    @Bean
    public FillScrollPane createFillScrollPane() {
        return new FillScrollPane();
    }

    @Bean
    public DefaultSubmitableFileBrowserSheet createDefaultSubmitableFileBrowserSheet() {
        return new DefaultSubmitableFileBrowserSheet();
    }

    @Bean
    public DirectoryFileBrowserSheet createDirectoryFileBrowserSheet() {
        return new DirectoryFileBrowserSheet();
    }

    @Bean
    public DefaultButtonDataRenderer createDefaultButtonDataRenderer() {
        return new DefaultButtonDataRenderer();
    }

    @Bean
    public ConfigurableMenuHandler createConfigurableMenuHandler() {
        return new ConfigurableMenuHandler();
    }

    @Bean
    public DefaultDynamicForm createDefaultDynamicForm() {
        return new DefaultDynamicForm();
    }

    @Bean
    public RadioButtonBooleanFormEditor createRadioButtonBooleanFormEditor() {
        return new RadioButtonBooleanFormEditor();
    }

    @Bean
    public DefaultCharacterFormEditor createDefaultCharacterFormEditor() {
        return new DefaultCharacterFormEditor();
    }

    @Bean
    public DefaultStringFormEditor createDefaultStringFormEditor() {
        return new DefaultStringFormEditor();
    }

    @Bean
    public DefaultByteFormEditor createDefaultByteFormEditor() {
        return new DefaultByteFormEditor();
    }

    @Bean
    public DefaultShortFormEditor createDefaultShortFormEditor() {
        return new DefaultShortFormEditor();
    }

    @Bean
    public DefaultFloatFormEditor createDefaultFloatFormEditor() {
        return new DefaultFloatFormEditor();
    }

    @Bean
    public DefaultDoubleFormEditor createDefaultDoubleFormEditor() {
        return new DefaultDoubleFormEditor();
    }

    @Bean
    public DefaultIntegerFormEditor createDefaultIntegerFormEditor() {
        return new DefaultIntegerFormEditor();
    }

    @Bean
    public DefaultCollectionFormEditor createDefaultCollectionFormEditor() {
        return new DefaultCollectionFormEditor();
    }

    @Bean
    public FilteredTreeView createFilteredTreeView() {
        return new FilteredTreeView();
    }

    @Bean
    public FilteredListView createFilteredListView() {
        return new FilteredListView();
    }

    @Bean
    public FilteredTableView createFilteredTableView() {
        return new FilteredTableView();
    }

    @Bean
    public FilteredListButton createFilteredListButton() {
        return new FilteredListButton();
    }

}
