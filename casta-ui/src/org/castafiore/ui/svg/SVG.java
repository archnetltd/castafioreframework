package org.castafiore.ui.svg;


public class SVG extends SVGContainer implements SVGComponent{

	public SVG(String name, String width, String height) {
		super(name, "svg");
		setAttribute("width", width);
		setAttribute("height", height);
	}

}
