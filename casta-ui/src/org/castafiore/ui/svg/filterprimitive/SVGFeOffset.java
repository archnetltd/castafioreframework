package org.castafiore.ui.svg.filterprimitive;

import java.awt.Point;

public class SVGFeOffset extends AbstractFe{
	
	public SVGFeOffset(String name) {
		super(name, "feOffset");
	}

	

	public Point getDXY() {
		return new Point(Integer.parseInt(getAttribute("dx")), Integer.parseInt(getAttribute("dy")) );
	}

	


	public void setDXY(Point xy) {
		setAttribute("dx", xy.getX() + "");
		setAttribute("dy", xy.getY() + "");
		
	}
}
