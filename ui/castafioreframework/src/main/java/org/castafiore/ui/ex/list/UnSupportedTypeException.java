package org.castafiore.ui.ex.list;

import org.castafiore.ui.UIException;

public class UnSupportedTypeException extends UIException{

	public UnSupportedTypeException() {
		super();
		
	}

	public UnSupportedTypeException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UnSupportedTypeException(String message) {
		super(message);
		
	}

	public UnSupportedTypeException(Throwable cause) {
		super(cause);
		
	}

}
