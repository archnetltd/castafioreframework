package org.castafiore.mongo;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Organization extends BasicDBObject implements DBObject{
	
	private MongoSession s_;
	
	public Organization() {
		super();
	}


	public Organization(String name){
		put("name", name);
		put("_id", name);
	}

	public MongoSession getSession(){
		if(s_ == null){
			s_ = new MongoSession(getName());
		}
		return s_;
	}
	
	public void save(){
		Mongo.o.save(this);
	}
	
	public String getAddressLine1() {
		return getString("addressLine1");
		
	}

	public void setAddressLine1(String addressLine1) {
		put("addressLine1", addressLine1);
	}

	public String getAddressLine2() {
		return getString("addressLine2");
		
	}

	public void setAddressLine2(String addressLine2) {
		put("addressLine2", addressLine2);
	}

	public String getAddressLine3() {
		return getString("addressLine3");
	}

	public void setAddressLine3(String addressLine3) {
		put("addressLine3", addressLine3);
	}

	public String getCity() {
		return getString("city");
	}

	public void setCity(String city) {
		put("city", city);
	}

	public String getZipCode() {
		return getString("zipCode");
	}

	public void setZipCode(String zipCode) {
		put("zipCode", zipCode);
	}

	public String getCountry() {
		return getString("country");
	}

	public void setCountry(String country) {
		put("country", country);
	}

	public String getCurrency() {
		return getString("currency");
	}

	public void setCurrency(String currency) {
		put("currency", currency);
	}

	public String getCategory() {
		return getString("category");
	}

	public void setCategory(String category) {
		put("category", category);
	}

	public String getLogo() {
		return getString("logo");
	}

	public void setLogo(String logo) {
		put("logo", logo);
	}

	public String getWebsite() {
		return getString("website");
	}

	public void setWebsite(String website) {
		put("website", website);
	}

	public String getEmail() {
		return getString("email");
	}

	public void setEmail(String email) {
		put("email", email);
	}

	public String getPhone() {
		return getString("phone");
	}

	public void setPhone(String phone) {
		put("phone", phone);
	}

	public String getMobile() {
		return getString("mobile");
	}

	public void setMobile(String mobile) {
		put("mobile", mobile);
	}

	public String getFax() {
		return getString("fax");
	}

	public void setFax(String fax) {
		put("fax", fax);
	}

	public String getBrn() {
		return getString("brn");
	}

	public void setBrn(String brn) {
		put("brn", brn);
	}

	public String getVatRegNumber() {
		return getString("vatRegNumber");
	}

	public void setVatRegNumber(String vatRegNumber) {
		put("vatRegNumber", vatRegNumber);
	}

	public String getName(){
		return getString("name");
	}
	
	public Date getDateCreated(){
		return getDate("dateCreated");
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	public void setTitle(String title){
		put("title", title);
	}
	
	public String getSummary(){
		return getString("summary");
	}
	
	public void setSummary(String summary){
		put("summary", summary);
	}
	
	public String getDetail(){
		return getString("detail");
	}
	
	public void setDetail(String detail){
		put("detail", detail);
	}

}
