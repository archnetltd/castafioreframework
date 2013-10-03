package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;

public class MinValidator extends AbstractValidator{
	
	private Number min;

	public MinValidator(Number min) {
		super();
		this.min = min;
		addSupportedType(Number.class);
	}

	@Override
	public boolean doValidate(FormComponent<?> field, DynaForm form) {
		Number n = (Number)field.getValue();
		if(n != null){
			return n.doubleValue() >= min.doubleValue();
		}return true;
	}
	
	

}
