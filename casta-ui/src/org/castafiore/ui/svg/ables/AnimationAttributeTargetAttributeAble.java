package org.castafiore.ui.svg.ables;

import org.castafiore.ui.svg.values.Values;

public interface AnimationAttributeTargetAttributeAble extends CoreAttributeAble{
//attributeType, attributeName
	
	public Values.AttributeTypesValues getAttributeType();
	
	public void setAttributeType(Values.AttributeTypesValues type);
	
	public String getAttributeName();
	
	public void setAttributeName(String attributeName);
	
}
