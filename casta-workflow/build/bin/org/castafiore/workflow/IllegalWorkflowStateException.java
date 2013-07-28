package org.castafiore.workflow;

public class IllegalWorkflowStateException extends RuntimeException{

	public IllegalWorkflowStateException() {
		super();
		
	}

	public IllegalWorkflowStateException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public IllegalWorkflowStateException(String message) {
		super(message);
		
	}

	public IllegalWorkflowStateException(Throwable cause) {
		super(cause);
		
	}

}
