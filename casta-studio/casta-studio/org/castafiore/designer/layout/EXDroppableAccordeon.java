package org.castafiore.designer.layout;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.layout.DroppableSection;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.navigation.EXAccordeon;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.ComponentUtil;

public class EXDroppableAccordeon extends EXAccordeon implements
		DesignableLayoutContainer, TabModel,
		ChildrenAttributeConfigurationListener {

	public EXDroppableAccordeon(String name) {
		super(name);
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
		
		for(int i = 0; i < 1; i++){
			Container c = SpringUtil.getBeanOfType(DesignableService.class).getDesignable("core:div");
			c.setName("Tab " + (i+1));
			c.setAttribute("des-id", "core:div");
			c.setStyle("width", "300px");
			c.setStyle("height", "200px");
			addChild(c, i + "");
		}
	}

	@Override
	public String getPossibleLayoutData(Container container) {
		return size() + "";
	}

	@Override
	public void moveDown(Container component) {
		EXACCItem item = component.getAncestorOfType(EXACCItem.class);
		ComponentUtil.moveDown(item, this);
	}

	@Override
	public void moveUp(Container component) {
		EXACCItem item = component.getAncestorOfType(EXACCItem.class);
		ComponentUtil.moveUp(item, this);
	}

	@Override
	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addChild(Container child, String layoutData) {
		int i = Integer.parseInt(layoutData);
		
		if(layoutData.equalsIgnoreCase("0")){
			EXACCItem item = new EXACCItem("", "Tab " + (i +1), child, true, i);
			addChild(item);
		}else{
			EXACCItem item = new EXACCItem("", "Tab " + (i +1), child, false, i);
			addChild(item);
		}
	}

	@Override
	public Container getChild(String name, String layoutData) {
		return getContainer(layoutData).getChild(name);
	}

	@Override
	public List<Container> getChildren(String layoutData) {
		return getContainer(layoutData).getChildren();
	}

	@Override
	public Container getContainer(String layoutData) {
		return getChildByIndex(Integer.parseInt(layoutData)).getDescendentByName("content");
	}

	@Override
	public Container getDescendentById(String id, String layoutData) {
		return getContainer(layoutData).getDescendentById(id);
	}

	@Override
	public Container getDescendentByName(String name, String layoutData) {
		return getContainer(layoutData).getDescendentByName(name);
	}

	@Override
	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getContainer(layoutData).getDescendentOfType(type);
	}

	@Override
	public List<DroppableSection> getSections() {
		List<DroppableSection> sections = new ArrayList<DroppableSection>();
		int i =0;
		for(Container c : getChildren()){
			Container section = c.getDescendentByName("content").getChildByIndex(0);
			DroppableSection sect = new DroppableSection(section.getId(), section.getAttribute("label"), i+"");
			sections.add(sect);
			i++;
		}
		return sections;
	}

	@Override
	public void removeChildFromLayout(String id) {
		Container c= getDescendentById(id);
		c.getParent().remove();
	}

	@Override
	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		try {
			Integer.parseInt(layoutData);
		} catch (Exception e) {
			throw new InvalidLayoutDataException(e);
		}
	}

	public String[] getAcceptClasses() {
		return new String[] { "components" };
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		return getContainer(index + "").getChildByIndex(0);
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return getTabContentAt(pane, index).getAttribute("label");
	}

	@Override
	public int size() {
		return getChildren().size();
	}

	@Override
	public void applyAttribute(Container child, String name, String value) {
		if(name.equalsIgnoreCase("label")){
			child.getParent().getChild("header").getChild("text").setText(value);
		}
	}

}
