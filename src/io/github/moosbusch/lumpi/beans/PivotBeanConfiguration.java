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
package io.github.moosbusch.lumpi.beans;

import io.github.moosbusch.lumpi.application.SpringBXMLSerializer;
import io.github.moosbusch.lumpi.gui.window.spi.BindableWindow;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.ArrayQueue;
import org.apache.pivot.collections.ArrayStack;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.HashSet;
import org.apache.pivot.collections.LinkedList;
import org.apache.pivot.collections.LinkedQueue;
import org.apache.pivot.collections.LinkedStack;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.io.FileList;
import org.apache.pivot.io.FileSerializer;
import org.apache.pivot.json.JSONSerializer;
import org.apache.pivot.serialization.BinarySerializer;
import org.apache.pivot.serialization.ByteArraySerializer;
import org.apache.pivot.serialization.CSVSerializer;
import org.apache.pivot.serialization.PropertiesSerializer;
import org.apache.pivot.serialization.StringSerializer;
import org.apache.pivot.util.CalendarDate;
import org.apache.pivot.util.Time;
import org.apache.pivot.util.concurrent.TaskGroup;
import org.apache.pivot.util.concurrent.TaskSequence;
import org.apache.pivot.wtk.Accordion;
import org.apache.pivot.wtk.ActivityIndicator;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.ButtonGroup;
import org.apache.pivot.wtk.Calendar;
import org.apache.pivot.wtk.CalendarButton;
import org.apache.pivot.wtk.CardPane;
import org.apache.pivot.wtk.Checkbox;
import org.apache.pivot.wtk.ColorChooser;
import org.apache.pivot.wtk.ColorChooserButton;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.Expander;
import org.apache.pivot.wtk.FileBrowser;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.FlowPane;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Frame;
import org.apache.pivot.wtk.GridPane;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.LinkButton;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuBar;
import org.apache.pivot.wtk.MenuButton;
import org.apache.pivot.wtk.MenuPopup;
import org.apache.pivot.wtk.Meter;
import org.apache.pivot.wtk.Palette;
import org.apache.pivot.wtk.Panel;
import org.apache.pivot.wtk.Panorama;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.RadioButton;
import org.apache.pivot.wtk.RadioButtonGroup;
import org.apache.pivot.wtk.Rollup;
import org.apache.pivot.wtk.ScrollBar;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.Separator;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.Slider;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.SplitPane;
import org.apache.pivot.wtk.StackPane;
import org.apache.pivot.wtk.SuggestionPopup;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewHeader;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.TextPane;
import org.apache.pivot.wtk.Tooltip;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.content.AccordionHeaderDataRenderer;
import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.content.ButtonDataRenderer;
import org.apache.pivot.wtk.content.CalendarButtonDataRenderer;
import org.apache.pivot.wtk.content.CalendarDateSpinnerData;
import org.apache.pivot.wtk.content.ColorItem;
import org.apache.pivot.wtk.content.LinkButtonDataRenderer;
import org.apache.pivot.wtk.content.ListButtonColorItemRenderer;
import org.apache.pivot.wtk.content.ListButtonDataRenderer;
import org.apache.pivot.wtk.content.ListItem;
import org.apache.pivot.wtk.content.ListViewColorItemRenderer;
import org.apache.pivot.wtk.content.ListViewItemEditor;
import org.apache.pivot.wtk.content.ListViewItemRenderer;
import org.apache.pivot.wtk.content.MenuBarItemDataRenderer;
import org.apache.pivot.wtk.content.MenuButtonDataRenderer;
import org.apache.pivot.wtk.content.MenuItemData;
import org.apache.pivot.wtk.content.MenuItemDataRenderer;
import org.apache.pivot.wtk.content.NumericSpinnerData;
import org.apache.pivot.wtk.content.SpinnerItemRenderer;
import org.apache.pivot.wtk.content.TableViewBooleanCellRenderer;
import org.apache.pivot.wtk.content.TableViewCellRenderer;
import org.apache.pivot.wtk.content.TableViewCheckboxCellRenderer;
import org.apache.pivot.wtk.content.TableViewDateCellRenderer;
import org.apache.pivot.wtk.content.TableViewFileSizeCellRenderer;
import org.apache.pivot.wtk.content.TableViewHeaderData;
import org.apache.pivot.wtk.content.TableViewHeaderDataRenderer;
import org.apache.pivot.wtk.content.TableViewImageCellRenderer;
import org.apache.pivot.wtk.content.TableViewMultiCellRenderer;
import org.apache.pivot.wtk.content.TableViewNumberCellRenderer;
import org.apache.pivot.wtk.content.TableViewRowEditor;
import org.apache.pivot.wtk.content.TableViewTextAreaCellRenderer;
import org.apache.pivot.wtk.content.TableViewTriStateCellRenderer;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.apache.pivot.wtk.content.TreeViewNodeEditor;
import org.apache.pivot.wtk.content.TreeViewNodeRenderer;
import org.apache.pivot.wtk.effects.BaselineDecorator;
import org.apache.pivot.wtk.effects.BlurDecorator;
import org.apache.pivot.wtk.effects.ClipDecorator;
import org.apache.pivot.wtk.effects.DropShadowDecorator;
import org.apache.pivot.wtk.effects.FadeDecorator;
import org.apache.pivot.wtk.effects.GrayscaleDecorator;
import org.apache.pivot.wtk.effects.OverlayDecorator;
import org.apache.pivot.wtk.effects.ReflectionDecorator;
import org.apache.pivot.wtk.effects.RotationDecorator;
import org.apache.pivot.wtk.effects.SaturationDecorator;
import org.apache.pivot.wtk.effects.ScaleDecorator;
import org.apache.pivot.wtk.effects.ShadeDecorator;
import org.apache.pivot.wtk.effects.TagDecorator;
import org.apache.pivot.wtk.effects.TranslationDecorator;
import org.apache.pivot.wtk.effects.WatermarkDecorator;
import org.apache.pivot.wtk.effects.easing.Circular;
import org.apache.pivot.wtk.effects.easing.Cubic;
import org.apache.pivot.wtk.effects.easing.Exponential;
import org.apache.pivot.wtk.effects.easing.Linear;
import org.apache.pivot.wtk.effects.easing.Quadratic;
import org.apache.pivot.wtk.effects.easing.Quartic;
import org.apache.pivot.wtk.effects.easing.Quintic;
import org.apache.pivot.wtk.effects.easing.Sine;
import org.apache.pivot.wtk.media.BufferedImageSerializer;
import org.apache.pivot.wtk.media.SVGDiagramSerializer;
import org.apache.pivot.wtk.text.BulletedList;
import org.apache.pivot.wtk.text.ComponentNode;
import org.apache.pivot.wtk.text.Document;
import org.apache.pivot.wtk.text.ImageNode;
import org.apache.pivot.wtk.text.List;
import org.apache.pivot.wtk.text.NumberedList;
import org.apache.pivot.wtk.text.Paragraph;
import org.apache.pivot.wtk.text.PlainTextSerializer;
import org.apache.pivot.wtk.text.Span;
//import org.apache.pivot.wtk.text.Span;
import org.apache.pivot.wtk.text.TextNode;
import org.apache.pivot.wtk.text.TextSpan;
import org.apache.pivot.wtk.validation.BigDecimalValidator;
import org.apache.pivot.wtk.validation.BigIntegerValidator;
import org.apache.pivot.wtk.validation.ComparableRangeValidator;
import org.apache.pivot.wtk.validation.ComparableValidator;
import org.apache.pivot.wtk.validation.DecimalValidator;
import org.apache.pivot.wtk.validation.DoubleRangeValidator;
import org.apache.pivot.wtk.validation.DoubleValidator;
import org.apache.pivot.wtk.validation.EmptyTextValidator;
import org.apache.pivot.wtk.validation.FloatRangeValidator;
import org.apache.pivot.wtk.validation.FloatValidator;
import org.apache.pivot.wtk.validation.IntRangeValidator;
import org.apache.pivot.wtk.validation.IntValidator;
import org.apache.pivot.wtk.validation.NotEmptyTextValidator;
import org.apache.pivot.wtk.validation.RegexTextValidator;
import org.apache.pivot.xml.XMLSerializer;

