/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.castafiore.ui.dynaform;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.dynaform.validator.Validator;
import org.castafiore.ui.engine.CastafioreApplicationContextHolder;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

/**
 * 
 * Interface for handling a dynamic form.<br>
 * It is recommended to make components implement this interface to handle
 * dynamic forms.<br>
 * 
 * 
 * @author Kureem Rossaye
 * 
 */
public interface DynaForm extends Container {

	/**
	 * 
	 * @return returns a map of fields configured in this Form
	 */
	public Map<String, FormComponent<?>> getFieldsMap();

	/**
	 * 
	 * @param name
	 *            The name of the field to search
	 * @return The Form component with the specified name
	 */
	public FormComponent<?> getField(String name);

	/**
	 * Adds a field with the specified label
	 * 
	 * @param label
	 *            - The label of the field
	 * @param input
	 *            - The field
	 * @return
	 */
	public DynaForm addField(String label, FormComponent<?> input);
	
	
	/**
	 * Adds a validator for the specified field
	 * @param fieldName The field to associate the validator
	 * @param validator The validator to add
	 * @return This form
	 */
	public DynaForm addValidator(String fieldName, Validator validator);

	/**
	 * Adds a button to the form
	 * 
	 * @param button
	 *            The button to add
	 * @return This current form
	 */
	public DynaForm addButton(Button button);

	/**
	 * 
	 * @return All fields in the form of a list
	 */
	public List<FormComponent<?>> getFields();

	/**
	 * Sets the label for a specified field
	 * 
	 * @param label
	 *            The label of the field
	 * @param c
	 *            The form component to set label for
	 */
	public void setLabelFor(String label, FormComponent<?> c);
	
	
	/**
	 * Clears all error information in this form
	 * @return This form
	 */
	public DynaForm clearErrors();
	
	
	/**
	 * Resets the values of this form
	 * @return This form
	 */
	public DynaForm reset();
	
	/**
	 * Adds an error message in this form
	 * @param msg
	 * @return
	 */
	public DynaForm addErrorMsg(String msg);
	
	
	/**
	 * Validates all validatable fields in this form.
	 * @return <code>true</code> if no error else <code>false</code>
	 */
	public boolean validate();
	
	
	/**
	 * adds a handler to be triggered when the form is submitted
	 * @param hander The handler to add
	 * @return This {@link DynaForm}
	 * @see #SUBMIT
	 */
	public DynaForm addSubmitFormHandler(SubmitFormHandler hander);
	
	/**
	 * Trigger server part of onsubmitformhandler
	 */
	public void triggerOnSubmitFormHandlers(Map<String,String> request);
	
	/**
	 * Trigger success part of on submit form handlers
	 */
	public void triggerOnSubmitFormHandlersSuccess(ClientProxy form, Map<String,String> request);
	
	
	/**
	 * Event to be added on reset button.
	 * Will call {@link #reset()}
	 */
	public final static Event RESET = new Event() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
		}
		
		@Override
		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			container.getAncestorOfType(DynaForm.class).reset();
			return true;
		}
		
		@Override
		public void ClientAction(ClientProxy container) {
			container.mask().makeServerRequest(this);
		}
	};
	
	/**
	 * event to be added on submit button.
	 * The event will do validation and trigger {@link SubmitFormHandler}s
	 */
	public final static Event SUBMIT = new Event(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void ClientAction(ClientProxy container) {
			container.mask().makeServerRequest(this);
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			DynaForm form = container.getAncestorOfType(DynaForm.class);
			if(form.validate()){
				form.triggerOnSubmitFormHandlers(request);
				request.put("formid", form.getId());
			}
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			if(request.containsKey("formid")){
				Application app =CastafioreApplicationContextHolder.getCurrentApplication();
				DynaForm form = (DynaForm)app.getDescendentById(request.get("formid"));
				form.triggerOnSubmitFormHandlersSuccess(container.getAncestorOfType(DynaForm.class), request);
			}
		}
		
	};

}
