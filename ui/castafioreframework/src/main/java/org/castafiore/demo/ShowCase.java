package org.castafiore.demo;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.button.EXButton;
import org.castafiore.ui.ex.button.EXButtonSet;
import org.castafiore.ui.ex.button.EXIconButton;
import org.castafiore.ui.ex.button.EXSplitButton;
import org.castafiore.ui.ex.button.Icons;
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
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.EXSlider;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXTimePicker;
import org.castafiore.ui.ex.form.EXTimeRangerPicker;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.navigation.EXMenu;
import org.castafiore.ui.ex.navigation.EXMenuItem;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.ex.tree.TreeNode;

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
