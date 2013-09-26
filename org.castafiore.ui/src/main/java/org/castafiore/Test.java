package org.castafiore;

import java.awt.Color;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXApplication;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.events.ServerEvent;
import org.castafiore.ui.ex.dynaform.EXDynaForm;
import org.castafiore.ui.ex.form.EXButton;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.list.DefaultDataModel;
import org.castafiore.ui.ex.list.EXRadio;
import org.castafiore.ui.ex.list.EXSelect;
import org.castafiore.ui.ex.tab.EXTabPanel;
import org.castafiore.ui.tab.TabModel;
import org.castafiore.ui.tab.TabPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class Test extends EXApplication{

	public Test() {
		super("test");
	}

	@Override
	public void initApp() {
		setStyle("font-size", "67%");
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
		 
		 
		 
		 DefaultDataModel<KeyValuePair> model = new DefaultDataModel<KeyValuePair>();
		 model.addItem(new SimpleKeyValuePair("a", "A"), new SimpleKeyValuePair("b", "B"), new SimpleKeyValuePair("c", "C"));
		 EXSelect<KeyValuePair> select = new EXSelect<KeyValuePair>("select");
		 select.setModel(model);
		 
		 form.addField("Select",select);
		 
		 form.addField("Radio", new EXRadio<KeyValuePair>("radio").setModel(model));
		 
		form.addButton((Button)new EXButton("btn", "Submit").addEvent(new ServerEvent() {
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				ComponentUtil.iterateOverDescendentsOfType(container.getRoot(), FormComponent.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container root) {
						System.out.println(((FormComponent)root).getValue());
						
					}
				});
				return true;
			}
		}, Event.CLICK));
		
		
		
		EXTabPanel tabs = new EXTabPanel("tab", new TabModel() {
			
			@Override
			public int size() {
				return 2;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				if(index == 0){
					return "Form";
				}else{
					return "Custom";
				}
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				if(index == 0)
					return form;
				else
					return new EXContainer("d", "h1").setText("Hello world");
			}
			
			@Override
			public int getSelectedTab() {
				return 0;
			}
		});
		
		addChild(tabs);
	}

}
