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

import java.util.prefs.Preferences;
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
import org.moosbusch.lumPi.beans.PivotBeanConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class DefaultPivotBeanConfiguration implements PivotBeanConfiguration {

    @Bean
    @Lazy
    @Override
    public Sequence.Tree.ImmutablePath createImmutablePath() {
        return new Sequence.Tree.ImmutablePath();
    }

    @Bean
    @Lazy
    @Override
    public Sequence.Tree<? extends Object> createTree() {
        return new Sequence.Tree<>();
    }

    @Bean
    @Lazy
    @Override
    public ArrayList<? extends Object> createArrayList() {
        return new ArrayList<>();
    }

    @Bean
    @Lazy
    @Override
    public ArrayQueue<? extends Object> createArrayQueue() {
        return new ArrayQueue<>();
    }

    @Bean
    @Lazy
    @Override
    public ArrayStack<? extends Object> createArrayStack() {
        return new ArrayStack<>();
    }

    @Bean
    @Lazy
    @Override
    public HashSet<? extends Object> createHashSet() {
        return new HashSet<>();
    }

    @Bean
    @Lazy
    @Override
    public HashMap<? extends Object, ? extends Object> createHashMap() {
        return new HashMap<>();
    }

    @Bean
    @Lazy
    @Override
    public LinkedList<? extends Object> createLinkedList() {
        return new LinkedList<>();
    }

    @Bean
    @Lazy
    @Override
    public LinkedQueue<? extends Object> createLinkedQueue() {
        return new LinkedQueue<>();
    }

    @Bean
    @Lazy
    @Override
    public LinkedStack<? extends Object> createLinkedStack() {
        return new LinkedStack<>();
    }

    @Bean
    @Lazy
    @Override
    public FileList createFileList() {
        return new FileList();
    }

    @Bean
    @Lazy
    @Override
    public CalendarDate createCalendarDate() {
        return new CalendarDate();
    }

    @Bean
    @Lazy
    @Override
    public Time createTime() {
        return new Time();
    }

    @Bean
    @Lazy
    @Override
    public TaskGroup createTaskGroup() {
        return new TaskGroup();
    }

    @Bean
    @Lazy
    @Override
    public TaskSequence createTaskSequence() {
        return new TaskSequence();
    }

    @Bean
    @Lazy
    @Override
    public BaselineDecorator createBaselineDecorator() {
        return new BaselineDecorator();
    }

    @Bean
    @Lazy
    @Override
    public BlurDecorator createBlurDecorator() {
        return new BlurDecorator();
    }

    @Bean
    @Lazy
    @Override
    public ClipDecorator createClipDecorator() {
        return new ClipDecorator();
    }

    @Bean
    @Lazy
    @Override
    public DropShadowDecorator createDropShadowDecorator() {
        return new DropShadowDecorator();
    }

    @Bean
    @Lazy
    @Override
    public FadeDecorator createFadeDecorator() {
        return new FadeDecorator();
    }

    @Bean
    @Lazy
    @Override
    public GrayscaleDecorator createGrayscaleDecorator() {
        return new GrayscaleDecorator();
    }

    @Bean
    @Lazy
    @Override
    public OverlayDecorator createOverlayDecorator() {
        return new OverlayDecorator();
    }

    @Bean
    @Lazy
    @Override
    public ReflectionDecorator createReflectionDecorator() {
        return new ReflectionDecorator();
    }

    @Bean
    @Lazy
    @Override
    public RotationDecorator createRotationDecorator() {
        return new RotationDecorator();
    }

    @Bean
    @Lazy
    @Override
    public SaturationDecorator createSaturationDecorator() {
        return new SaturationDecorator();
    }

    @Bean
    @Lazy
    @Override
    public ScaleDecorator createScaleDecorator() {
        return new ScaleDecorator();
    }

    @Bean
    @Lazy
    @Override
    public ShadeDecorator createShadeDecorator() {
        return new ShadeDecorator();
    }

    @Bean
    @Lazy
    @Override
    public TagDecorator createTagDecorator() {
        return new TagDecorator();
    }

    @Bean
    @Lazy
    @Override
    public TranslationDecorator createTranslationDecorator() {
        return new TranslationDecorator();
    }

    @Bean
    @Lazy
    @Override
    public WatermarkDecorator createWatermarkDecorator() {
        return new WatermarkDecorator();
    }

    @Bean
    @Lazy
    @Override
    public Circular createCircular() {
        return new Circular();
    }

    @Bean
    @Lazy
    @Override
    public Cubic createCubic() {
        return new Cubic();
    }

    @Bean
    @Lazy
    @Override
    public Exponential createExponential() {
        return new Exponential();
    }

    @Bean
    @Lazy
    @Override
    public Linear createLinear() {
        return new Linear();
    }

    @Bean
    @Lazy
    @Override
    public Quadratic createQuadratic() {
        return new Quadratic();
    }

    @Bean
    @Lazy
    @Override
    public Quartic createQuartic() {
        return new Quartic();
    }

    @Bean
    @Lazy
    @Override
    public Quintic createQuintic() {
        return new Quintic();
    }

    @Bean
    @Lazy
    @Override
    public Sine createSine() {
        return new Sine();
    }

    @Bean
    @Lazy
    @Override
    public BulletedList createBulletedList() {
        return new BulletedList();
    }

    @Bean
    @Lazy
    @Override
    public ComponentNode createComponentNode() {
        return new ComponentNode();
    }

    @Bean
    @Lazy
    @Override
    public Document createDocument() {
        return new Document();
    }

    @Bean
    @Lazy
    @Override
    public ImageNode createImageNode() {
        return new ImageNode();
    }

    @Bean
    @Lazy
    @Override
    public List.Item createTextListItem() {
        return new List.Item();
    }

    @Bean
    @Lazy
    @Override
    public NumberedList createNumberedList() {
        return new NumberedList();
    }

    @Bean
    @Lazy
    @Override
    public Paragraph createParagraph() {
        return new Paragraph();
    }

    @Bean
    @Lazy
    @Override
    public Span createSpan() {
        return new Span();
    }

    @Bean
    @Lazy
    @Override
    public TextNode createTextNode() {
        return new TextNode();
    }

    @Bean
    @Lazy
    @Override
    public TextSpan createTextSpan() {
        return new TextSpan();
    }

    @Bean
    @Lazy
    @Override
    public BigDecimalValidator createBigDecimalValidator() {
        return new BigDecimalValidator();
    }

    @Bean
    @Lazy
    @Override
    public BigIntegerValidator createBigIntegerValidator() {
        return new BigIntegerValidator();
    }

    @Bean
    @Lazy
    @Override
    public ComparableRangeValidator<?> createComparableRangeValidator() {
        return new ComparableRangeValidator();
    }

    @Bean
    @Lazy
    @Override
    public ComparableValidator<?> createComparableValidator() {
        return new ComparableValidator();
    }

    @Bean
    @Lazy
    @Override
    public DecimalValidator createDecimalValidator() {
        return new DecimalValidator();
    }

    @Bean
    @Lazy
    @Override
    public DoubleRangeValidator createDoubleRangeValidator() {
        return new DoubleRangeValidator();
    }

    @Bean
    @Lazy
    @Override
    public DoubleValidator createDoubleValidator() {
        return new DoubleValidator();
    }

    @Bean
    @Lazy
    @Override
    public EmptyTextValidator createEmptyTextValidator() {
        return new EmptyTextValidator();
    }

    @Bean
    @Lazy
    @Override
    public FloatRangeValidator createFloatRangeValidator() {
        return new FloatRangeValidator();
    }

    @Bean
    @Lazy
    @Override
    public FloatValidator createFloatValidator() {
        return new FloatValidator();
    }

    @Bean
    @Lazy
    @Override
    public IntRangeValidator createIntRangeValidator() {
        return new IntRangeValidator();
    }

    @Bean
    @Lazy
    @Override
    public IntValidator createIntValidator() {
        return new IntValidator();
    }

    @Bean
    @Lazy
    @Override
    public NotEmptyTextValidator createNotEmptyTextValidator() {
        return new NotEmptyTextValidator();
    }

    @Bean
    @Lazy
    @Override
    public RegexTextValidator createRegexTextValidator() {
        return new RegexTextValidator();
    }

    @Bean
    @Lazy
    @Override
    public FileSerializer createFileSerializer() {
        return new FileSerializer();
    }

    @Bean
    @Lazy
    @Override
    public BinarySerializer createBinarySerializer() {
        return new BinarySerializer();
    }

    @Bean
    @Lazy
    @Override
    public BufferedImageSerializer createBufferedImageSerializer() {
        return new BufferedImageSerializer();
    }

    @Bean
    @Lazy
    @Override
    public BXMLSerializer createBXMLSerializer() {
        return new BXMLSerializer();
    }

    @Bean
    @Lazy
    @Override
    public ByteArraySerializer createByteArraySerializer() {
        return new ByteArraySerializer();
    }

    @Bean
    @Lazy
    @Override
    public CSVSerializer createCSVSerializer() {
        return new CSVSerializer();
    }

    @Bean
    @Lazy
    @Override
    public JSONSerializer createJSONSerializer() {
        return new JSONSerializer();
    }

    @Bean
    @Lazy
    @Override
    public PlainTextSerializer createPlainTextSerializer() {
        return new PlainTextSerializer();
    }

    @Bean
    @Lazy
    @Override
    public PropertiesSerializer createPropertiesSerializer() {
        return new PropertiesSerializer();
    }

    @Bean
    @Lazy
    @Override
    public StringSerializer createStringSerializer() {
        return new StringSerializer();
    }

    @Bean
    @Lazy
    @Override
    public SVGDiagramSerializer createSVGDiagramSerializer() {
        return new SVGDiagramSerializer();
    }

    @Bean
    @Lazy
    @Override
    public XMLSerializer createXMLSerializer() {
        return new XMLSerializer();
    }

    @Bean
    @Lazy
    @Override
    public TextInput createTextInput() {
        return new TextInput();
    }

    @Bean
    @Lazy
    @Override
    public TextArea createTextArea() {
        return new TextArea();
    }

    @Bean
    @Lazy
    @Override
    public TextPane createTextPane() {
        return new TextPane();
    }

    @Bean
    @Lazy
    @Override
    public RadioButtonGroup createRadioButtonGroup() {
        return new RadioButtonGroup();
    }

    @Bean
    @Lazy
    @Override
    public ButtonGroup createButtonGroup() {
        return new ButtonGroup();
    }

    @Bean
    @Lazy
    @Override
    public PushButton createPushButton() {
        return new PushButton();
    }

    @Bean
    @Lazy
    @Override
    public LinkButton createLinkButton() {
        return new LinkButton();
    }

    @Bean
    @Lazy
    @Override
    public ListButton createListButton() {
        return new ListButton();
    }

    @Bean
    @Lazy
    @Override
    public Rollup createRollup() {
        return new Rollup();
    }

    @Bean
    @Lazy
    @Override
    public RadioButton createRadioButton() {
        return new RadioButton();
    }

    @Bean
    @Lazy
    @Override
    public ListView createListView() {
        return new ListView();
    }

    @Bean
    @Lazy
    @Override
    public TableView createTableView() {
        return new TableView();
    }

    @Bean
    @Lazy
    @Override
    public TreeView createTreeView() {
        return new TreeView();
    }

    @Bean
    @Lazy
    @Override
    public MenuButton createMenuButton() {
        return new MenuButton();
    }

    @Bean
    @Lazy
    @Override
    public Menu createMenu() {
        return new Menu();
    }

    @Bean
    @Lazy
    @Override
    public Menu.Item createMenuItem() {
        return new Menu.Item();
    }

    @Bean
    @Lazy
    @Override
    public Menu.Section createMenuSection() {
        return new Menu.Section();
    }

    @Bean
    @Lazy
    @Override
    public MenuBar createMenuBar() {
        return new MenuBar();
    }

    @Bean
    @Lazy
    @Override
    public MenuBar.Item createMenuBarItem() {
        return new MenuBar.Item();
    }

    @Bean
    @Lazy
    @Override
    public MenuPopup createMenuPopup() {
        return new MenuPopup();
    }

    @Bean
    @Lazy
    @Override
    public SuggestionPopup createSuggestionPopup() {
        return new SuggestionPopup();
    }

    @Bean
    @Lazy
    @Override
    public Meter createMeter() {
        return new Meter();
    }

    @Bean
    @Lazy
    @Override
    public CalendarButton createCalendarButton() {
        return new CalendarButton();
    }

    @Bean
    @Lazy
    @Override
    public Calendar createCalendar() {
        return new Calendar();
    }

    @Bean
    @Lazy
    @Override
    public ColorChooserButton createColorChooserButton() {
        return new ColorChooserButton();
    }

    @Bean
    @Lazy
    @Override
    public ColorChooser createColorChooser() {
        return new ColorChooser();
    }

    @Bean
    @Lazy
    @Override
    public Checkbox createCheckbox() {
        return new Checkbox();
    }

    @Bean
    @Lazy
    @Override
    public Label createLabel() {
        return new Label();
    }

    @Bean
    @Lazy
    @Override
    public CardPane createCardPane() {
        return new CardPane();
    }

    @Bean
    @Lazy
    @Override
    public GridPane createGridPane() {
        return new GridPane();
    }

    @Bean
    @Lazy
    @Override
    public GridPane.Row createGridPaneRow() {
        return new GridPane.Row();
    }

    @Bean
    @Lazy
    @Override
    public TablePane createTablePane() {
        return new TablePane();
    }

    @Bean
    @Lazy
    @Override
    public TablePane.Column createTablePaneColumn() {
        return new TablePane.Column();
    }

    @Bean
    @Lazy
    @Override
    public TablePane.Row createTablePaneRow() {
        return new TablePane.Row();
    }

    @Bean
    @Lazy
    @Override
    public TabPane createTabPane() {
        return new TabPane();
    }

    @Bean
    @Lazy
    @Override
    public BoxPane createBoxPane() {
        return new BoxPane();
    }

    @Bean
    @Lazy
    @Override
    public SplitPane createSplitPane() {
        return new SplitPane();
    }

    @Bean
    @Lazy
    @Override
    public StackPane createStackPane() {
        return new StackPane();
    }

    @Bean
    @Lazy
    @Override
    public FillPane createFillPane() {
        return new FillPane();
    }

    @Bean
    @Lazy
    @Override
    public Border createBorder() {
        return new Border();
    }

    @Bean
    @Lazy
    @Override
    public Panel createPanel() {
        return new Panel();
    }

    @Bean
    @Lazy
    @Override
    public Palette createPalette() {
        return new Palette();
    }

    @Bean
    @Lazy
    @Override
    public Accordion createAccordion() {
        return new Accordion();
    }

    @Bean
    @Lazy
    @Override
    public ActivityIndicator createActivityIndicator() {
        return new ActivityIndicator();
    }

    @Bean
    @Lazy
    @Override
    public Alert createAlert() {
        return new Alert(new String());
    }

    @Bean
    @Lazy
    @Override
    public Dialog createDialog() {
        return new Dialog();
    }

    @Bean
    @Lazy
    @Override
    public Form createForm() {
        return new Form();
    }

    @Bean
    @Lazy
    @Override
    public Form.Section createFormSection() {
        return new Form.Section();
    }

    @Bean
    @Lazy
    @Override
    public Frame createFrame() {
        return new Frame();
    }

    @Bean
    @Lazy
    @Override
    public ImageView createImageView() {
        return new ImageView();
    }

    @Bean
    @Lazy
    @Override
    public Expander createExpander() {
        return new Expander();
    }

    @Bean
    @Lazy
    @Override
    public FileBrowser createFileBrowser() {
        return new FileBrowser();
    }

    @Bean
    @Lazy
    @Override
    public FileBrowserSheet createFileBrowserSheet() {
        return new FileBrowserSheet();
    }

    @Bean
    @Lazy
    @Override
    public ScrollBar createScrollBar() {
        return new ScrollBar();
    }

    @Bean
    @Lazy
    @Override
    public ScrollPane createScrollPane() {
        return new ScrollPane();
    }

    @Bean
    @Lazy
    @Override
    public Separator createSeparator() {
        return new Separator();
    }

    @Bean
    @Lazy
    @Override
    public Slider createSlider() {
        return new Slider();
    }

    @Bean
    @Lazy
    @Override
    public Spinner createSpinner() {
        return new Spinner();
    }

    @Bean
    @Lazy
    @Override
    public Tooltip createTooltip() {
        return new Tooltip();
    }

    @Bean
    @Lazy
    @Override
    public Window createWindow() {
        return new Window();
    }

    @Bean
    @Lazy
    @Override
    public Sheet createSheet() {
        return new Sheet();
    }

    @Bean
    @Lazy
    @Override
    public Prompt createPrompt() {
        return new Prompt();
    }

    @Bean
    @Lazy
    @Override
    public Panorama createPanorama() {
        return new Panorama();
    }

    @Bean
    @Lazy
    @Override
    public AccordionHeaderDataRenderer createAccordionHeaderDataRenderer() {
        return new AccordionHeaderDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public ButtonData createButtonData() {
        return new ButtonData();
    }

    @Bean
    @Lazy
    @Override
    public ButtonDataRenderer createButtonDataRenderer() {
        return new ButtonDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public CalendarButtonDataRenderer createCalendarButtonDataRenderer() {
        return new CalendarButtonDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public CalendarDateSpinnerData createCalendarDateSpinnerData() {
        return new CalendarDateSpinnerData();
    }

    @Bean
    @Lazy
    @Override
    public ColorItem createColorItem() {
        return new ColorItem();
    }

    @Bean
    @Lazy
    @Override
    public LinkButtonDataRenderer createLinkButtonDataRenderer() {
        return new LinkButtonDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public ListButtonColorItemRenderer createListButtonColorItemRenderer() {
        return new ListButtonColorItemRenderer();
    }

    @Bean
    @Lazy
    @Override
    public ListButtonDataRenderer createListButtonDataRenderer() {
        return new ListButtonDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public ListItem createListItem() {
        return new ListItem();
    }

    @Bean
    @Lazy
    @Override
    public ListViewColorItemRenderer createListViewColorItemRenderer() {
        return new ListViewColorItemRenderer();
    }

    @Bean
    @Lazy
    @Override
    public ListViewColorItemRenderer.ColorBadge createColorBadge() {
        return new ListViewColorItemRenderer.ColorBadge();
    }

    @Bean
    @Lazy
    @Override
    public ListViewItemEditor createListViewItemEditor() {
        return new ListViewItemEditor();
    }

    @Bean
    @Lazy
    @Override
    public ListViewItemRenderer createListViewItemRenderer() {
        return new ListViewItemRenderer();
    }

    @Bean
    @Lazy
    @Override
    public MenuBarItemDataRenderer createMenuBarItemDataRenderer() {
        return new MenuBarItemDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public MenuButtonDataRenderer createMenuButtonDataRenderer() {
        return new MenuButtonDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public MenuItemData createMenuItemData() {
        return new MenuItemData();
    }

    @Bean
    @Lazy
    @Override
    public MenuItemDataRenderer createMenuItemDataRenderer() {
        return new MenuItemDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public NumericSpinnerData createNumericSpinnerData() {
        return new NumericSpinnerData();
    }

    @Bean
    @Lazy
    @Override
    public SpinnerItemRenderer createSpinnerItemRenderer() {
        return new SpinnerItemRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewBooleanCellRenderer createTableViewBooleanCellRenderer() {
        return new TableViewBooleanCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewCellRenderer createTableViewCellRenderer() {
        return new TableViewCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewCheckboxCellRenderer createTableViewCheckboxCellRenderer() {
        return new TableViewCheckboxCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewDateCellRenderer createTableViewDateCellRenderer() {
        return new TableViewDateCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewFileSizeCellRenderer createTableViewFileSizeCellRenderer() {
        return new TableViewFileSizeCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewHeaderData createTableViewHeaderData() {
        return new TableViewHeaderData();
    }

    @Bean
    @Lazy
    @Override
    public TableViewHeaderDataRenderer createTableViewHeaderDataRenderer() {
        return new TableViewHeaderDataRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewImageCellRenderer createTableViewImageCellRenderer() {
        return new TableViewImageCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewMultiCellRenderer createTableViewMultiCellRenderer() {
        return new TableViewMultiCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewMultiCellRenderer.RendererMapping createRendererMapping() {
        return new TableViewMultiCellRenderer.RendererMapping();
    }

    @Bean
    @Lazy
    @Override
    public TableViewNumberCellRenderer createTableViewNumberCellRenderer() {
        return new TableViewNumberCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewRowEditor createTableViewRowEditor() {
        return new TableViewRowEditor();
    }

    @Bean
    @Lazy
    @Override
    public TableViewTextAreaCellRenderer createTableViewTextAreaCellRenderer() {
        return new TableViewTextAreaCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TableViewTriStateCellRenderer createTableViewTriStateCellRenderer() {
        return new TableViewTriStateCellRenderer();
    }

    @Bean
    @Lazy
    @Override
    public TreeBranch createTreeBranch() {
        return new TreeBranch();
    }

    @Bean
    @Lazy
    @Override
    public TreeNode createTreeNode() {
        return new TreeNode();
    }

    @Bean
    @Lazy
    @Override
    public TreeViewNodeEditor createTreeViewNodeEditor() {
        return new TreeViewNodeEditor();
    }

    @Bean
    @Lazy
    @Override
    public TreeViewNodeRenderer createTreeViewNodeRenderer() {
        return new TreeViewNodeRenderer();
    }
}
