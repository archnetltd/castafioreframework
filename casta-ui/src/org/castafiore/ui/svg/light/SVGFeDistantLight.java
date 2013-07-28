package org.castafiore.ui.svg.light;

import org.castafiore.ui.svg.filterprimitive.AbstractFe;

public class SVGFeDistantLight extends AbstractFe {


	
	public SVGFeDistantLight(String name) {
		super(name, "feDistantLight");
		
	}


	public String getAzimuth() {
		return getAttribute("azimuth");
	}


	public void setAzimuth(String azimuth) {
		setAttribute("azimuth", azimuth);
	}


	public String getElevation() {
		return getAttribute("elevation");
	}


	public void setElevation(String elevation) {
		setAttribute("elevation", elevation);
	}

}
