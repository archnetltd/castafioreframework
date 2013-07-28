package org.castafiore.wfs.types;

import javax.persistence.Entity;

@Entity
public class Sequence extends Value{

	public int getNextSequence(){
		Double d = super.getDouble();
		return d.intValue();
		
	}
	
}