/**
 *
 * @author Gunnar Kappei
 */
public interface PivotBeanConfiguration
    <T extends BindableWindow, V extends SpringBXMLSerializer> {

    public T createApplicationWindow();

    public V createSerializer();

    public BulletedList createBulletedList();

    public ComponentNode createComponentNode();

    public Document createDocument();

    public ImageNode createImageNode();

    public List.Item createTextListItem();

    public NumberedList createNumberedList();

    public Paragraph createParagraph();

    public Span createSpan();

    public TextNode createTextNode();

    public TextSpan createTextSpan();

    public BaselineDecorator createBaselineDecorator();

    public BlurDecorator createBlurDecorator();

    public ClipDecorator createClipDecorator();

    public DropShadowDecorator createDropShadowDecorator();

    public FadeDecorator createFadeDecorator();

    public GrayscaleDecorator createGrayscaleDecorator();

    public OverlayDecorator createOverlayDecorator();

    public ReflectionDecorator createReflectionDecorator();

    public RotationDecorator createRotationDecorator();

    public SaturationDecorator createSaturationDecorator();

    public ScaleDecorator createScaleDecorator();

    public ShadeDecorator createShadeDecorator();

    public TagDecorator createTagDecorator();

    public TranslationDecorator createTranslationDecorator();

    public WatermarkDecorator createWatermarkDecorator();

    public Circular createCircular();

    public Cubic createCubic();

    public Exponential createExponential();

    public Linear createLinear();

    public Quadratic createQuadratic();

    public Quartic createQuartic();

    public Quintic createQuintic();

    public Sine createSine();

    public FileSerializer createFileSerializer();

    public AccordionHeaderDataRenderer createAccordionHeaderDataRenderer();

    public ButtonData createButtonData();

    public ButtonDataRenderer createButtonDataRenderer();

    public CalendarButtonDataRenderer createCalendarButtonDataRenderer();

    public CalendarDateSpinnerData createCalendarDateSpinnerData();

    public ColorItem createColorItem();

    public LinkButtonDataRenderer createLinkButtonDataRenderer();

    public ListButtonColorItemRenderer createListButtonColorItemRenderer();

    public ListButtonDataRenderer createListButtonDataRenderer();

    public ListItem createListItem();

    public ListViewColorItemRenderer createListViewColorItemRenderer();

    public ListViewColorItemRenderer.ColorBadge createColorBadge();

    public ListViewItemEditor createListViewItemEditor();

    public ListViewItemRenderer createListViewItemRenderer();

    public MenuBarItemDataRenderer createMenuBarItemDataRenderer();

    public MenuButtonDataRenderer createMenuButtonDataRenderer();

    public MenuItemData createMenuItemData();

    public MenuItemDataRenderer createMenuItemDataRenderer();

    public NumericSpinnerData createNumericSpinnerData();

    public SpinnerItemRenderer createSpinnerItemRenderer();

    public TableViewBooleanCellRenderer createTableViewBooleanCellRenderer();

    public TableViewCellRenderer createTableViewCellRenderer();

    public TableViewCheckboxCellRenderer createTableViewCheckboxCellRenderer();

    public TableViewDateCellRenderer createTableViewDateCellRenderer();

    public TableViewFileSizeCellRenderer createTableViewFileSizeCellRenderer();
    
    public TableViewHeader createTableViewHeader();
    
    public TableView.Column createTableViewColumn();

    public TableViewHeaderData createTableViewHeaderData();

    public TableViewHeaderDataRenderer createTableViewHeaderDataRenderer();

    public TableViewImageCellRenderer createTableViewImageCellRenderer();

    public TableViewMultiCellRenderer createTableViewMultiCellRenderer();

    public TableViewMultiCellRenderer.RendererMapping createRendererMapping();

    public TableViewNumberCellRenderer createTableViewNumberCellRenderer();

    public TableViewRowEditor createTableViewRowEditor();

    public TableViewTextAreaCellRenderer createTableViewTextAreaCellRenderer();

    public TableViewTriStateCellRenderer createTableViewTriStateCellRenderer();

    public TreeBranch createTreeBranch();

    public TreeNode createTreeNode();

    public TreeViewNodeEditor createTreeViewNodeEditor();

    public TreeViewNodeRenderer createTreeViewNodeRenderer();

    public BinarySerializer createBinarySerializer();

    public BufferedImageSerializer createBufferedImageSerializer();

    public BXMLSerializer createBXMLSerializer();

    public ByteArraySerializer createByteArraySerializer();

    public CSVSerializer createCSVSerializer();

    public JSONSerializer createJSONSerializer();

    public PlainTextSerializer createPlainTextSerializer();

    public PropertiesSerializer createPropertiesSerializer();

    public StringSerializer createStringSerializer();

    public SVGDiagramSerializer createSVGDiagramSerializer();

    public XMLSerializer createXMLSerializer();

    public Sequence.Tree.ImmutablePath createImmutablePath();

    public Sequence.Tree<? extends Object> createTree();

    public ArrayList<? extends Object> createArrayList();

    public ArrayQueue<? extends Object> createArrayQueue();

    public ArrayStack<? extends Object> createArrayStack();

    public HashSet<? extends Object> createHashSet();

    public HashMap<? extends Object, ? extends Object> createHashMap();

    public LinkedList<? extends Object> createLinkedList();

    public LinkedQueue<? extends Object> createLinkedQueue();

    public LinkedStack<? extends Object> createLinkedStack();

    public FileList createFileList();

    public TextInput createTextInput();

    public TextArea createTextArea();

    public TextPane createTextPane();

    public RadioButtonGroup createRadioButtonGroup();

    public ButtonGroup createButtonGroup();

    public PushButton createPushButton();

    public LinkButton createLinkButton();

    public ListButton createListButton();

    public Rollup createRollup();

    public RadioButton createRadioButton();

    public ListView createListView();

    public TableView createTableView();

    public TreeView createTreeView();

    public MenuButton createMenuButton();

    public Menu createMenu();

    public Menu.Item createMenuItem();

    public Menu.Section createMenuSection();

    public MenuBar createMenuBar();

    public MenuBar.Item createMenuBarItem();

    public MenuPopup createMenuPopup();

    public SuggestionPopup createSuggestionPopup();

    public Meter createMeter();

    public CalendarButton createCalendarButton();

    public Calendar createCalendar();

    public ColorChooserButton createColorChooserButton();

    public ColorChooser createColorChooser();

    public Checkbox createCheckbox();

    public Label createLabel();

    public CardPane createCardPane();

    public GridPane createGridPane();

    public GridPane.Row createGridPaneRow();

    public TablePane createTablePane();

    public FlowPane createFlowPane();

    public TablePane.Column createTablePaneColumn();

    public TablePane.Row createTablePaneRow();

    public TablePane.Filler createTablePaneFiller();

    public TabPane createTabPane();

    public BoxPane createBoxPane();

    public SplitPane createSplitPane();

    public StackPane createStackPane();

    public FillPane createFillPane();

    public Border createBorder();

    public Panel createPanel();

    public Palette createPalette();

    public Accordion createAccordion();

    public ActivityIndicator createActivityIndicator();

    public Alert createAlert();

    public Dialog createDialog();

    public Form createForm();

    public Form.Section createFormSection();

    public Frame createFrame();

    public ImageView createImageView();

    public Expander createExpander();

    public FileBrowser createFileBrowser();

    public FileBrowserSheet createFileBrowserSheet();

    public ScrollBar createScrollBar();

    public ScrollPane createScrollPane();

    public Separator createSeparator();

    public Slider createSlider();

    public Spinner createSpinner();

    public Tooltip createTooltip();

    public Window createWindow();

    public Sheet createSheet();

    public Prompt createPrompt();

    public Panorama createPanorama();

    public BigDecimalValidator createBigDecimalValidator();

    public BigIntegerValidator createBigIntegerValidator();

    public ComparableRangeValidator<?> createComparableRangeValidator();

    public ComparableValidator<?> createComparableValidator();

    public DecimalValidator createDecimalValidator();

    public DoubleRangeValidator createDoubleRangeValidator();

    public DoubleValidator createDoubleValidator();

    public EmptyTextValidator createEmptyTextValidator();

    public FloatRangeValidator createFloatRangeValidator();

    public FloatValidator createFloatValidator();

    public IntRangeValidator createIntRangeValidator();

    public IntValidator createIntValidator();

    public NotEmptyTextValidator createNotEmptyTextValidator();

    public RegexTextValidator createRegexTextValidator();

    public CalendarDate createCalendarDate();

    public Time createTime();

    public TaskGroup createTaskGroup();

    public TaskSequence createTaskSequence();

}
