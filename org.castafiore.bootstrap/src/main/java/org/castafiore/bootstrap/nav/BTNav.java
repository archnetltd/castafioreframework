package org.castafiore.bootstrap.nav;

import java.util.Map;

import org.castafiore.bootstrap.AlignmentType;
import org.castafiore.bootstrap.dropdown.BTDropDown;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;
import org.castafiore.ui.ex.tab.TabRenderer;

public class BTNav extends EXContainer implements TabPanel, TabRenderer, Event {

	private TabModel model = null;

	private TabRenderer renderer = null;

	private BTNavContent linked;

	public BTNav(String name, BTNavContent linked) {
		super(name, "ul");
		addClass("nav");
		setNavType(NavType.TABS);
		this.linked = linked;
	}

	public void setModel(TabModel model) {
		this.model = model;
		refresh();
	}

	public void refresh() {
		super.refresh();
		if (model == null || linked == null)
			return;
		
		int selected = model.getSelectedTab();
		int size = model.size();
		TabRenderer r = getTabRenderer();
		this.getChildren().clear();
		setRendered(false);
		for (int i = 0; i < size; i++) {
			Container t = r.getComponentAt(this, model, i);
			addChild(t);
			if (selected == i) {
				r.onSelect(this, model, i, t);
			}
		}
		
		if(linked != null){
			linked.showTab(this, model, selected);
		}

	}
	
	public BTNavContent getLinkedNavContent(){
		return linked;
	}

	public BTNav setNavType(NavType type) {
		if (type == NavType.PILLS) {
			removeClass("nav-tabs").removeClass("nav-stacked").addClass(
					"nav-pills");
		} else if (type == NavType.STACKED) {
			removeClass("nav-tabs").addClass("nav-pills").addClass(
					"nav-stacked");
		} else if (type == NavType.TABS) {
			addClass("nav-tabs").removeClass("nav-pills").removeClass(
					"nav-stacked");
		}else if(type == NavType.BREADCRUMB){
			removeClass("nav-tabs").removeClass("nav-stacked").removeClass("nav-pills").addClass("breadcrumbs");
		}
		return this;
	}

	public BTNav setJustified(boolean justified) {
		if (justified)
			addClass("nav-justified");
		else
			removeClass("nav-justified");
		return this;

	}

	@Override
	public TabModel getModel() {
		return model;
	}

	@Override
	public TabRenderer getTabRenderer() {
		if (renderer == null) {
			renderer = this;
		}
		return renderer;
	}

	@Override
	public Container getComponentAt(TabPanel pane, TabModel model, int index) {
		Container content = new EXContainer(index + "", "a").setAttribute(
				"href", "#t" + index).setText(
				model.getTabLabelAt(pane, index, false));
		content.addEvent(this, CLICK);
		Container tab = new EXContainer("tab-" + index, "li");
		tab.addChild(content);
		return tab;
	}

	public BTNav setEnabled(int index, boolean enabled) {
		if (enabled)
			getChildByIndex(index).addClass("disabled");
		else
			getChildByIndex(index).removeClass("disabled");

		return this;
	}

	public BTNav setDropDown(int index, BTDropDown bt) {
		Container c = getChildByIndex(index);
		c.addClass("dropdown");

		c.getChildByIndex(0).addClass("dropdown-toggle")
				.setAttribute("data-toggle", "dropdown");
		c.getChildByIndex(0).addChild(new EXContainer("c", "span").addClass("caret"));
		c.getChildByIndex(0).getEvents().clear();
		c.getChildByIndex(0).setRendered(false);
		c.addChild(bt.getChildByIndex(0));
		//<span class="caret"></span>
		
		return this;
	}

	@Override
	public void onSelect(TabPanel pane, TabModel model, int index, Container tab) {
		tab.addClass("active");

	}

	@Override
	public void onDeselect(TabPanel pane, TabModel model, int index,
			Container tab) {
		tab.removeClass("active");
	}

	public BTNav setAlignment(AlignmentType alignment) {

		if (alignment == AlignmentType.LEFT) {
			removeClass("pull-right");
		} else if (alignment == AlignmentType.RIGHT) {
			addClass("pull-right");
		}
		return this;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		int index = Integer.parseInt(container.getName());

		linked.showTab(this, model, index);

		for(int i= 0; i < model.size();i++){
			Container tab = getChildByIndex(i);
			if(i== index){
				renderer.onSelect(this, model, index, tab);
			}else{
				renderer.onDeselect(this, model, index, tab);
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {

	}

}
