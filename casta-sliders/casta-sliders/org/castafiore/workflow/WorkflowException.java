package org.castafiore.workflow;

public class WorkflowException extends RuntimeException{

	public WorkflowException() {
		super();
	}

	public WorkflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowException(String message) {
		super(message);
	}

	public WorkflowException(Throwable cause) {
		super(cause);
	}

}
