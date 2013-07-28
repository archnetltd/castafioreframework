package org.castafiore.ui.svg.ables;

import org.castafiore.ui.svg.values.Values;

public interface AnimationAdditionAttributeAble extends CoreAttributeAble {

	//additive, accumulate
	
	public void setAdditive(Values.AnimationAdditionValues att);
	
	public Values.AnimationAdditionValues getAdditive();
	
public void setAccumulate(Values.AnimationAdditionValues att);
	
	public Values.AnimationAdditionValues getAccumulate();
	
}
