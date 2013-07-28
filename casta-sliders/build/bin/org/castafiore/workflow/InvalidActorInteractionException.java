package org.castafiore.workflow;

public class InvalidActorInteractionException extends WorkflowException {

	public InvalidActorInteractionException() {
		super();
	}

	public InvalidActorInteractionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidActorInteractionException(String message) {
		super(message);
	}

	public InvalidActorInteractionException(Throwable cause) {
		super(cause);

	}

}
