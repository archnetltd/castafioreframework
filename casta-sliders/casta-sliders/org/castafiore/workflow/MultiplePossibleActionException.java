package org.castafiore.workflow;

public class MultiplePossibleActionException extends WorkflowException{

	public MultiplePossibleActionException() {
		super();
		
	}

	public MultiplePossibleActionException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public MultiplePossibleActionException(String message) {
		super(message);
		
	}

	public MultiplePossibleActionException(Throwable cause) {
		super(cause);
		
	}

}
