package org.castafiore;

import java.awt.Color;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXApplication;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.dynaform.DynaForm;
import org.castafiore.ui.dynaform.validator.RequiredValidator;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaForm;
import org.castafiore.ui.ex.form.EXButton;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXRange;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.list.DefaultDataModel;
import org.castafiore.ui.ex.list.EXRadio;
import org.castafiore.ui.ex.list.EXSelect;
import org.castafiore.ui.ex.tab.EXTabPanel;
import org.castafiore.ui.ex.tree.DefaultMutableTreeNode;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.layout.EXBorderLayout;
import org.castafiore.ui.layout.EXGridLayout;
import org.castafiore.ui.layout.EXHLayout;
import org.castafiore.ui.layout.EXVLayout;
import org.castafiore.ui.tab.TabModel;
import org.castafiore.ui.tab.TabPanel;

public class Test extends EXApplication{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Test() {
		super("test");
		setStyle("font-size", "62%");
	}

	@Override
	public void initApp() {
		//setStyle("font-size", "67%");
		final EXDynaForm form = new EXDynaForm("form", "Dynaform");
		
		form.setDraggable(false);
		
		EXInput input = new EXInput("input");
		 form.addField("Input",input);
		 EXColorPicker picker = new EXColorPicker("color", Color.BLACK);
		 form.addField("Color",picker);
		 
		 form.addField("Label",new EXLabel("label", "hello world"));
		 
		 form.addField("Mask",new EXMaskableInput("mask", "9999-9999", "9999-9999"));
		 
		 form.addField("Password",new EXPassword("password", "cool shen"));
		 
		 form.addField("Textarea",new EXTextArea("textarea"));
		 
		 form.addField("Check box",new EXCheckBox("cb"));
		 
		 form.addField("Range",new EXRange("range", 100).setMin(0).setMax(200).setStep(20));
		 form.addValidator("input", new RequiredValidator());
		 
		 form.addValidator("textarea", new RequiredValidator());
		 
		 DefaultDataModel<KeyValuePair> model = new DefaultDataModel<KeyValuePair>();
		 model.addItem(new SimpleKeyValuePair("a", "A"), new SimpleKeyValuePair("b", "B"), new SimpleKeyValuePair("c", "C"));
		 EXSelect<KeyValuePair> select = new EXSelect<KeyValuePair>("select");
		 select.setModel(model);
		 
		 form.addField("Select",select);
		 
		 form.addField("Radio", new EXRadio<KeyValuePair>("radio").setModel(model));
		 
		form.addButton((Button)new EXButton("btn", "Submit").addEvent(DynaForm.SUBMIT, Event.CLICK));
		
		form.addButton((Button)new EXButton("reset", "Reset").addEvent(DynaForm.RESET, Event.CLICK));
		
		
		
		EXTabPanel tabs = new EXTabPanel("tab", new TabModel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public int size() {
				return 2;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				if(index == 0){
					return "Form";
				}else{
					return "Layout";
				}
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				if(index == 0)
					return form;
				else
					return layout();
			}
			
			@Override
			public int getSelectedTab() {
				return 0;
			}
		});
		
		addChild(tabs);
	}
	
	
	public Container layout(){
		EXBorderLayout border = new EXBorderLayout("border");
		
		EXHLayout hlayout = new EXHLayout();
		for(int i = 0; i < 5; i ++){
			Container c = new EXContainer("", "div").setStyle("width", "175px").setStyle("height", "30px").setStyle("background", "steelblue").setStyle("margin", "5px");
			hlayout.addChild(c, i + "");
			
		}
		
		border.addChild(hlayout, EXBorderLayout.TOP);
		
		EXVLayout vlayout = new EXVLayout();
		for(int i = 0; i < 5; i ++){
			Container c = new EXContainer("", "div").setStyle("width", "175px").setStyle("height", "30px").setStyle("background", "steelblue").setStyle("margin", "5px");
			vlayout.addChild(c, i + "");
			
		}
		
		border.addChild(vlayout, EXBorderLayout.LEFT);
		
		EXGridLayout gl = new EXGridLayout("grid", 5, 5);
		for(int i = 0; i < 5; i ++){
			Container c = new EXContainer("", "div").setStyle("width", "175px").setStyle("height", "30px").setStyle("background", "steelblue").setStyle("margin", "5px");
			gl.addChild(c, i + ":" + i);
			
		}
		
		border.addChild(gl, EXBorderLayout.CENTER);
		border.addChild(tree(),EXBorderLayout.RIGHT);
		return border;
		
	}
	
	
	public Container tree(){
		EXTreeComponent c=  new EXTreeComponent("s", "Index ", "https://raw.github.com/joshuaclayton/blueprint-css/master/blueprint/plugins/link-icons/icons/doc.png");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(c, false);
		for(int i =0;i< 10;i++){
			EXTreeComponent c1=  new EXTreeComponent("s", "Index " + i, "https://raw.github.com/joshuaclayton/blueprint-css/master/blueprint/plugins/link-icons/icons/doc.png");
			DefaultMutableTreeNode l1 = new DefaultMutableTreeNode(c1, false);
			root.addChild(l1);
			
			for(int ii = 0; ii < 10; ii++){
				EXTreeComponent c11=  new EXTreeComponent("s", "Index " + i + " " + ii, "https://raw.github.com/joshuaclayton/blueprint-css/master/blueprint/plugins/link-icons/icons/doc.png");
				DefaultMutableTreeNode l11 = new DefaultMutableTreeNode(c11, false);
				l1.addChild(l11);
			}
		}
		EXTree tree = new EXTree("tree", root);
		return tree;
	}

}
