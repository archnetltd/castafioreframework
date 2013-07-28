package org.castafiore.ui.svg.ables;

public interface TransferFunctionAttributeAble extends CoreAttributeAble{

	//type, tableValues, slope, intercept, amplitude, exponent, offset
	public String getType();
	public String getTableValues();
	public String getSlope();
	public String getIntercept();
	public String getAmplitude();
	public String getExponent();
	public String getOffset();
	
	public void setType(String s);
	public void setTableValues(String s);
	public void setSlope(String s);
	public void setIntercept(String s);
	public void setAmplitude(String s);
	public void setExponent(String s);
	public void setOffset(String s);
	
	
	
	
}
