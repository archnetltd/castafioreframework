package org.castafiore.finance.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.button.Button;

public class EXOSForm extends EXContainer implements DynaForm{

	public EXOSForm(String name, String title) {
		super(name, "div");
		addClass("form");
		Container fs = new EXContainer("fs", "fieldset");
		addChild(fs);
		fs.addChild(new EXContainer("lg", "legend").setText(title));
		fs.addChild(new EXContainer("fields", "div"));
		setStyle("padding", "6px");
		
	}

	@Override
	public DynaForm addButton(Button button) {
		Container fs = getChild("fs");
		if(fs.getChild("btns") == null){
			fs.addChild(new EXContainer("btns", "p"));
		}
		fs.getChild("btns").addChild(button);
		return this;
	}

	@Override
	public DynaForm addField(String label, StatefullComponent input) {
		Container fs = getChild("fs");
		Container p = new EXContainer("", "p");
		p.addChild(new EXContainer("", "label").setText(label).setAttribute("for", input.getName()));
		p.addChild(new EXContainer("", "br"));
		p.addChild(input.addClass("text"));
		fs.getChild("fields").addChild(p);
		return this;
	}

	@Override
	public StatefullComponent getField(String name) {
		return (StatefullComponent)getDescendentByName(name);
	}

	@Override
	public List<StatefullComponent> getFields() {
		Container fs = getChild("fs");
	 	List<Container> ps=  fs.getChild("fields").getChildren();
	 	List<StatefullComponent> result = new ArrayList<StatefullComponent>();
	 	for(Container p : ps){
	 		result.add(p.getDescendentOfType(StatefullComponent.class));
	 	}
	 	return result;
	}

	@Override
	public Map<String, StatefullComponent> getFieldsMap() {
		Container fs = getChild("fs");
		List<Container> ps=  fs.getChild("fields").getChildren();
	 	Map<String,StatefullComponent> result = new HashMap<String, StatefullComponent>();
	 	for(Container p : ps){
	 		result.put(p.getDescendentOfType(StatefullComponent.class).getName(), p.getDescendentOfType(StatefullComponent.class));
	 	}
	 	return result;
	}

	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.dynaform.DynaForm#setLabelFor(java.lang.String, org.castafiore.ui.StatefullComponent)
	 */
	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		// TODO Auto-generated method stub
		
	}

}
