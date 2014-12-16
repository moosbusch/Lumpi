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
package io.github.moosbusch.lumpi.beans.impl;

import io.github.moosbusch.lumpi.action.impl.DefaultDialogAction;
import io.github.moosbusch.lumpi.action.impl.DefaultPromptAction;
import io.github.moosbusch.lumpi.action.impl.DefaultQuitAction;
import io.github.moosbusch.lumpi.action.impl.DefaultSheetAction;
import io.github.moosbusch.lumpi.gui.component.impl.FillScrollPane;
import io.github.moosbusch.lumpi.gui.dialog.impl.DefaultSubmitableFileBrowserSheet;
import io.github.moosbusch.lumpi.gui.dialog.impl.DirectoryFileBrowserSheet;
import io.github.moosbusch.lumpi.gui.dialog.spi.AbstractSubmitableDialog;
import io.github.moosbusch.lumpi.gui.dialog.spi.AbstractSubmitablePrompt;
import io.github.moosbusch.lumpi.gui.dialog.spi.AbstractSubmitableSheet;
import io.github.moosbusch.lumpi.gui.filtered.FilteredListButton;
import io.github.moosbusch.lumpi.gui.filtered.FilteredListView;
import io.github.moosbusch.lumpi.gui.filtered.FilteredTableView;
import io.github.moosbusch.lumpi.gui.filtered.FilteredTreeView;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultByteFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultCharacterFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultCollectionFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultDoubleFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultFloatFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultIntegerFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultShortFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.DefaultStringFormEditor;
import io.github.moosbusch.lumpi.gui.form.editor.impl.RadioButtonBooleanFormEditor;
import io.github.moosbusch.lumpi.gui.form.impl.DefaultDynamicForm;
import io.github.moosbusch.lumpi.gui.menu.impl.ConfigurableMenuHandler;
import io.github.moosbusch.lumpi.gui.renderer.impl.DefaultButtonDataRenderer;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;
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
