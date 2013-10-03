package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;

public class ContainsValidator extends AbstractValidator {

	private String contains;
	
	
	
	public ContainsValidator(String contains) {
		super();
		this.contains = contains;
		addSupportedType(String.class);
		setErrorMsg("Should contain " + contains);
	}


	

	@Override
	public boolean doValidate(FormComponent<?> field, DynaForm form) {
		
		Object value = field.getValue();
		if(value != null && value.toString().contains(contains)){
			return true;
		}else{
			return false;
		}
		
	}

	

}
