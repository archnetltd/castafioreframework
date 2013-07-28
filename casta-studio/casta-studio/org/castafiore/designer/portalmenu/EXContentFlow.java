package org.castafiore.designer.portalmenu;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.DroppableSection;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;

public class EXContentFlow extends EXContainer implements DesignableLayoutContainer{

	public EXContentFlow(String name) {
		super(name, "div");
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
		addClass("ContentFlow");
		addScript("contentflow/contentflow.js");
		//addChild(new EXContainer("", "script").setAttribute("type", "text/javascript").setAttribute("src", "contentflow/contentflow.js"));
		addChild(new EXContainer("", "div").addClass("loadIndicator").setText("<div class=\"indicator\"></div>"));
		
		addChild(new EXContainer("flow", "div").addClass("flow"));
		addChild(new EXContainer("", "div").addClass("globalCaption"));
		addChild(new EXContainer("", "div").addClass("scrollbar").setText("<div class=\"slider\"><div class=\"position\"></div></div>") );
		
		
	}

	@Override
	public void onReady(ClientProxy proxy) {
		// TODO Auto-generated method stub
		super.onReady(proxy);
		proxy.appendJSFragment("new ContentFlow('contentFlow', {reflectionColor: \"#000000\"});");
	}

	@Override
	public String getPossibleLayoutData(Container container) {
		return container.getName();
	}

	@Override
	public void moveDown(Container component) {
		ComponentUtil.moveUp(component.getParent(), getChild("flow"));
		
	}

	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveDown(component.getParent(), getChild("flow"));
		
	}

	@Override
	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChild(Container child, String layoutData) {
		
		getChild("flow").addChild(new EXContainer("", "div").addClass("item").addChild(child));
		
		
	}

	@Override
	public Container getChild(String name, String layoutData) {
		return getChild("flow").getDescendentByName(name);
	}

	@Override
	public List<Container> getChildren(String layoutData) {
		List<Container> result = new ArrayList<Container>();
		for(Container c : getChild("flow").getChildren()){
			if(c.getChildren().size() > 0){
				result.add(c.getChildByIndex(0));
			}
		}
		return result;
	}

	@Override
	public Container getContainer(String layoutData) {
		return getChild("flow");
	}

	@Override
	public Container getDescendentById(String id, String layoutData) {
		return getChild("flow").getDescendentById(id);
	}

	@Override
	public Container getDescendentByName(String name, String layoutData) {
		return getChild("flow").getDescendentByName(name);
	}

	@Override
	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getChild("flow").getDescendentOfType(type);
	}

	@Override
	public List<DroppableSection> getSections() {
		List<Container> children = getChild("flow").getChildren();
		
		List<DroppableSection> sections = new ArrayList<DroppableSection>(children.size());
		
		for(Container cc : children){
			Container c = cc.getChildByIndex(0);
			DroppableSection ds = new DroppableSection(c.getId(),c.getName(),c.getName());
			sections.add(ds);
		}
		return sections;
	}

	@Override
	public void removeChildFromLayout(String id) {
		getChild("flow").getDescendentById(id).remove();
		
	}

	@Override
	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		// TODO Auto-generated method stub
		
	}

	public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}
	
	

}
