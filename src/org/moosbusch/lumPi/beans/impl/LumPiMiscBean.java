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
package org.moosbusch.lumPi.beans.impl;

import org.moosbusch.lumPi.action.impl.DefaultDialogAction;
import org.moosbusch.lumPi.action.impl.DefaultPromptAction;
import org.moosbusch.lumPi.action.impl.DefaultQuitAction;
import org.moosbusch.lumPi.action.impl.DefaultSheetAction;
import org.moosbusch.lumPi.gui.component.impl.FillScrollPane;
import org.moosbusch.lumPi.gui.dialog.impl.DefaultSubmitableFileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.impl.DirectoryFileBrowserSheet;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableDialog;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitablePrompt;
import org.moosbusch.lumPi.gui.dialog.spi.AbstractSubmitableSheet;
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
import org.moosbusch.lumPi.gui.menu.impl.ConfigurableMenuHandler;
import org.moosbusch.lumPi.gui.renderer.impl.DefaultButtonDataRenderer;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class LumPiMiscBean {

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultQuitAction createDefaultQuitAction() {
        return new DefaultQuitAction();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultDialogAction<? extends BindableWindow, ? extends AbstractSubmitableDialog<?>>
            createDefaultDialogAction() {
        return new DefaultDialogAction<>();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultSheetAction<? extends BindableWindow, ? extends AbstractSubmitableSheet<?>>
            createDefaultSheetAction() {
        return new DefaultSheetAction<>();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultPromptAction<? extends BindableWindow, ? extends AbstractSubmitablePrompt<?>>
            createDefaultPromptAction() {
        return new DefaultPromptAction<>();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FillScrollPane createFillScrollPane() {
        return new FillScrollPane();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultSubmitableFileBrowserSheet createDefaultSubmitableFileBrowserSheet() {
        return new DefaultSubmitableFileBrowserSheet();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DirectoryFileBrowserSheet createDirectoryFileBrowserSheet() {
        return new DirectoryFileBrowserSheet();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultButtonDataRenderer createDefaultButtonDataRenderer() {
        return new DefaultButtonDataRenderer();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ConfigurableMenuHandler createConfigurableMenuHandler() {
        return new ConfigurableMenuHandler();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultDynamicForm createDefaultDynamicForm() {
        return new DefaultDynamicForm();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public RadioButtonBooleanFormEditor createRadioButtonBooleanFormEditor() {
        return new RadioButtonBooleanFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultCharacterFormEditor createDefaultCharacterFormEditor() {
        return new DefaultCharacterFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultStringFormEditor createDefaultStringFormEditor() {
        return new DefaultStringFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultByteFormEditor createDefaultByteFormEditor() {
        return new DefaultByteFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultShortFormEditor createDefaultShortFormEditor() {
        return new DefaultShortFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultFloatFormEditor createDefaultFloatFormEditor() {
        return new DefaultFloatFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultDoubleFormEditor createDefaultDoubleFormEditor() {
        return new DefaultDoubleFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultIntegerFormEditor createDefaultIntegerFormEditor() {
        return new DefaultIntegerFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultCollectionFormEditor createDefaultCollectionFormEditor() {
        return new DefaultCollectionFormEditor();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FilteredTreeView createFilteredTreeView() {
        return new FilteredTreeView();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FilteredListView createFilteredListView() {
        return new FilteredListView();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FilteredTableView createFilteredTableView() {
        return new FilteredTableView();
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FilteredListButton createFilteredListButton() {
        return new FilteredListButton();
    }

}
