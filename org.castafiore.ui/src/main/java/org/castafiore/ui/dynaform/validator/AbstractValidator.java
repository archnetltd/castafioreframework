package org.castafiore.ui.dynaform.validator;

import java.util.LinkedList;
import java.util.List;

import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.dynaform.DynaForm;

public abstract class AbstractValidator implements Validator{
	
	private List<Class<?>> supportedTypes = new LinkedList<Class<?>>();
	
	private String errorMsg;

	@Override
	public Class<?>[] getSupportedDataTypes() {
		return supportedTypes.toArray(new Class<?>[supportedTypes.size()]);
	}
	
	protected AbstractValidator addSupportedType(Class<?> clazz){
		supportedTypes.add(clazz);
		return this;
	}
	
	protected boolean isSupported(FormComponent<?> field){
		Object value = field.getValue();
		if(value ==null){
			return true;
		}
		for(Class<?> clazz : getSupportedDataTypes()){
			if(clazz.isAssignableFrom(value.getClass())){
				return true;
			}
		}
		return false;
	}
	
	public boolean validate(FormComponent<?> field, DynaForm form){
		if(isSupported(field)){
			boolean valid = doValidate(field, form);
			if(!valid){
				form.addErrorMsg(getErrorMsg());
				field.addClass("ui-state-error");
			}
			
			return valid;
		}
		throw new UIException("Unsupported return value for field " +form.getName() + "." + field.getName() + " by validator " + getClass().getSimpleName());
	}

	public abstract boolean doValidate(FormComponent<?> field, DynaForm form);
	
	
	protected String getErrorMsg(){
		return errorMsg;
	}

	@Override
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		
	}

}
