package org.castafiore.shoppingmall.crm;

import org.castafiore.shoppingmall.crm.newsletter.EXNewsletterList;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXCRM extends EXPanel implements TabModel{

	
	public EXCRM(String name) {
		super(name, "Customer Relationship Management System");
		
		EXTabPanel tabs = new EXTabPanel("tabs");
		tabs.setModel(this);
		
		setBody(tabs);
		setStyle("width", "900px");
		
		setCloseButtonEvent(HIDE_EVENT);
		
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		if(index == 0){
			return "Customers";
		}else
			return "Newsletters";
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			return new EXSubscribersList("subscribers");
		}else
			return new EXNewsletterList("newsletters");
	}

	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public int size() {
		
		return 2;
	}
	
	

}
