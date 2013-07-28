package org.castafiore.ui.svg.animation;

import org.castafiore.ui.svg.SVGContainer;
import org.castafiore.ui.svg.ables.AnimationAttributeTargetAttributeAble;
import org.castafiore.ui.svg.ables.AnimationTimingAttributeAble;
import org.castafiore.ui.svg.ables.ConditionalProcessingAttributeAble;
import org.castafiore.ui.svg.ables.XLinkAttributeAble;
import org.castafiore.ui.svg.values.CountingValue;
import org.castafiore.ui.svg.values.Values.AnimationFillValues;
import org.castafiore.ui.svg.values.Values.AnimationRestartValues;
import org.castafiore.ui.svg.values.Values.AttributeTypesValues;

public class AbstractSVGAnimate extends SVGContainer implements
		ConditionalProcessingAttributeAble, XLinkAttributeAble,
		AnimationAttributeTargetAttributeAble, AnimationTimingAttributeAble {

	public AbstractSVGAnimate(String name, String tagName, String attributeType) {
		super(name, tagName);
	}

	@Override
	public String getRequiredExtensions() {
		return getAttribute("requiredExtensions");
	}

	@Override
	public String getRequiredFeatures() {
		return getAttribute("requiredFeatures");
	}

	@Override
	public String getSystemLanguage() {
		return getAttribute("systemLanguage");
	}

	@Override
	public void setRequiredExtensions(String requiredExtensions) {
		setAttribute("requiredExtensions", requiredExtensions);

	}

	@Override
	public void setRequiredFeatures(String requiredFeatures) {
		setAttribute("requiredFeatures", requiredFeatures);

	}

	@Override
	public void setSystemLanguage(String systemLanguage) {
		setAttribute("systemLanguage", systemLanguage);

	}

	@Override
	public String getXLinkActuate() {
		return getAttribute("xlink:acutuate");
	}

	@Override
	public String getXLinkArcRole() {
		return getAttribute("xlink:arcRole");
	}

	@Override
	public String getXLinkHref() {
		return getAttribute("xlink:href");
	}

	@Override
	public String getXLinkRole() {
		return getAttribute("xlink:role");
	}

	@Override
	public String getXLinkShow() {
		return getAttribute("xlink:show");
	}

	@Override
	public String getXLinkTitle() {
		return getAttribute("xlink:title");
	}

	@Override
	public String getXLinkType() {
		return getAttribute("xlink:type");
	}

	@Override
	public void setXLinkActuate(String s) {
		setAttribute("xlink:actuate", s);

	}

	@Override
	public void setXLinkArcRole(String s) {
		setAttribute("xlink:arcRole", s);

	}

	@Override
	public void setXLinkHref(String s) {
		setAttribute("xlink:href", s);

	}

	@Override
	public void setXLinkRole(String s) {
		setAttribute("xlink:role", s);

	}

	@Override
	public void setXLinkShow(String s) {
		setAttribute("xlink:show", s);

	}

	@Override
	public void setXLinkTitle(String s) {
		setAttribute("xlink:title", s);
	}

	@Override
	public void setXLinkType(String s) {
		setAttribute("xlink:type", s);
	}

	@Override
	public String getAttributeName() {
		return getAttribute("attributeName");
	}

	@Override
	public AttributeTypesValues getAttributeType() {
		return AttributeTypesValues.valueOf(getAttribute("attributeType"));

	}

	@Override
	public void setAttributeName(String attributeName) {
		setAttribute("attributeName", attributeName);
		
	}

	@Override
	public void setAttributeType(AttributeTypesValues type) {
		setAttribute("attributeType", type.toString());
		
	}

	@Override
	public String getBegin() {
		return getAttribute("begin");
	}

	@Override
	public String getDuration() {
		return getAttribute("duration");
	}

	@Override
	public String getEnd() {
		return getAttribute("end");
	}

	@Override
	public AnimationFillValues getFill() {
		return AnimationFillValues.valueOf(getAttribute("fill"));
	}

	@Override
	public String getMax() {
		return getAttribute("max");
	}

	@Override
	public String getMin() {
		return getAttribute("min");
	}

	@Override
	public CountingValue getRepeatCount() {
		return CountingValue.create(getAttribute("repeatCount"));
	}

	@Override
	public AnimationRestartValues getRestart() {
		return AnimationRestartValues.valueOf(getAttribute("restart"));
	}

	@Override
	public void setBegin(String begin) {
		setAttribute("begin", begin);
		
	}

	@Override
	public void setDuration(String duration) {
		setAttribute("duration", duration);
		
	}

	@Override
	public void setEnd(String end) {
		setAttribute("end", end);
		
	}

	@Override
	public void setFill(AnimationFillValues fill) {
		setAttribute("fill", fill.toString());
		
	}

	@Override
	public void setMax(String max) {
		setAttribute("max", max);
		
	}

	@Override
	public void setMin(String min) {
		setAttribute("min", min);
		
	}

	@Override
	public void setRepeatCount(CountingValue repeatCount) {
		setAttribute("repeatCount", repeatCount.toString());
		
	}

	@Override
	public void setRestart(AnimationRestartValues restart) {
		setAttribute("restart", restart.toString());
		
	}

	
}
