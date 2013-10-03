package org.castafiore.ui.dynaform;

import java.util.Map;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

/**
 * Handler triggered when a form is submitted using the {@link DynaForm#SUBMIT} event
 * @author arossaye
 * 
 *@see DynaForm#SUBMIT
 */
public interface SubmitFormHandler {
	
	/**
	 * Triggered in {@link Event#ServerAction(org.castafiore.ui.Container, Map)}
	 * @param form The {@link DynaForm} on which this handler is added
	 * @param request The request parameters from {@link Event#ClientAction(ClientProxy)} <br>This parameter should be filled and passed to {@link #onSubmitFormSuccess(ClientProxy, Map)}
	 */
	public void onSubmitForm(DynaForm form, Map<String,String> request);
	
	/**
	 * Triggers in {@link Event#Success(ClientProxy, Map)} 
	 * @param form The {@link DynaForm} on which this handler is added
	 * @param request The request parameters from {@link Event#ClientAction(ClientProxy)} through {@link Event#ServerAction(org.castafiore.ui.Container, Map)} via {@link #onSubmitForm(DynaForm, Map)}
	 */
	public void onSubmitFormSuccess(ClientProxy form, Map<String,String> request);

}
