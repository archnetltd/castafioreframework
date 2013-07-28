package org.castafiore.ui.svg.light;

import org.castafiore.ui.svg.filterprimitive.AbstractFe;

public class SVGFePointLight extends AbstractFe{

	private int z;
	public SVGFePointLight(String name) {
		super(name, "fePointLight");
		
	}
	public int getZ() {
		return Integer.parseInt(getAttribute("z"));
	}
	public void setZ(int z) {
		setAttribute("z", z + "");
	}

	
}
