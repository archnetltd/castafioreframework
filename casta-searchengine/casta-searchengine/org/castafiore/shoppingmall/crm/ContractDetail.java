package org.castafiore.shoppingmall.crm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ContractDetail {
	
	private String fsCode; 
	
	private String code;
	
	private String planDetail;
	
	private BigDecimal installment;
	
	private BigDecimal joiningFee;
	
	private BigDecimal total;
	
	private String salesAgent;
	
	private String pointOfSale;
	
	private Date registrationDate;
	
	private Date effectiveDate;
	
	private Date transactionDate;
	
	private String contactFirstName;
	
	private String contactLastName;
	
	private String contactEmail;
	
	private String contactAddressLine1;
	
	private String contactAddressLine2;
	
	private String contactTel;
	
	private String contactMobile;
	
	private String contactNIC;
	
	private String principalFirstName;
	
	private String principalLastName;
	
	private String principalAddressLine1;

	private String principalAddressLine2;
	
	private String principalTel;
	
	private String principalMobile;
	
	private String principalNIC;
	
	private String principalEmail;
	
	private String spouseLastName;
	
	private String spouseNIC;
	
	
	private String bankName;
	
	private String accountNumber;
	
	private String paymentMethod;
	
	private List<Dependent> dependants=new LinkedList<Dependent>();

	
	
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPlanDetail() {
		return planDetail;
	}

	public void setPlanDetail(String planDetail) {
		this.planDetail = planDetail;
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public BigDecimal getJoiningFee() {
		return joiningFee;
	}

	public void setJoiningFee(BigDecimal joiningFee) {
		this.joiningFee = joiningFee;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getSalesAgent() {
		return salesAgent;
	}

	public void setSalesAgent(String salesAgent) {
		this.salesAgent = salesAgent;
	}

	public String getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactAddressLine1() {
		return contactAddressLine1;
	}

	public void setContactAddressLine1(String contactAddressLine1) {
		this.contactAddressLine1 = contactAddressLine1;
	}

	public String getContactAddressLine2() {
		return contactAddressLine2;
	}

	public void setContactAddressLine2(String contactAddressLine2) {
		this.contactAddressLine2 = contactAddressLine2;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactNIC() {
		return contactNIC;
	}

	public void setContactNIC(String contactNIC) {
		this.contactNIC = contactNIC;
	}

	public String getPrincipalFirstName() {
		return principalFirstName;
	}

	public void setPrincipalFirstName(String principalFirstName) {
		this.principalFirstName = principalFirstName;
	}

	public String getPrincipalLastName() {
		return principalLastName;
	}

	public void setPrincipalLastName(String principalLastName) {
		this.principalLastName = principalLastName;
	}

	public String getPrincipalAddressLine1() {
		return principalAddressLine1;
	}

	public void setPrincipalAddressLine1(String principalAddressLine1) {
		this.principalAddressLine1 = principalAddressLine1;
	}

	public String getPrincipalAddressLine2() {
		return principalAddressLine2;
	}

	public void setPrincipalAddressLine2(String principalAddressLine2) {
		this.principalAddressLine2 = principalAddressLine2;
	}

	public String getPrincipalTel() {
		return principalTel;
	}

	public void setPrincipalTel(String principalTel) {
		this.principalTel = principalTel;
	}

	public String getPrincipalMobile() {
		return principalMobile;
	}

	public void setPrincipalMobile(String principalMobile) {
		this.principalMobile = principalMobile;
	}

	public String getPrincipalNIC() {
		return principalNIC;
	}

	public void setPrincipalNIC(String principalNIC) {
		this.principalNIC = principalNIC;
	}

	public String getPrincipalEmail() {
		return principalEmail;
	}

	public void setPrincipalEmail(String principalEmail) {
		this.principalEmail = principalEmail;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<Dependent> getDependants() {
		return dependants;
	}

	public void setDependants(List<Dependent> dependants) {
		this.dependants = dependants;
	}

	public String getSpouseLastName() {
		return spouseLastName;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}

	public String getSpouseNIC() {
		return spouseNIC;
	}

	public void setSpouseNIC(String spouseNIC) {
		this.spouseNIC = spouseNIC;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
	
	
	
	
	

}
