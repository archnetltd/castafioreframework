package org.castafiore.demo;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.easyui.grid.DataGrid;
import org.castafiore.easyui.grid.DataGridColumnModel;
import org.castafiore.easyui.grid.DataGridModel;
import org.castafiore.easyui.grid.PropertyGrid;
import org.castafiore.easyui.grid.PropertyGridModel;
import org.castafiore.easyui.grid.Tree;
import org.castafiore.easyui.layout.Layout;
import org.castafiore.easyui.layout.RegionPanel;
import org.castafiore.easyui.layout.TabModel;
import org.castafiore.easyui.layout.Tabs;
import org.castafiore.ui.Container;
import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXAddressAutoComplete;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXDateTimePicker;
import org.castafiore.ui.ex.form.EXDimensionInput;
import org.castafiore.ui.ex.form.EXEditableLabel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.EXSlider;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXTimePicker;
import org.castafiore.ui.ex.form.EXTimeRangerPicker;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.EXSplitButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.ui.menu.EXMenu;
import org.castafiore.ui.menu.EXMenuItem;
import org.castafiore.ui.tabbedpane.TabPanel;

public class ShowCase extends EXApplication implements DescriptibleApplication{

	public ShowCase() {
		super("showcase");
		//addTree();
//		testLayouts();
//addPropertyGrid();
//		addDataGrid();
		setStyle("font-size", "64%");
		EXBorderLayoutContainer bl = new EXBorderLayoutContainer("df");
		bl.setDisplay(true);
		bl.addChild(addButtons(), EXBorderLayoutContainer.TOP);
		bl.addChild(getForms(), EXBorderLayoutContainer.CENTER);
		addChild(bl);
//		addChild(getTabs());
	}
	
	
	public Tabs getTabs(){
		TabModel model = new TabModel() {
			
			@Override
			public int size() {
				return 3;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				return "Tab label " + index;
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
			
					return new EXLabel("sdfsd", "sdfsfsdfsdfsd");
			}
			
			@Override
			public int getSelectedTab() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean isClosable(int index) {
				return true;
			}
			
			@Override
			public String getIconCls(int index) {
				return "icon-save";
			}
		};
		
		Tabs tabs = new Tabs("sfs").setModel(model).setFit(true);
		return tabs;
	}
	
	public void testLayouts(){
		
		
		Layout layout = new Layout("my layout");
		
		RegionPanel north = new RegionPanel("north", "North panel","north");
		north.setIconCls("icon-add").setSplit(true).setStyle("height", "100px");
		
		RegionPanel center = new RegionPanel("center", "Center panel","center");
		center.setIconCls("icon-add").setSplit(true).setStyle("height", "300px");
		
		RegionPanel east = new RegionPanel("east", "east panel","east");
		east.setIconCls("icon-add").setSplit(true).setStyle("width", "200px");
		
		RegionPanel west = new RegionPanel("west", "west panel","west");
		west.setIconCls("icon-add").setSplit(true).setStyle("width", "200px");
		
		RegionPanel south = new RegionPanel("south", "south panel","south");
		south.setIconCls("icon-add").setSplit(true).setStyle("height", "75px");
		
		layout.addRegion(north);
		layout.addRegion(west);
		layout.addRegion(center);
		layout.addRegion(east);
		//layout.addRegion(south);
		
		center.addChild(this.getTabs().setStyle("width", "500px").setStyle("height", "500px"));
		north.addChild(this.addButtons());
		west.addChild(this.addPropertyGrid());
		east.addChild(this.addTree());
		//addChild(layout)
		addChild(layout.setStyle("width", "1000px").setStyle("height", "600px"));
		//panel.setBody(layout);
		
		//addChild(panel.setStyle("height", "700px").setStyle("width", "700px"));
		
		
	}
	
