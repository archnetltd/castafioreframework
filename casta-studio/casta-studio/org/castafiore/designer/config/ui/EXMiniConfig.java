package org.castafiore.designer.config.ui;

import java.util.Map;

import javax.naming.spi.StateFactory;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.BaseSpringUtil;

public class EXMiniConfig extends EXContainer implements Event{
	Container c = null;

	public EXMiniConfig(String name) {
		super(name, "table");
		setStyle("position", "relative").setStyle("left", "-20px");
		
	}
	
	
	private void addRow(String label, Container input){
		Container tr= new EXContainer("", "tr");
		Container tLabel = new EXContainer("", "td").setStyle("text-align", "right").addClass("np");
		tLabel.setText("<label>" + label + "</label>");
		tr.addChild(tLabel);
		tr.addChild(new EXContainer("", "td").addClass("np").addChild(input.setStyle("margin", "0").setStyle("padding", "0")));
		addChild(tr);
	}
	
	
	public void setContainer(Container container){
		c = container;
		String uniqueId = c.getAttribute("des-id");
		DesignableFactory fact = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(uniqueId);
		String[] attrs= fact.getRequiredAttributes();
		this.getChildren().clear();
		setRendered(false);
		addRow("name :", new EXInput("name", c.getName()).addEvent(this, BLUR));
		if(attrs != null){
			for(String s : attrs){
				addRow(s, new EXInput(s, c.getAttribute(s)).addEvent(this, BLUR));
			}
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String value = ((StatefullComponent)container).getValue().toString();
		if(container.getName().equals("name")){
			c.setName(value);
			getAncestorOfType(EXDesigner.class).getDescendentOfType(EXConfigVerticalBar.class).getNode(c).refresh();
		}else{
			String uniqueId = c.getAttribute("des-id");
			DesignableFactory fact = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(uniqueId);
			fact.applyAttribute(c, container.getName(), value);
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
