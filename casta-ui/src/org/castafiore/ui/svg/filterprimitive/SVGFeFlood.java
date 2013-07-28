package org.castafiore.ui.svg.filterprimitive;

import java.awt.Color;

import org.castafiore.ui.svg.values.OpacityValues;


public class SVGFeFlood extends AbstractFe {


	public SVGFeFlood(String name) {
		super(name, "feFlood");
	}


	public Color getFloodColor() {
		return Color.getColor(getAttribute("flood-color"));
	}


	public void setFloodColor(Color c) {
		setAttribute("flood-color", "rgb(" +c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")" );
	}


	public OpacityValues getFloodOpacity() {
		return OpacityValues.valueOf(getAttribute("flood-opacity"));
	}


	public void setFloodOpacity(OpacityValues floodOpacity) {
		setAttribute("flood-opacity", floodOpacity.toString());
	}
	
	
	
}
