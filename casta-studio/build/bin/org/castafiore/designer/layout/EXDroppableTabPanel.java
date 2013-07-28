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
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

public class EXDroppableTabPanel extends EXTabPanel implements DesignableLayoutContainer, TabModel, ChildrenAttributeConfigurationListener{

	public EXDroppableTabPanel(String name) {
		super(name);
		setModel(this);
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
//		for(int i = 0; i < 1; i++){
//			Container c = SpringUtil.getBeanOfType(DesignableService.class).getDesignable("core:div");
//			c.setName("Tab " + (i+1));
//			c.setAttribute("des-id", "core:div");
//			c.setStyle("width", "300px");
//			c.setStyle("height", "200px");
//			addChild(c, i + "");
//		}
		
	}
	
	public void setModel(TabModel model) {
		this.model = model;
		this.getChildren().clear();
		this.setRendered(false);
		if(model == null){
			return;
		}
		Container tabs = ComponentUtil.getContainer("tabs", "ul", null, null);
		tabs.setAttribute("class",TABS_HEADER_STYLE);
		addChild(tabs);
	}

	@Override
	public String getPossibleLayoutData(Container container) {
		return size() + "";
	}

	@Override
	public void moveDown(Container component) {
		Container content = getDescendentById(component.getId()).getParent();
		if(content.getName().equalsIgnoreCase("c-" + (size()-1))){
			
		}else{
			int index = Integer.parseInt(content.getName().replace("c-", ""));
			int newIndex = index+1;
			
			Container newComponent = getChild("c-" + newIndex).getChildByIndex(0);
			Container newContent = newComponent.getParent();
			newComponent.remove();
			newComponent.setRendered(false);
			component.remove();
			component.setRendered(false);
			newContent.addChild(component);
			content.addChild(newComponent);
			
			getChild("tabs").getChildByIndex(newIndex).getChildByIndex(0).setText(component.getAttribute("label"));
			getChild("tabs").getChildByIndex(index).getChildByIndex(0).setText(newComponent.getAttribute("label"));
			
		}
		
	}

	@Override
	public void moveUp(Container component) {
		Container content = getDescendentById(component.getId()).getParent();
		if(content.getName().equalsIgnoreCase("c-0")){
			
		}else{
			int index = Integer.parseInt(content.getName().replace("c-", ""));
			int newIndex = index-1;
			
			Container newComponent = getChild("c-" + newIndex).getChildByIndex(0);
			Container newContent = newComponent.getParent();
			newComponent.remove();
			newComponent.setRendered(false);
			component.remove();
			component.setRendered(false);
			newContent.addChild(component);
			content.addChild(newComponent);
			
			getChild("tabs").getChildByIndex(newIndex).getChildByIndex(0).setText(component.getAttribute("label"));
			getChild("tabs").getChildByIndex(index).getChildByIndex(0).setText(newComponent.getAttribute("label"));
			
		}
		
	}

	@Override
	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChild(Container child, String layoutData) {
		
		Container content = ComponentUtil.getContainer("c-" + layoutData, "div", "", null);
		content.setAttribute("init", "false");
		String label = child.getAttribute("label");
		if(!StringUtil.isNotEmpty(label))
			child.setAttribute("label", "Tab " + (Integer.parseInt(layoutData) +1));
		content.setDisplay(false);
		addChild(content);
		content.addChild(child);
		if(getTabContentDecorator() != null)
			getTabContentDecorator().decorateContent(content);
		
		
		Container tab = getTabRenderer().getComponentAt(this, this, Integer.parseInt(layoutData));
		Container tabs = getChild("tabs");
		
		tabs.addChild(tab);
		tab.setAttribute("t", layoutData + "");
		tab.setAttribute("init", "false");
		tab.addEvent(EVT_SHOW_TAB, Event.CLICK);
		
		if(layoutData.equalsIgnoreCase("0")){
			getTabRenderer().onSelect(this, this, Integer.parseInt(layoutData), tab);
			content.setDisplay(true);
			
		}
		content.setAttribute("init", "true");
		tab.setAttribute("init", "true");
	}

	@Override
	public Container getChild(String name, String layoutData) {
		Container content = getChild("c-" + layoutData);
		return content.getDescendentByName(name);
	}

	@Override
	public List<Container> getChildren(String layoutData) {
		Container content = getChild("c-" + layoutData);
		return content.getChildren();
	}

	@Override
	public Container getContainer(String layoutData) {
		Container content = getChild("c-" + layoutData);
		return content;
	}

	@Override
	public Container getDescendentById(String id, String layoutData) {
		Container content = getChild("c-" + layoutData);
		return content.getDescendentById(id);
	}

	@Override
	public Container getDescendentByName(String name, String layoutData) {
		Container content = getChild("c-" + layoutData);
		return content.getDescendentByName(name);
	}

	@Override
	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		Container content = getChild("c-" + layoutData);
		return content.getDescendentOfType(type);
	}

	@Override
	public List<DroppableSection> getSections() {
		int count = 1;
		List<DroppableSection> result = new ArrayList<DroppableSection>();
		for(Container c : getChildren()){
			if(!c.getName().equalsIgnoreCase("tabs")){
				DroppableSection section = new DroppableSection(c.getChildByIndex(0).getId(), "Tab " + count, (count-1) + "");
				result.add(section);
			}
		}
		
		return result;
	}

	@Override
	public void removeChildFromLayout(String id) {
		Container c = getDescendentById(id);
		String layoutName = c.getParent().getName();
		int index = Integer.parseInt(layoutName.replace("c-", "").trim());
		
		c.getParent().remove();
		getChild("tabs").getChildByIndex(index).remove();
		setRendered(false);
		
	}

	@Override
	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		try{
			Integer.parseInt(layoutData);
		}catch(Exception e){
			throw new InvalidLayoutDataException(e);
		}
		
	}

	public String[] getAcceptClasses() {
		return new String[]{"components"};
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
		return getChild("c-" + index).getChildByIndex(0);
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return getTabContentAt(pane, index).getAttribute("label");
	}

	@Override
	public int size() {
		return getChildren().size()-1;
	}

	@Override
	public void applyAttribute(Container child, String name,String label) {
		if(name.equalsIgnoreCase("label")){
			int index = Integer.parseInt(child.getParent().getName().replace("c-", ""));
			getChild("tabs").getChildByIndex(index).getChildByIndex(0).setText(label);
			child.setAttribute("label",label);
		}
		
	}

}
