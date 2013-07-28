package org.castafiore.ui.svg.animation;


public class SVGAnimateTransform extends SVGAnimate {

	public SVGAnimateTransform(String name, String attributeType) {
		super(name, "animateTransform", attributeType);
	}

	public String getType() {
		return getAttribute("type");
	}

	public void setType(String type) {
		setAttribute("type", type);
	}

}
