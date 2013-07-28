package org.castafiore.swing.payments;

import java.math.BigDecimal;

public class PaymentDetail {
	private String fsCode;
	
	private long startPaymentMonth;
	
	private BigDecimal installment;
	
	private BigDecimal arrear;
	
	private int installmentsPaid;
	
 

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	public long getStartPaymentMonth() {
		return startPaymentMonth;
	}

	public void setStartPaymentMonth(long startPaymentMonth) {
		this.startPaymentMonth = startPaymentMonth;
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public BigDecimal getArrear() {
		return arrear;
	}

	public void setArrear(BigDecimal arrear) {
		this.arrear = arrear;
	}

	public int getInstallmentsPaid() {
		return installmentsPaid;
	}

	public void setInstallmentsPaid(int installmentsPaid) {
		this.installmentsPaid = installmentsPaid;
	}
	
	

}
