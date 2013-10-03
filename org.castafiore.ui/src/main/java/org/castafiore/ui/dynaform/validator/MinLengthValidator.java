package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;

public class MinLengthValidator extends AbstractValidator{

private int length;
	
	
	public MinLengthValidator(int length) {
		super();
		this.length = length;
		addSupportedType(String.class);
	}


	@Override
	public boolean doValidate(FormComponent<?> field, DynaForm form) {
		String s = (String) field.getValue();
		if(s != null && s.length() >0){
			if(s.length() >= length){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
}
