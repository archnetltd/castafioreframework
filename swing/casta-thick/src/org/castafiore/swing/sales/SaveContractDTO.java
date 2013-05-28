package org.castafiore.swing.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class SaveContractDTO extends ValueObjectImpl{
	
	private String plan;
	
	private String planDetail;
	
	private Date date;
	
	private String pointOfSale;
	
	private Map<String, String> options = new HashMap<String, String>();
	
	private String source;
	
	private String salesAgent;
	
	private String fsCode;
	
	private String promotion;
	
	private String contactFirstName;
	
	private String contactLastName;
	
	private String contactNic;
	
	private String contactEmail;
	
	private String contactPhone;
	
	private String contactMobile;
	
	private String contactAddressLine1;
	
	private String contactAddressLine2;
	
	private String principalFirstName;
	
	private String principalLastName;
	
	private String principalNic;
	
	private String principalEmail;
	
	private String principalPhone;
	
	private String principalMobile;
	
	private String principalAddressLine1;
	
	private String principalAddressLine2;
	
	private List<DependantVO> dependants = new ArrayList<DependantVO>();
	
	private String firstPaymentMethod;
	
	private String monthlyPaymentMethod;
	
	private String bankAccountNumber;
	
	private String bankName;
	
	private String status;
	
	private BigDecimal installment = BigDecimal.ZERO;
	
	

	public String getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSalesAgent() {
		return salesAgent;
	}

	public void setSalesAgent(String salesAgent) {
		this.salesAgent = salesAgent;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
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

	public String getContactNic() {
		return contactNic;
	}

	public void setContactNic(String contactNic) {
		this.contactNic = contactNic;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
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

	

	public String getPrincipalEmail() {
		return principalEmail;
	}

	public void setPrincipalEmail(String principalEmail) {
		this.principalEmail = principalEmail;
	}

	public String getPrincipalPhone() {
		return principalPhone;
	}

	public void setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
	}

	public String getPrincipalMobile() {
		return principalMobile;
	}

	public void setPrincipalMobile(String principalMobile) {
		this.principalMobile = principalMobile;
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

	public List<DependantVO> getDependants() {
		return dependants;
	}

	public void setDependants(List<DependantVO> dependants) {
		this.dependants = dependants;
	}

	public String getFirstPaymentMethod() {
		return firstPaymentMethod;
	}

	public void setFirstPaymentMethod(String firstPaymentMethod) {
		this.firstPaymentMethod = firstPaymentMethod;
	}

	public String getMonthlyPaymentMethod() {
		return monthlyPaymentMethod;
	}

	public void setMonthlyPaymentMethod(String monthlyPaymentMethod) {
		this.monthlyPaymentMethod = monthlyPaymentMethod;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPlanDetail() {
		return planDetail;
	}

	public void setPlanDetail(String planDetail) {
		this.planDetail = planDetail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getCustomer(){
		return contactFirstName + " " + contactLastName;
	}
	
	public void setCustomer(String customer){
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public String getPrincipalNic() {
		return principalNic;
	}

	public void setPrincipalNic(String principalNic) {
		this.principalNic = principalNic;
	}

	
}
