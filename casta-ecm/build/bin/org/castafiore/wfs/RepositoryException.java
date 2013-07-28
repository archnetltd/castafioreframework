package org.castafiore.wfs;

public class RepositoryException extends WFSException{

	public RepositoryException() {
		super();
		
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public RepositoryException(String message) {
		super(message);
		
	}

	public RepositoryException(Throwable cause) {
		super(cause);
		
	}

}
