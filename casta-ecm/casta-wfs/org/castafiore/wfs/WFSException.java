package org.castafiore.wfs;

public class WFSException extends RuntimeException{

	public WFSException() {
		super();
	}

	public WFSException(String message, Throwable cause) {
		super(message, cause);
	}

	public WFSException(String message) {
		super(message);
	}
	public WFSException(Throwable cause) {
		super(cause);
	}

}
