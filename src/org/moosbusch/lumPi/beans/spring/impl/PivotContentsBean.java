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

import org.apache.pivot.wtk.content.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PivotContentsBean {

    @Bean
    public AccordionHeaderDataRenderer createAccordionHeaderDataRenderer() {
        return new AccordionHeaderDataRenderer();
    }

    @Bean
    public ButtonData createButtonData() {
        return new ButtonData();
    }

    @Bean
    public ButtonDataRenderer createButtonDataRenderer() {
        return new ButtonDataRenderer();
    }

    @Bean
    public CalendarButtonDataRenderer createCalendarButtonDataRenderer() {
        return new CalendarButtonDataRenderer();
    }

    @Bean
    public CalendarDateSpinnerData createCalendarDateSpinnerData() {
        return new CalendarDateSpinnerData();
    }

    @Bean
    public ColorItem createColorItem() {
        return new ColorItem();
    }

    @Bean
    public LinkButtonDataRenderer createLinkButtonDataRenderer() {
        return new LinkButtonDataRenderer();
    }

    @Bean
    public ListButtonColorItemRenderer createListButtonColorItemRenderer() {
        return new ListButtonColorItemRenderer();
    }

    @Bean
    public ListButtonDataRenderer createListButtonDataRenderer() {
        return new ListButtonDataRenderer();
    }

    @Bean
    public ListItem createListItem() {
        return new ListItem();
    }

    @Bean
    public ListViewColorItemRenderer createListViewColorItemRenderer() {
        return new ListViewColorItemRenderer();
    }

    @Bean
    public ListViewColorItemRenderer.ColorBadge createColorBadge() {
        return new ListViewColorItemRenderer.ColorBadge();
    }

    @Bean
    public ListViewItemEditor createListViewItemEditor() {
        return new ListViewItemEditor();
    }

    @Bean
    public ListViewItemRenderer createListViewItemRenderer() {
        return new ListViewItemRenderer();
    }

    @Bean
    public MenuBarItemDataRenderer createMenuBarItemDataRenderer() {
        return new MenuBarItemDataRenderer();
    }

    @Bean
    public MenuButtonDataRenderer createMenuButtonDataRenderer() {
        return new MenuButtonDataRenderer();
    }

    @Bean
    public MenuItemData createMenuItemData() {
        return new MenuItemData();
    }

    @Bean
    public MenuItemDataRenderer createMenuItemDataRenderer() {
        return new MenuItemDataRenderer();
    }

    @Bean
    public NumericSpinnerData createNumericSpinnerData() {
        return new NumericSpinnerData();
    }

    @Bean
    public SpinnerItemRenderer createSpinnerItemRenderer() {
        return new SpinnerItemRenderer();
    }

    @Bean
    public TableViewBooleanCellRenderer createTableViewBooleanCellRenderer() {
        return new TableViewBooleanCellRenderer();
    }

    @Bean
    public TableViewCellRenderer createTableViewCellRenderer() {
        return new TableViewCellRenderer();
    }

    @Bean
    public TableViewCheckboxCellRenderer createTableViewCheckboxCellRenderer() {
        return new TableViewCheckboxCellRenderer();
    }

    @Bean
    public TableViewDateCellRenderer createTableViewDateCellRenderer() {
        return new TableViewDateCellRenderer();
    }

    @Bean
    public TableViewFileSizeCellRenderer createTableViewFileSizeCellRenderer() {
        return new TableViewFileSizeCellRenderer();
    }

    @Bean
    public TableViewHeaderData createTableViewHeaderData() {
        return new TableViewHeaderData();
    }

    @Bean
    public TableViewHeaderDataRenderer createTableViewHeaderDataRenderer() {
        return new TableViewHeaderDataRenderer();
    }

    @Bean
    public TableViewImageCellRenderer createTableViewImageCellRenderer() {
        return new TableViewImageCellRenderer();
    }

    @Bean
    public TableViewMultiCellRenderer createTableViewMultiCellRenderer() {
        return new TableViewMultiCellRenderer();
    }

    @Bean
    public TableViewMultiCellRenderer.RendererMapping createRendererMapping() {
        return new TableViewMultiCellRenderer.RendererMapping();
    }

    @Bean
    public TableViewNumberCellRenderer createTableViewNumberCellRenderer() {
        return new TableViewNumberCellRenderer();
    }

    @Bean
    public TableViewRowEditor createTableViewRowEditor() {
        return new TableViewRowEditor();
    }

    @Bean
    public TableViewTextAreaCellRenderer createTableViewTextAreaCellRenderer() {
        return new TableViewTextAreaCellRenderer();
    }

    @Bean
    public TableViewTriStateCellRenderer createTableViewTriStateCellRenderer() {
        return new TableViewTriStateCellRenderer();
    }

    @Bean
    public TreeBranch createTreeBranch() {
        return new TreeBranch();
    }

    @Bean
    public TreeNode createTreeNode() {
        return new TreeNode();
    }

    @Bean
    public TreeViewNodeEditor createTreeViewNodeEditor() {
        return new TreeViewNodeEditor();
    }

    @Bean
    public TreeViewNodeRenderer createTreeViewNodeRenderer() {
        return new TreeViewNodeRenderer();
    }
}
