package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;

/**
 * Validator interface for validating inputs
 * @author arossaye
 *
 */
public interface Validator {
	
	/**
	 * 
	 * @return supported datatypes to validate
	 */
	public Class<?>[] getSupportedDataTypes();
	
	/**
	 * Validate the field
	 * @param field The field to validate
	 * @param form The form this field belongs to
	 */
	public boolean validate(FormComponent<?> field, DynaForm form);
	
	public void setErrorMsg(String errorMsg);
}
