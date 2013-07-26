package org.castafiore.shoppingmall.checkout;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.castafiore.wfs.types.FileImpl;

@Entity
public class DeliveryOptions extends FileImpl {

	private boolean ups = false;

	private String transportPayer;
	
	private BigDecimal freeDeliveryThreshold = BigDecimal.ZERO;


	public boolean isUps() {
		return ups;
	}

	public void setUps(boolean ups) {
		this.ups = ups;
	}

	public String getTransportPayer() {
		return transportPayer;
	}
	public void setTransportPayer(String transportPayer) {
		this.transportPayer = transportPayer;
	}

	public BigDecimal getFreeDeliveryThreshold() {
		if(freeDeliveryThreshold == null){
			freeDeliveryThreshold = BigDecimal.ZERO;
		}
		return freeDeliveryThreshold;
	}

	public void setFreeDeliveryThreshold(BigDecimal freeDeliveryThreshold) {
		this.freeDeliveryThreshold = freeDeliveryThreshold;
	}
	
	
	
}
