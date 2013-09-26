/*
 * 
 */
package org.castafiore.ui.ex.form;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.dynaform.InputVerifier;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;

public class UISlider extends EXContainer implements FormComponent<Integer>, Event{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orientation = "horizontal";
	
	private int min =0;;
	
	private int max = 100;
	
	private boolean animate = false;
	
	private int step = 1;
	
	private List<SliderValueChangeListener> changeListeners;
	
	
	

	

	public UISlider(String name) {
		super(name, "div");
		setAttribute("value", "0");
		addEvent(this, Event.MISC);
		
	}
	
	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
		setRendered(false);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
		setRendered(false);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		setRendered(false);
	}

	public boolean isAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
		setRendered(false);
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
		setRendered(false);
	}
	
	
	public UISlider addValueChangeListener(SliderValueChangeListener l){
		if(this.changeListeners == null){
			changeListeners = new LinkedList<SliderValueChangeListener>();
		}
		changeListeners.add(l);
		return this;
	}
	
	public List<SliderValueChangeListener> getValueChangeListeners(){
		return changeListeners;
	}

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		
		String s = "$( '"+proxy.getIdRef()+"' ).slider( 'option', 'value' )";
		ClientProxy p = proxy.clone().makeServerRequest( new JMap().put("val", new Var(s)),this);
		
		JMap par = new JMap().put("origntation", orientation).put("min", min).put("max", max).put("animate", animate).put("step", step).put("value", getAttribute("value"));
		
		par.put("stop",p, "event", "ui");
		proxy.addMethod("slider", par);
	}
	
	public void ClientAction(ClientProxy container) {
		String s = "$( '"+container.getIdRef()+"' ).slider( 'option', 'value' )";
		container.makeServerRequest( new JMap().put("val", new Var(s)),this);
		
	}


	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String val = request.get("val");
		setAttribute("value", val);
		
		if(this.changeListeners != null){
			for(SliderValueChangeListener l : changeListeners){
				l.onChange(this, getValue());
			}
		}
		
		return true;
	}

	
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

	public Integer getValue() {
		try{
		return Integer.parseInt(getAttribute("value"));
		}catch(Exception e){
			setAttribute("value", min + "");
			return min;
		}
	}

	public void setValue(Integer value) {
		setAttribute("value", value.toString());
	}

	public FormComponent<Integer> setInputVerifier(InputVerifier verifier) {
		return this;
	}

	public InputVerifier getInputVerifier() {
		return null;
	}

	
}
