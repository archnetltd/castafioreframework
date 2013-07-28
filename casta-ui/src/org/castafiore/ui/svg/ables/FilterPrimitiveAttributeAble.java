package org.castafiore.ui.svg.ables;

import java.awt.Point;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;

public interface FilterPrimitiveAttributeAble extends CoreAttributeAble{
	
	public Dimension getHeight();
	
	public Container setHeight(Dimension height);
	
	public Container setWidth(Dimension width);
	
	public Dimension getWidth();
	
	public Point getXY();
	
	public void setXY(Point xy);
	
	public String getResult();
	
	public void setResult(String result);	

}
