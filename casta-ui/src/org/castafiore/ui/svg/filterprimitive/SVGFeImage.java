package org.castafiore.ui.svg.filterprimitive;


public class SVGFeImage extends AbstractFe {
	public SVGFeImage(String name) {
		super(name, "feImage");
	
	}
	
	public boolean isPreserveAspectRatio(){
		return Boolean.parseBoolean(getAttribute("preserveAspectRatio"));
	}
	
	public void setPreserveAspectRation(Boolean b){
		setAttribute("preserveAspectRatio", b.toString());
	}

}
