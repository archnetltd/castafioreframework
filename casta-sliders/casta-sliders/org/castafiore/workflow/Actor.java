package org.castafiore.workflow;

import java.io.Serializable;

public interface Actor extends Serializable{
	
	public String getUsername();
	
	
	public boolean equals(Object other);
	
	

}
