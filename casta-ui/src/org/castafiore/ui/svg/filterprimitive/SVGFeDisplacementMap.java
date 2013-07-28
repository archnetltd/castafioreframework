package org.castafiore.ui.svg.filterprimitive;


public class SVGFeDisplacementMap extends AbstractFe {
	
	public SVGFeDisplacementMap(String name) {
		super(name, "feDisplacementMap");
		
	}

	public String getIn2() {
		return getAttribute("in2");
	}

	public void setIn2(String in2) {
		setAttribute("in2", in2);
	}

	public String getScale() {
		return getAttribute("scale");
	}

	public void setScale(String scale) {
		setAttribute("scale", scale);
	}

	public String getxChannelSelector() {
		return getAttribute("xChannelSelector");
	}

	public void setxChannelSelector(String xChannelSelector) {
		setAttribute("xChannelSelector", xChannelSelector);
	}

	public String getyChannelSelector() {
		return getAttribute("yChannelSelector");
	}

	public void setyChannelSelector(String yChannelSelector) {
		setAttribute("yChannelSelector", yChannelSelector);
	}

	
}
