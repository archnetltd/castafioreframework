package org.castafiore.easyui.layout;

import org.castafiore.ui.Container;

public interface AccordionModel {

	public int size();
	
	public Container getTabContentAt(Accordion acc, int index);
	
	public String getTabLabelAt(Accordion acc, int index);
	
	public String getIconCls(Accordion acc, int index);
	
	public int getSelectedTab();
	
	public String getIconCls(int index);
	
}
