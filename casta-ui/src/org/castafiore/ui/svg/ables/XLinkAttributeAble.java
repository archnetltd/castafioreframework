package org.castafiore.ui.svg.ables;

public interface XLinkAttributeAble extends CoreAttributeAble {
//xlink:href, xlink:type, xlink:role, xlink:arcrole, xlink:title, xlink:show, xlink:actuate
	
	
	public String getXLinkHref();
	
	public String getXLinkType();
	public String getXLinkRole();
	public String getXLinkArcRole();
	public String getXLinkTitle();
	public String getXLinkShow();
	public String getXLinkActuate();
	
	public void setXLinkType(String s);
	public void setXLinkRole(String s);
	public void setXLinkArcRole(String s);
	public void setXLinkTitle(String s);
	public void setXLinkShow(String s);
	public void setXLinkActuate(String s);
	
	
	
	public void setXLinkHref(String xLinkHref);
}
