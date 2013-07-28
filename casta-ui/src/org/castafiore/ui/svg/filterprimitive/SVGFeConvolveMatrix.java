package org.castafiore.ui.svg.filterprimitive;

import java.awt.Point;


public class SVGFeConvolveMatrix extends AbstractFe {

	
	public SVGFeConvolveMatrix(String name) {
		super(name, "feConvolveMatrix");

	}

	public String getOrder() {
		return getAttribute("order");
	}

	public void setOrder(String order) {
		setAttribute("order", order);
	}

	public String getKernelMatrix() {
		return getAttribute("kernelMatrix");
	}

	public void setKernelMatrix(String kernelMatrix) {
		setAttribute("kernelMatrix", kernelMatrix);
	}

	public String getDivisor() {
		return getAttribute("divisor");
	}

	public void setDivisor(String divisor) {
		setAttribute("divisor", divisor);
	}

	public String getBias() {
		return getAttribute("bias");
	}

	public void setBias(String bias) {
		setAttribute("bias", bias);
	}

	public Point getTarget() {
		return new Point(Integer.parseInt(getAttribute("targetX")), Integer.parseInt(getAttribute("targetY")));
	}

	public void setTarget(Point target) {
		setAttribute("targetX", target.x + "");
		setAttribute("targetY", target.y + "");
	}

	public String getEdgeMode() {
		return getAttribute("edgeMode");
	}

	public void setEdgeMode(String edgeMode) {
		setAttribute("edgeMode", edgeMode);
	}

	public String getKernelUnitLength() {
		return getAttribute("kernelUnitLength");
	}

	public void setKernelUnitLength(String kernelUnitLength) {
		setAttribute("kernelUnitLength", kernelUnitLength);
	}

	public boolean isPreserveAlpha() {
		return Boolean.parseBoolean(getAttribute("preserveAlpha"));
	}

	public void setPreserveAlpha(boolean preserveAlpha) {
		setAttribute("preserveAlpha", preserveAlpha + "");
	}

	
}
