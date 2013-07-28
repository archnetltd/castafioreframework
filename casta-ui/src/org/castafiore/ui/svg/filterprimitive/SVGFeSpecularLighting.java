package org.castafiore.ui.svg.filterprimitive;


public class SVGFeSpecularLighting extends AbstractFe {
	
	
	public SVGFeSpecularLighting(String name) {
		super(name, "feSpecularLighting");
		
	}

	public String getSurfaceScale() {
		return getAttribute("surfaceScale");
	}

	public void setSurfaceScale(String surfaceScale) {
		setAttribute("surfaceScale", surfaceScale);
	}

	public String getSpecularConstant() {
		return getAttribute("specularConstant");
	}

	public void setSpecularConstant(String specularConstant) {
		setAttribute("specularConstant", specularConstant);
	}

	public String getSpecularExponent() {
		return getAttribute("specularExponent");
	}

	public void setSpecularExponent(String specularExponent) {
		setAttribute("specularExponent", specularExponent);
	}

	public String getKernelUnitLength() {
		return getAttribute("kernelUnitLength");
	}

	public void setKernelUnitLength(String kernelUnitLength) {
		setAttribute("kernelUnitLength", kernelUnitLength);
	}
	
	

}
