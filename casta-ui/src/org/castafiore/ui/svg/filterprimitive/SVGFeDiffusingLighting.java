package org.castafiore.ui.svg.filterprimitive;


public class SVGFeDiffusingLighting extends AbstractFe {
	
	public SVGFeDiffusingLighting(String name, String tagName) {
		super(name, "feDiffuseLighting");
		
	}
	public String getSurfaceScale() {
		return getAttribute("surfaceScale");
	}
	public void setSurfaceScale(String surfaceScale) {
		setAttribute("surfaceScale", surfaceScale);
	}
	public String getDiffuseConstant() {
		return getAttribute("diffuseConstant");
	}
	public void setDiffuseConstant(String diffuseConstant) {
		setAttribute("diffuseConstant", diffuseConstant);
	}
	public String getKernelUnitLength() {
		return getAttribute("kernelUnitLength");
	}
	public void setKernelUnitLength(String kernelUnitLength) {
		setAttribute("kernelUnitLength", kernelUnitLength);
	}

	
	
}
