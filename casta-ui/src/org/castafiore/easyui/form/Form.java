package org.castafiore.easyui.form;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.EXGrid.EXRow;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.mvc.CastafioreController;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

public class Form extends EXContainer implements CastafioreController, DynaForm{

	private JMap options = new JMap();
	public Form(String name, String tagName) {
		super(name, "form");
		EXGrid grid = new EXGrid("grid", 2, 0);
		addChild(grid);
		
	}
	
	
	public void onReady(ClientProxy proxy){
		options.put("url", ResourceUtil.getMethodUrl(this));
	}
	
	
	

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, StatefullComponent> getFieldsMap() {
		final Map<String, StatefullComponent> map = new LinkedHashMap<String, StatefullComponent>();
		ComponentUtil.iterateOverDescendentsOfType(this, StatefullComponent.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				map.put(c.getName(), (StatefullComponent)c);
				
			}
		});
		
		return map;
	}


	@Override
	public StatefullComponent getField(String name) {
		return (StatefullComponent)getDescendentByName(name);
	}


	@Override
	public DynaForm addField(String label, StatefullComponent input) {
		EXRow row = getDescendentOfType(EXGrid.class).addRow();
		row.addInCell(0, new EXContainer("lbl" + input.getName(), "label").setText(label).setAttribute("for", input.getName()));
		row.addInCell(1, input);
		return this;
		
	}


	@Override
	public DynaForm addButton(Button button) {
		if(getChildren().size() ==1){
			EXGrid buttons = new EXGrid("buttons", 1, 1);
			buttons.getCell(0, 0).addChild(button);
		}else{
			((EXGrid)getChildByIndex(1)).getCell(0, 0).addChild(button);
		}
		
		return this;
	}


	@Override
	public List<StatefullComponent> getFields() {
		List<StatefullComponent> stfs = new LinkedList<StatefullComponent>();
		
		ComponentUtil.getAllStatefullDescendents(this, stfs);
		return stfs;
	}


	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		getDescendentByName("lbl" + c.getName()).setText(label);
		
	}

}
