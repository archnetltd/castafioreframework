package org.castafiore.ui.ex.form;

import org.castafiore.ui.EXContainer;
import org.castafiore.ui.FormComponent;

public class EXRange extends EXContainer implements FormComponent<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EXRange(String name) {
		super(name, "input");
		setReadOnlyAttribute("type", "range");
	}
	
	public EXRange(String name,Integer value){
		super(name,"input");
		setReadOnlyAttribute("type", "range");
		setValue(value);
	}
	
	public EXRange setMin(Integer min){
		setAttribute("min", min.toString());
		return this;
	}
	
	public EXRange setMax(Integer max){
		setAttribute("max", max.toString());
		return this;
	}
	
	public Integer getMax(){
		try{
			return Integer.parseInt(getAttribute("max"));
		}catch(Exception e){
			setAttribute("max", "100");
			return getMax();
		}
	}
	
	public EXRange setStep(Integer step){
		setAttribute("step", step.toString());
		return this;
	}
	
	public Integer getStep(){
		try{
			return Integer.parseInt(getAttribute("step"));
		}catch(Exception e){
			setAttribute("step", "1");
			return getStep();
		}
	}

	@Override
	public Integer getValue() {
		try{
			return Integer.parseInt(getAttribute("value"));
		}catch(Exception e){
			setAttribute("value", getMin().toString());
			return getMin();
		}
	}
	
	public Integer getMin(){
		try{
			return Integer.parseInt(getAttribute("min"));
		}catch(Exception e){
			setAttribute("min", "0");
			return getMin();
		}
	}

	@Override
	public void setValue(Integer value) {
		if(value == null)
			setValue(0);
		else
			setAttribute("value", value.toString());
		
	}

	

}
