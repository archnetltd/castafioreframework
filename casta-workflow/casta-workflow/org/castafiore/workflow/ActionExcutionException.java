package org.castafiore.workflow;

public class ActionExcutionException extends RuntimeException{

	public ActionExcutionException() {
		super();
		
	}

	public ActionExcutionException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ActionExcutionException(String message) {
		super(message);
		
	}

	public ActionExcutionException(Throwable cause) {
		super(cause);
		
	}

}
