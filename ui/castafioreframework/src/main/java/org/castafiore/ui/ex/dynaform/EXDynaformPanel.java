/*
 * 
 */
package org.castafiore.ui.ex.dynaform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.button.Button;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;

public class EXDynaformPanel extends EXPanel implements DynaForm{
	
	public EXDynaformPanel(String name, String title) {
		super(name, title);
		Container fieldSet = ComponentUtil.getContainer("fieldSet", "table", null, "dynaform");
		fieldSet.setDisplay(false);
		setBody(fieldSet);
	}
	
	public EXDynaformPanel(String name, String title, FormModel model) {
		this(name,title);
		
		 int actSize = model.actionSize();
		 
		 for(int i = 0; i < actSize; i ++){
			 addButton(model.getActionAt(i, this));
		 }
		 
		 int fieldSize = model.size();
		 for(int i = 0; i < fieldSize; i ++){
			 addField(model.getLabelAt(i, this), model.getFieldAt(i, this));
		 }
	}

	public DynaForm addButton(Button button) {
		setShowFooter(true);
		getFooterContainer().addChild(button);
		return this;
	}

	public DynaForm addField(String label, StatefullComponent input) {
		
		if(input instanceof EXFieldSet){
			
			Container tr = ComponentUtil.getContainer("tr", "tr", null, "dynaformRow");
			
			getBody().addChild(tr);	

			Container td = ComponentUtil.getContainer("td", "td", null, "dynaformInput");
			td.setAttribute("colspan", "2");
			td.addChild(input);
			tr.addChild(td);
		}else{
			Container uiLabel = ComponentUtil.getContainer("label_" + input.getId(), "td", label, "dynaformLabel");
			Container tr = ComponentUtil.getContainer("tr", "tr", null, "dynaformRow");
			tr.addChild(uiLabel);
			getBody().addChild(tr);	

			Container td = ComponentUtil.getContainer("td", "td", null, "dynaformInput");
			td.addChild(input);
			tr.addChild(td);
		}
		return this;
	}
	
	public DynaForm addOtherItem(Container input) {
		

			Container tr = ComponentUtil.getContainer("tr", "tr", null, "dynaformRow");
			
			getBody().addChild(tr);	

			Container td = ComponentUtil.getContainer("td", "td", null, "dynaformInput");
			td.setAttribute("colspan", "2");
			td.addChild(input);
			tr.addChild(td);
	
		return this;
	}
	
	public DynaForm hideField(String name){
		getField(name).getParent().getParent().setDisplay(false);
		return this;
	}
	
	public DynaForm showField(String name){
		getField(name).getParent().getParent().setDisplay(true);
		return this;
	}
	
	public void setLabelFor(String label, StatefullComponent input){
		Container uiLabel = getDescendentByName("label_" + input.getId());
		if(uiLabel != null){
			uiLabel.setText(label);
		}
		
	}
	
	public Map<String, StatefullComponent> getFieldsMap() {
		Map<String, StatefullComponent> result = new HashMap<String, StatefullComponent>();
		List<Container> children = getBody().getChildren();
		for(Container c : children){
			StatefullComponent stf = c.getDescendentOfType(StatefullComponent.class);
			if(stf != null){
				result.put(stf.getName(), stf);
			}
		}
		return result;
	}

	public StatefullComponent getField(String name) {
		return getFieldsMap().get(name);
	}	
	
	public List<StatefullComponent> getFields(){
		List<StatefullComponent> result = new ArrayList<StatefullComponent>();
		List<Container> children = getBody().getChildren();
		
		for(Container c : children){
			StatefullComponent stf = c.getDescendentOfType(StatefullComponent.class);
			if(stf != null){
				result.add( stf);
			}
		}
		
		return result;
	}
	
	
	public Map<String, String> getFieldValues(){
		Map<String, String> result = new HashMap<String, String>();
		List<Container> children = getBody().getChildren();
		for(Container c : children){
			StatefullComponent stf = c.getDescendentOfType(StatefullComponent.class);
			if(stf != null){
				result.put(stf.getName(), stf.getValue().toString());
			}
		}
		return result;
	}
	
	public List<Button> getButtons(){
		Container footer = getFooterContainer();
		List result = new ArrayList();
		ComponentUtil.getDescendentsOfType(footer, result, Button.class);
		
		return result;
	}

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		if(proxy.getDescendentByName("fieldSet") != null)
			proxy.getDescendentByName("fieldSet").fadeIn(100);
	}
	
	
}
