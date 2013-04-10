package org.castafiore.easyui.layout;

public interface TabModel extends org.castafiore.ui.tabbedpane.TabModel{
	
	public boolean isClosable(int index);
	
	public String getIconCls(int index);

}
