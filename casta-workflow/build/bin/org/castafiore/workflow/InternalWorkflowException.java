package org.castafiore.workflow;

public class InternalWorkflowException extends RuntimeException{

	public InternalWorkflowException() {
		super();
		
	}

	public InternalWorkflowException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InternalWorkflowException(String message) {
		super(message);
		
	}

	public InternalWorkflowException(Throwable cause) {
		super(cause);
		
	}

}
