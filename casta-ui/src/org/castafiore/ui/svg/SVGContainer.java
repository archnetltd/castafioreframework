package org.castafiore.ui.svg;

import org.castafiore.ui.ex.EXContainer;

public class SVGContainer extends EXContainer implements SVGComponent{
	

	
	public SVGContainer(String name, String tagName) {
		super( name, tagName);
	}

	@Override
	public String getXMLBase() {
		return getAttribute("xml:base");
	}

	@Override
	public String getXMLLang() {
		return getAttribute("xml:lang");
	}

	@Override
	public String getXMLSpace() {
		return getAttribute("xml:space");
	}

	@Override
	public void setXMLBase(String s) {
		setAttribute("xml:base", s);
		
	}

	@Override
	public void setXMLLang(String s) {
		setAttribute("xml:lang", s);
		
	}

	@Override
	public void setXMLSpace(String s) {
		setAttribute("xml:space", s);
		
	}

	
	
	

	
	
	
	

}
