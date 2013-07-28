package org.castafiore.shoppingmall.checkout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Value;
import org.hibernate.annotations.Type;

@Entity
public class BillingInformation extends Directory{
	
	@Type(type="text")
	private String username;
	
	@Type(type="text")
	private String firstName;
	
	@Type(type="text")
	private String lastName;
	
	@Type(type="text")
	private String company;
	
	private String phone;
	
	private String mobile;
	
	private String gender;
	
	private String title;
	
	private String maritalStatus;
	
	
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public static Map<String,String> buildMap(String s){
		
		
		String[] sections = StringUtils.splitByWholeSeparator(s, "-:;:-");
		Map<String,String> kvs = new HashMap<String, String>();
		for(String ss : sections){
			String[] kv = StringUtils.split(ss, ":");
			if(kv != null && kv.length == 2){
				if(!"null".equals(kv[1]))
					kvs.put(kv[0], kv[1]);
				else
					kvs.put(kv[0], "");
			}
		}
		
		return kvs;
	}

	public static String buildString(Map<String,String> kvs){
		Iterator<String> keys = kvs.keySet().iterator();
		StringBuilder b = new StringBuilder();
		while(keys.hasNext()){
			String k = keys.next();
			String v = kvs.get(k);
			if(b.length() > 0)
				b.append("-:;:-");
			
			b.append(k).append(":").append(v);
		}
		
		return b.toString();
	}
	
	public Map<String, String> getOtherProperties(){
		Value val = getFile("applicationForm", Value.class);
		if(val != null){
			String s = val.getString();
			
			return buildMap(s);
		}
		return new HashMap<String, String>();
	}
	public BillingInformation setOtherProperty(String key,String value){
		Value val = getFile("applicationForm", Value.class);
		if(val == null)
			val = createFile("applicationForm", Value.class);
		
		String s = val.getString();
		
		if(s==null){
			s="";
		}
		
		Map<String,String> kvs = buildMap(s);
		
		kvs.put(key, value);
		
		val.setString(buildString(kvs));
		
		return this;
	}
	

}
