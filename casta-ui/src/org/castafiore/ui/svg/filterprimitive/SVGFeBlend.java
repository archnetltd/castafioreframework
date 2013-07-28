package org.castafiore.ui.svg.filterprimitive;


public class SVGFeBlend extends AbstractFe {

	public SVGFeBlend(String name) {
		super(name, "feBlend");
		
	}
	public String getIn2() {
		return getAttribute("in2");
	}
	public void setIn2(String in2) {
		setAttribute("in2", in2);
	}
	public String getMode() {
		return getAttribute("mode");
	}
	public void setMode(String mode) {
		setAttribute("mode", mode);
	}

	
}
