package org.castafiore.ui.dynaform.validator;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.DynaForm;
import org.castafiore.utils.StringUtil;

public class RequiredValidator extends AbstractValidator{
	

	

	public RequiredValidator() {
		super();
		addSupportedType(Object.class);
		setErrorMsg("Field is required");
	}

	@Override
	public boolean doValidate(FormComponent<?> field, DynaForm form) {
		String o = (String)field.getValue();
		if(StringUtil.isNotEmpty(o)){
			return true;
		}
		return false;
	}

}
