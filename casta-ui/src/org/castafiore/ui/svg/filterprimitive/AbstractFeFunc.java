package org.castafiore.ui.svg.filterprimitive;

public class AbstractFeFunc extends AbstractFe {

	public AbstractFeFunc(String name, String tagName) {
		super(name, tagName);
	}

	public String getTableValues() {
		return getAttribute("tableValues");
	}

	public void setTableValues(String tableValues) {
		setAttribute("tableValues", tableValues);
	}

	public String getSlope() {
		return getAttribute("slope");
	}

	public void setSlope(String slope) {
		setAttribute("slope", slope);
	}

	public String getIntercept() {
		return getAttribute("intercept");
	}

	public void setIntercept(String intercept) {
		setAttribute("intercept", intercept);
	}

	public String getAmplitude() {
		return getAttribute("amplitude");
	}

	public void setAmplitude(String amplitude) {
		setAttribute("amplitude", amplitude);
	}

	public String getExponent() {
		return getAttribute("exponent");
	}

	public void setExponent(String exponent) {
		setAttribute("exponent", exponent);
	}

	public String getOffset() {
		return getAttribute("offset");
	}

	public void setOffset(String offset) {
		setAttribute("offset", offset);
	}

}
