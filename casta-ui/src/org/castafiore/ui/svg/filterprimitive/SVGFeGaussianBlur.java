package org.castafiore.ui.svg.filterprimitive;


public class SVGFeGaussianBlur extends AbstractFe  {
	public SVGFeGaussianBlur(String name) {
		super(name, "feGaussianBlur");
	}
	
	public String getStdDeviation() {
		return getAttribute("stdDeviation");
	}
	public void setStdDeviation(String stdDeviation) {
		setAttribute("stdDeviation", stdDeviation);
	}

}
