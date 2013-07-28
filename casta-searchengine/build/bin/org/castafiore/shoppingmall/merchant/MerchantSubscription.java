package org.castafiore.shoppingmall.merchant;

import java.util.Calendar;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.annotations.Type;

@Entity
public class MerchantSubscription extends Directory{
	
	public MerchantSubscription(){
		super();
	}

	@Type(type="text")
	private String subscriber;
	
	@Type(type="text")
	private String merchantUsername;
	
	@Type(type="text")
	private String title;
	
	
	private String gender;
	
	private String maritalStatus;
	
	@Type(type="text")
	private String firstName;
	
	@Type(type="text")
	private String lastName;
	
	@Type(type="text")
	private String company;
	
	
	private String phone;
	
	private String mobile;
	
	
	private String fax;
	
	private String email;
	
	@Type(type="text")
	private String addressLine1;
	
	@Type(type="text")
	private String addressLine2;
	
	@Type(type="text")
	private String city;
	
	@Type(type="text")
	private String country;
	
	
	private String zipPostalCode;
	
	@Type(type="text")
	private String nic;
	
	private Calendar dateOfBirth;
	
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	public String getMerchantUsername() {
		return merchantUsername;
	}

	public void setMerchantUsername(String merchantUsername) {
		this.merchantUsername = merchantUsername;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	
	
	
	
	
}
