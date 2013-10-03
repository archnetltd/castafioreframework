package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;

public class MaxValidator extends AbstractValidator {
	
	private Number max;

	public MaxValidator(Number max) {
		super();
		this.max = max;
		addSupportedType(Number.class);
		setErrorMsg("The maximum value is " + max);
	}

	@Override
	public boolean doValidate(FormComponent<?> field, DynaForm form) {
		Number n = (Number)field.getValue();
		if(n != null){
			return n.doubleValue() <= max.doubleValue();
		}return true;
	}

}
