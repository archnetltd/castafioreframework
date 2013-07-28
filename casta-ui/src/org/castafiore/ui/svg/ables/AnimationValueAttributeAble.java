package org.castafiore.ui.svg.ables;

import org.castafiore.ui.svg.values.Values;

public interface AnimationValueAttributeAble extends CoreAttributeAble{
	// calcMode, values, keyTimes, keySplines, from, to, by

	public void setCalcMode(Values.CalcModeValues calcMode);

	public Values.CalcModeValues getCalcMode();

	public void setValues(String values);

	public String getValues();

	public void setKeyTimes(String keyTimes);

	public String getKeyTimes();

	public void setKeySplines(String keySplines);

	public String getSplines();

	public void setFrom(String from);

	public String getFrom();

	public void setTo(String to);

	public String getTo();

	public void setBy(String by);

	public String getBy();

}
