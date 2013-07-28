package org.castafiore.ui.svg.ables;

import org.castafiore.ui.Container;

public interface CoreAttributeAble extends Container{
	
	public String getId();
	
	public String getXMLBase();
	public String getXMLLang();
	public String getXMLSpace();
	
	public void setXMLBase(String s);
	public void setXMLLang(String s);
	public void setXMLSpace(String s);
	
	
	
	
}
