package org.castafiore.ui.svg.animation;

import org.castafiore.ui.svg.ables.AnimationAdditionAttributeAble;
import org.castafiore.ui.svg.values.Values.AnimationAdditionValues;

public class SVGAnimate extends AbstractSVGAnimate implements AnimationAdditionAttributeAble{

	public SVGAnimate(String name,  String attributeType) {
		super(name, "animate", attributeType);
	}
	protected SVGAnimate(String name, String tag, String attributeType) {
		super(name, tag, attributeType);
	}
	@Override
	public AnimationAdditionValues getAdditive() {
		return AnimationAdditionValues.valueOf(getAttribute("additive"));
	}
	@Override
	public void setAdditive(AnimationAdditionValues att) {
		setAttribute("additive", att.toString());
		
	}
	@Override
	public AnimationAdditionValues getAccumulate() {
		return AnimationAdditionValues.valueOf(getAttribute("accumulate"));
	}
	@Override
	public void setAccumulate(AnimationAdditionValues att) {
		setAttribute("accumulate", att.toString());
		
	}
}
