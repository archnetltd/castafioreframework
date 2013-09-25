/*
 * 
 */
package org.castafiore.ui.ex.form;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Decoder;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.Encoder;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;

public class UISlider extends EXContainer implements FormComponent, Event{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orientation = "horizontal";
	
	private int min =0;;
	
	private int max = 100;
	
	private boolean animate = false;
	
	private int step = 1;
	
	

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		
		String s = "$( '"+proxy.getIdRef()+"' ).slider( 'option', 'value' )";
		ClientProxy p = proxy.clone().makeServerRequest( new JMap().put("val", new Var(s)),this);
		
		JMap par = new JMap().put("origntation", orientation).put("min", min).put("max", max).put("animate", animate).put("step", step).put("value", getAttribute("value"));
		
		par.put("stop",p, "event", "ui");
		proxy.addMethod("slider", par);
	}

	public UISlider(String name) {
		super(name, "div");
		setAttribute("value", "0");
		addEvent(this, Event.MISC);
		
	}


	public Decoder getDecoder() {
		return null;
	}

	
	public Encoder getEncoder() {
		return null;
	}

	
	public String getRawValue() {
		return getAttribute("value");
	}

	
	public Object getValue() {
		return getRawValue();
	}


	public void setDecoder(Decoder decoder) {
		
	}

	
	public void setEncoder(Encoder encoder) {
		
	}


	public void setRawValue(String rawValue) {
		setAttribute("value", rawValue);
		
	}

	
	public void setValue(Object value) {
		setRawValue(value.toString());
		setRendered(false);
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


	public void ClientAction(ClientProxy container) {
		String s = "$( '"+container.getIdRef()+"' ).slider( 'option', 'value' )";
		container.makeServerRequest( new JMap().put("val", new Var(s)),this);
		
	}


	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String val = request.get("val");
		setAttribute("value", val);
		SliderPropagator p = getAncestorOfType(SliderPropagator.class);
		if(p != null)
			p.propagate();
		return true;
	}

	
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

	
}
