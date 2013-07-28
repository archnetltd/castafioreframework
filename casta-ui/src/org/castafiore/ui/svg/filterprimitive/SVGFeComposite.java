package org.castafiore.ui.svg.filterprimitive;

import org.castafiore.ui.svg.ables.FilterPrimitiveAttributeAble;
import org.castafiore.ui.svg.ables.PresentationAttributeAble;

public class SVGFeComposite extends AbstractFe implements PresentationAttributeAble,FilterPrimitiveAttributeAble {

	public SVGFeComposite(String name) {
		super(name, "feComposite");
		
	}
	
	public String getIn2() {
		return getAttribute("in2");
	}

	
	public String setIn2() {
		return getAttribute("in2");
	}
	
	public String getOperator() {
		return getAttribute("operator");
	}

	
	public String setOperator() {
		return getAttribute("operator");
	}
	
	public String getK1() {
		return getAttribute("k1");
	}

	
	public String setK1() {
		return getAttribute("k1");
	}
	
	public String getK2() {
		return getAttribute("k2");
	}

	
	public String setK2() {
		return getAttribute("k2");
	}
	
	public String getK3() {
		return getAttribute("k3");
	}

	
	public String setK3() {
		return getAttribute("k3");
	}
	
	public String getK4() {
		return getAttribute("k4");
	}

	
	public String setK4() {
		return getAttribute("k4");
	}

	
}