	public Tree addTree(){
		org.castafiore.easyui.grid.TreeNode node = new org.castafiore.easyui.grid.TreeNode() {
			
			@Override
			public boolean isOpen() {
				return false;
			}
			
			@Override
			public boolean isChecked() {
				return true;
			}
			
			@Override
			public String getText() {
				return "root";
			}
			
			@Override
			public String getId() {
				return "0";
				
			}
			
			@Override
			public List<org.castafiore.easyui.grid.TreeNode> getChildren() {
				List<org.castafiore.easyui.grid.TreeNode> children = new ArrayList<org.castafiore.easyui.grid.TreeNode>();
				for(int i = 0; i < 10; i ++){
					final int count = i;
					org.castafiore.easyui.grid.TreeNode c1 = new org.castafiore.easyui.grid.TreeNode() {
						
						@Override
						public boolean isOpen() {
							
							return false;
						}
						
						@Override
						public boolean isChecked() {
							
							return false;
						}
						
						@Override
						public String getText() {
							return "Child 1 " + count;
						}
						
						@Override
						public String getId() {
							return "0/" + count;
						}
						
						@Override
						public List<org.castafiore.easyui.grid.TreeNode> getChildren() {
							List<org.castafiore.easyui.grid.TreeNode> c2 = new ArrayList<org.castafiore.easyui.grid.TreeNode>();
							//final int count1 =0;
							for(int j = 0; j < 10; j++){
								final int count1=j;
								
								org.castafiore.easyui.grid.TreeNode n2 = new org.castafiore.easyui.grid.TreeNode() {
									
									@Override
									public boolean isOpen() {
										// TODO Auto-generated method stub
										return false;
									}
									
									@Override
									public boolean isChecked() {
										// TODO Auto-generated method stub
										return false;
									}
									
									@Override
									public String getText() {
										return "Node --" + count1; 
									}
									
									@Override
									public String getId() {
										return "0/" + count + "/" + count1;
									}
									
									@Override
									public List<org.castafiore.easyui.grid.TreeNode> getChildren() {
										return new ArrayList<org.castafiore.easyui.grid.TreeNode>();
									}

									@Override
									public boolean isLeaf() {
										// TODO Auto-generated method stub
										return true;
									}
								}; 
								c2.add(n2);
							}
							return c2;
						}

						@Override
						public boolean isLeaf() {
							// TODO Auto-generated method stub
							return false;
						}
					};
					children.add(c1);
				}
				return children;
			}

			@Override
			public boolean isLeaf() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		
		Tree tree = new Tree("dfsd").setRootNode(node).setAnimate(true);
		tree.showCheckbox(true).showLines(true).enableDragNDrop(true);
		return tree;
	}
	
	public PropertyGrid addPropertyGrid(){
		PropertyGridModel model = new PropertyGridModel() {
			
			@Override
			public int size() {
				return 7;
			}
			
			@Override
			public String getValue(int index) {
				return "Field " + index + " :";
			}
			
			@Override
			public String getName(int index) {
				return "field" + index;
			}
			
			@Override
			public String getGroup(int index) {

				if(index < 4){
					return "Manager";
				}return "Director";
				
			}
			
			@Override
			public String getEditor(int index) {
				return "text";
			}
		};
		
		PropertyGrid g = new PropertyGrid("pp").setModel(model).showGroup(true);
		return g;
		
		
	}
	
	public DataGrid addDataGrid(){
		DataGridColumnModel cols = new DataGridColumnModel() {
			
			@Override
			public int size() {
				return 5;
			}
			
			@Override
			public boolean isSortable(int index) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isResizable(int index) {
				// TODO Auto-generated method stub
				return true;
				
			}
			
			@Override
			public boolean isIdentityField(int index) {
				return index==0;
			}
			
			@Override
			public boolean isHidden(int index) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isCheckBox(int index) {
				// TODO Auto-generated method stub
				return index ==4;
			}
			
			@Override
			public int getWidth(int index) {
				// TODO Auto-generated method stub
				return 200;
			}
			
			@Override
			public String getTitle(int index) {
				// TODO Auto-generated method stub
				return "Field title " + index;
			}
			
			@Override
			public int getRowSpan(int index) {
				// TODO Auto-generated method stub
				return 1;
			}
			
			@Override
			public String getOrder(int index) {
				// TODO Auto-generated method stub
				return "asc";
			}
			
			@Override
			public String getName(int index) {
				// TODO Auto-generated method stub
				return "field" + index;
			}
			
			@Override
			public String getHAlign(int index) {
				// TODO Auto-generated method stub
				return "center";
			}
			
			@Override
			public String getAlign(int index) {
				// TODO Auto-generated method stub
				return "center";
			}
		};
		
		
		
		DataGridModel model = new DataGridModel() {
			
			@Override
			public int size() {
				return 200;
			}
			
			@Override
			public Object getValueAt(int row, int col) {
				return "Value Matrix(" + col + "," + row + ")";
			}
			
			
		};
		DataGrid grid = new DataGrid("grid");
		grid.setModel(model).setColumnModel(cols).showPagination(true);
		return grid;
		//addChild(grid);
	}
	
	
	public EXToolBar addButtons(){
		EXToolBar tb = new EXToolBar("dfs");
		tb.addItem(new EXIconButton("sdsd", "A simple button"));
		tb.addItem(new EXIconButton("sdasa", "Button left icon", Icons.ICON_FLAG));
		tb.addItem(new EXIconButton("eeee", "icons icons", Icons.ICON_FLAG, Icons.ICON_TRIANGLE_1_S));
		
		EXButtonSet bs = new EXButtonSet("ssssssssss");
		bs.addItem(new EXIconButton("sdsd", null, Icons.ICON_GEAR));
		bs.addItem(new EXIconButton("sdasa", null, Icons.ICON_FLAG));
		bs.addItem(new EXIconButton("eeee", null, Icons.ICON_FLAG, Icons.ICON_TRIANGLE_1_S));
		
		tb.addItem(bs);
		 TreeNode<EXMenuItem> n=null;
		n= new TreeNode<EXMenuItem>() {
			
			@Override
			public void refresh() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isLeaf() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public TreeNode<EXMenuItem> getParent() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public TreeNode<EXMenuItem> getNodeAt(final int index) {
				return new TreeNode<EXMenuItem>() {

					@Override
					public int childrenCount() {
						
						return 0;
					}

					@Override
					public TreeNode<EXMenuItem> getNodeAt(int index) {
					
						return null;
					}

					@Override
					public EXMenuItem getComponent() {
						
						return new EXMenuItem("dfs", "Item - " + index);
					}

					@Override
					public boolean isLeaf() {
						return true;
					}

					@Override
					public TreeNode<EXMenuItem> getParent() {
						return null;
					}

					@Override
					public void refresh() {
						
						
					}
				};
			}
			
			@Override
			public EXMenuItem getComponent() {
				return new EXMenuItem("dfs", "Root");
			}
			
			@Override
			public int childrenCount() {
				return 5;
			}
		};
		EXSplitButton split = new EXSplitButton("sdfs", new EXButton("dsss", "Here we are "), new EXMenu("menu",n ));
		tb.addItem(split);
		
		return tb;
	}
	
	
	public EXDynaformPanel getForms(){
		EXDynaformPanel panel = new EXDynaformPanel("panel", "Dynaform panel");
		panel.addField("Text field", new EXInput("textfield"));
		panel.addField("Password Field", new EXPassword("passwordfield"));
		
		List<String> dict = new ArrayList<String>();
		for(int i = 0; i < 10; i++){
			dict.add("Item " + i);
		}
		panel.addField("Autocomplete", new EXAutoComplete("autocomplete", "",dict));
		
		panel.addField("Address", new EXAddressAutoComplete("address",""));
		
		panel.addField("Checkbox", new EXCheckBox("checkbox",true));
		
		panel.addField("Color", new EXColorPicker("color",""));
		
		panel.addField("Date", new EXDatePicker("date"));
		
		panel.addField("Time", new EXDateTimePicker("time"));
		
		panel.addField("Dimension", new EXDimensionInput("dimension"));
		
		panel.addField("Editable label", new EXEditableLabel("dimension",""));
		
		panel.addField("Maskable", new EXMaskableInput("dimension", "", "##-##"));
		
		panel.addField("Text area", new EXTextArea("textarea"));
		
		panel.addField("Rich text", new EXRichTextArea("rta"));
		
		
		panel.addField("Slider", new EXSlider("slider"));
		
		panel.addField("Time picker", new EXTimePicker("rta"));
		
		panel.addField("Time range", new EXTimeRangerPicker("rt"));
		
		panel.addField("Upload", new EXUpload("rt"));
		
		
		panel.addButton(new EXButton("save", "Save"));
		
		panel.addButton(new EXButton("cancel", "Cancel"));
		
		panel.setStyle("width", "100%");
		panel.setDraggable(false);
		return panel;
		
		
	}


	@Override
	public String getDescription() {
		return "Showcase of form components";
	}
	
	

}
