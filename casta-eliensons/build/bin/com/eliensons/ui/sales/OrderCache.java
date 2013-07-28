package com.eliensons.ui.sales;

import java.math.BigDecimal;
import java.util.Calendar;

import org.castafiore.shoppingmall.checkout.Order;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;

public class OrderCache {
	
	private Calendar timeIn;
	
	private Calendar timeOut;
	
	private String salesPerson;
	
	private String pos;
	
	private Calendar contractDate;
	
	private String fsNumber;
	
	private String planCode;
	
	private String planDetail;
	
	private String contactName;
	
	private String contactAddress;
	
	private String contactTel;
	
	private String contactMobile;
	
	private String principalAddress;
	
	private String principalTel;
	
	private String principalMobile;
	
	private String principalEmail;
	
	private BigDecimal joiningFee;
	
	private BigDecimal installment;
	
	private BigDecimal advancePayment;
	
	private BigDecimal totalFirstPayment;
	
	private String paymentMode;
	
	private String memberOne;
	
	private String idOne;
	
	private BigDecimal ageOne;
	
private String memberTwo;
	
	private String idTwo;
	
	private BigDecimal ageTwo;
	
private String memberThree;
	
	private String idThree;
	
	private BigDecimal ageThree;
	
private String memberFour;
	
	private String idFour;
	
	private BigDecimal ageFour;
	
private String memberFive;
	
	private String idFive;
	
	private BigDecimal ageFive;
	
private String memberSix;
	
	private String idSix;
	
	private BigDecimal ageSix;
	
private String memberSeven;
	
	private String idSeven;
	
	private BigDecimal ageSeven;
	
	private Calendar firstPaymentDate;
	
	private Calendar effectiveDate;
	
	private BigDecimal totalPayments;
	
	private Calendar nextPaymentDate;
	
	private BigDecimal arrears;
	
	private int status;
	
	private BigDecimal costOfFuneral;
	
	private Calendar deathDate;
	
	private Calendar cancelledDate;

	public Calendar getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Calendar timeIn) {
		this.timeIn = timeIn;
	}

	public Calendar getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Calendar timeOut) {
		this.timeOut = timeOut;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Calendar getContractDate() {
		return contractDate;
	}

	public void setContractDate(Calendar contractDate) {
		this.contractDate = contractDate;
	}

	public String getFsNumber() {
		return fsNumber;
	}

	public void setFsNumber(String fsNumber) {
		this.fsNumber = fsNumber;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanDetail() {
		return planDetail;
	}

	public void setPlanDetail(String planDetail) {
		this.planDetail = planDetail;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
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

	public String getPrincipalAddress() {
		return principalAddress;
	}

	public void setPrincipalAddress(String principalAddress) {
		this.principalAddress = principalAddress;
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

	public String getPrincipalEmail() {
		return principalEmail;
	}

	public void setPrincipalEmail(String principalEmail) {
		this.principalEmail = principalEmail;
	}

	public BigDecimal getJoiningFee() {
		return joiningFee;
	}

	public void setJoiningFee(BigDecimal joiningFee) {
		this.joiningFee = joiningFee;
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public BigDecimal getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(BigDecimal advancePayment) {
		this.advancePayment = advancePayment;
	}

	public BigDecimal getTotalFirstPayment() {
		return totalFirstPayment;
	}

	public void setTotalFirstPayment(BigDecimal totalFirstPayment) {
		this.totalFirstPayment = totalFirstPayment;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getMemberOne() {
		return memberOne;
	}

	public void setMemberOne(String memberOne) {
		this.memberOne = memberOne;
	}

	public String getIdOne() {
		return idOne;
	}

	public void setIdOne(String idOne) {
		this.idOne = idOne;
	}

	public BigDecimal getAgeOne() {
		return ageOne;
	}

	public void setAgeOne(BigDecimal ageOne) {
		this.ageOne = ageOne;
	}

	public String getMemberTwo() {
		return memberTwo;
	}

	public void setMemberTwo(String memberTwo) {
		this.memberTwo = memberTwo;
	}

	public String getIdTwo() {
		return idTwo;
	}

	public void setIdTwo(String idTwo) {
		this.idTwo = idTwo;
	}

	public BigDecimal getAgeTwo() {
		return ageTwo;
	}

	public void setAgeTwo(BigDecimal ageTwo) {
		this.ageTwo = ageTwo;
	}

	public String getMemberThree() {
		return memberThree;
	}

	public void setMemberThree(String memberThree) {
		this.memberThree = memberThree;
	}

	public String getIdThree() {
		return idThree;
	}

	public void setIdThree(String idThree) {
		this.idThree = idThree;
	}

	public BigDecimal getAgeThree() {
		return ageThree;
	}

	public void setAgeThree(BigDecimal ageThree) {
		this.ageThree = ageThree;
	}

	public String getMemberFour() {
		return memberFour;
	}

	public void setMemberFour(String memberFour) {
		this.memberFour = memberFour;
	}

	public String getIdFour() {
		return idFour;
	}

	public void setIdFour(String idFour) {
		this.idFour = idFour;
	}

	public BigDecimal getAgeFour() {
		return ageFour;
	}

	public void setAgeFour(BigDecimal ageFour) {
		this.ageFour = ageFour;
	}

	public String getMemberFive() {
		return memberFive;
	}

	public void setMemberFive(String memberFive) {
		this.memberFive = memberFive;
	}

	public String getIdFive() {
		return idFive;
	}

	public void setIdFive(String idFive) {
		this.idFive = idFive;
	}

	public BigDecimal getAgeFive() {
		return ageFive;
	}

	public void setAgeFive(BigDecimal ageFive) {
		this.ageFive = ageFive;
	}

	public String getMemberSix() {
		return memberSix;
	}

	public void setMemberSix(String memberSix) {
		this.memberSix = memberSix;
	}

	public String getIdSix() {
		return idSix;
	}

	public void setIdSix(String idSix) {
		this.idSix = idSix;
	}

	public BigDecimal getAgeSix() {
		return ageSix;
	}

	public void setAgeSix(BigDecimal ageSix) {
		this.ageSix = ageSix;
	}

	public String getMemberSeven() {
		return memberSeven;
	}

	public void setMemberSeven(String memberSeven) {
		this.memberSeven = memberSeven;
	}

	public String getIdSeven() {
		return idSeven;
	}

	public void setIdSeven(String idSeven) {
		this.idSeven = idSeven;
	}

	public BigDecimal getAgeSeven() {
		return ageSeven;
	}

	public void setAgeSeven(BigDecimal ageSeven) {
		this.ageSeven = ageSeven;
	}

	public Calendar getFirstPaymentDate() {
		return firstPaymentDate;
	}

	public void setFirstPaymentDate(Calendar firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}

	public Calendar getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Calendar effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getTotalPayments() {
		return totalPayments;
	}

	public void setTotalPayments(BigDecimal totalPayments) {
		this.totalPayments = totalPayments;
	}

	public Calendar getNextPaymentDate() {
		return nextPaymentDate;
	}

	public void setNextPaymentDate(Calendar nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	public BigDecimal getArrears() {
		return arrears;
	}

	public void setArrears(BigDecimal arrears) {
		this.arrears = arrears;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getCostOfFuneral() {
		return costOfFuneral;
	}

	public void setCostOfFuneral(BigDecimal costOfFuneral) {
		this.costOfFuneral = costOfFuneral;
	}

	public Calendar getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Calendar deathDate) {
		this.deathDate = deathDate;
	}

	public Calendar getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Calendar cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	
}
