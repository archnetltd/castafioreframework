package org.castafiore.bootstrap.nav;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.tab.TabModel;
import org.castafiore.ui.tab.TabPanel;
import org.castafiore.ui.tab.TabRenderer;

public class BTAccordion extends EXContainer implements TabPanel{
	
	private TabModel model ;

	public BTAccordion(String name, TabModel model) {
		super(name, "div");
		addClass("accordion");
		setModel(model);
	}
	
	public BTAccordion(String name) {
		this(name,null);
	}
	
	public void setModel(TabModel model){
		this.model = model;
		refresh();
	}
	
	public void refresh(){
		this.getChildren().clear();
		setRendered(false);
		if(model == null){
			return;
		}
		int size = model.size();
		int selected = model.getSelectedTab();
		for(int i = 0; i < size;i++){
			BTAccordionGroup g = new BTAccordionGroup("b", this, i, model, selected);
			addChild(g);
		}
	}
	
	
	public static class BTAccordionGroup extends EXContainer{

		public BTAccordionGroup(String name,TabPanel parent, int index, TabModel model, int selected) {
			super(name, "div");
			
			addClass("accordion-group");
			Container heading = new EXContainer("h", "div").addClass("accordion-heading");
			addChild(heading);
			Container toggle = new EXContainer("t", "a").setAttribute("data-toggle", "collapse")
					.setAttribute("data-parent", "#" + parent.getId()).setAttribute("href", "#collapse" + index)
					.addClass("accordion-toggle");
			heading.addChild(toggle);
			toggle.setText(model.getTabLabelAt(parent, index, selected == index));
			
			Container body = new EXContainer("b", "div").addClass("accordion-body").addClass("collapse");
			if(index == selected){
				body.addClass("in");
			}
			addChild(body);
			toggle.setAttribute("href", "#" + body.getId());
			
			Container inner = new EXContainer("i", "div");
			inner.addClass("accordion-inner");
			body.addChild(inner);
			
			Container content = model.getTabContentAt(parent, index);
			inner.addChild(content);
			
			
			
			
		}
		
	}


	@Override
	public TabModel getModel() {
		return model;
	}

	@Override
	public TabRenderer getTabRenderer() {
		return null;
	}

}
