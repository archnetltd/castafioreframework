package org.castafiore.ui.svg.filterprimitive;



public class SVGFeMorphology extends AbstractFe {
	public SVGFeMorphology(String name) {
		super(name, "feMorphology");
	}

	public String getOperator() {
		return getAttribute("operator");
	}

	
	public String setOperator() {
		return getAttribute("operator");
	}
	
	public void setRadius(int radius){
		setAttribute("radius", radius + "");
		
	}
	
	public int getRadius(){
		return Integer.parseInt(getAttribute("radius"));
	}
	
}
