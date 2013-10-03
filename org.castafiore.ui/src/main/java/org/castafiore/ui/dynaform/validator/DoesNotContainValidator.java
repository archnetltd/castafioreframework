package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;

public class DoesNotContainValidator extends AbstractValidator{
	
	private String contain;
	
	

	public DoesNotContainValidator(String contain) {
		super();
		this.contain = contain;
		addSupportedType(String.class);
		setErrorMsg("Should not contain " + contain);
	}



	@Override
	public boolean doValidate(FormComponent<?> field, DynaForm form) {
		Object val = field.getValue();
		if(val == null || val.toString().contains(contain)){
			return false;
		}
		return true;
	}
	
	

}
