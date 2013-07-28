package org.castafiore.ui.svg.ables;

import org.castafiore.ui.svg.values.CountingValue;
import org.castafiore.ui.svg.values.Values;

public interface AnimationTimingAttributeAble extends CoreAttributeAble{
	//begin, dur, end, min, max, restart, repeatCount, repeatDur, fill
	
	public String getBegin();
	
	public void setBegin(String begin);
	
	public String getDuration();
	
	public void setDuration(String duration);
	
	public String getEnd();
	
	public void setEnd(String end);
	
	public String getMin();
	
	public void setMin(String min);
	
	public String getMax();
	
	public void setMax(String max);
	
	public Values.AnimationRestartValues getRestart();
	
	public void setRestart(Values.AnimationRestartValues restart);
	
	public void setRepeatCount(CountingValue repeatCount);
	
	public CountingValue getRepeatCount();
	
	public void setFill(Values.AnimationFillValues fill);
	
	public Values.AnimationFillValues getFill();

}
