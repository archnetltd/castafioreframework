package org.castafiore.catalogue;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Value;

@Entity
public class ProductOption extends Value{
	
	private BigDecimal overhead;

	public BigDecimal getOverhead() {
		return overhead;
	}

	public void setOverhead(BigDecimal overhead) {
		this.overhead = overhead;
	}

	
	
	

}
