package org.castafiore.shoppingmall.crm;

import java.math.BigDecimal;
import java.util.Date;

import org.castafiore.shoppingmall.reports.ReportsUtil;

public class OrderInfo {
	public String absolutePath;
	
	public String fsCode;
	
	
	public BigDecimal totalPayment;
	
	
	public BigDecimal installment;
	
	public Date dateCreated;
	
	public Date startInstallmentDate;
	
	public Date dateOfTransaction;
	
	public int totalInstallments=-1;
	
	public String firstName;
	
	public String lastName;
	
	public String title;
	
	public String phone;
	
	public String mobile;
	
	public String email;
	
	public int status=-1;
	
	public String company;
	
	public String fax;
	
	public String addressLine1;
	
	public String addressLine2;
	
	public String city;
	
	public String country;
	
	public String zipPostalCode;
	
	public BigDecimal total;
	
	public BigDecimal tax = new BigDecimal(15);
	
	public String paymentMethod;
	
	public int count;
	
	public String bankName;
	
	public String accountNumber;
	
	
	
	

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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipPostalCode() {
		return zipPostalCode;
	}

	public void setZipPostalCode(String zipPostalCode) {
		this.zipPostalCode = zipPostalCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getTotalPayment() {
		if(totalPayment == null){
			totalPayment = ReportsUtil.getTotalPaymentsMade(fsCode);
		}
		return totalPayment;
	}


	public int getTotalInstallments() {
		if(totalInstallments ==-1){
			totalInstallments = ReportsUtil.getTotalInstallments(dateOfTransaction);
		}
		return totalInstallments;
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	

	public Date getStartInstallmentDate() {
		if(dateOfTransaction != null && startInstallmentDate != null)
		{
			if(dateOfTransaction.before(startInstallmentDate)){
				return dateOfTransaction;
			}
		}
			
		if(startInstallmentDate == null)
			return getDateOfTransaction();
		return startInstallmentDate;
	}

	

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	
	public BigDecimal getSubTotal(){
		return total.multiply(tax.divide(new BigDecimal(100)));
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
