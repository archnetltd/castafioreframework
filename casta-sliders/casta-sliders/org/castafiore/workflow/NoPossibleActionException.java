package org.castafiore.workflow;

public class NoPossibleActionException extends WorkflowException{

	public NoPossibleActionException() {
		super();
		
	}

	public NoPossibleActionException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NoPossibleActionException(String message) {
		super(message);
		
	}

	public NoPossibleActionException(Throwable cause) {
		super(cause);
		
	}

}
