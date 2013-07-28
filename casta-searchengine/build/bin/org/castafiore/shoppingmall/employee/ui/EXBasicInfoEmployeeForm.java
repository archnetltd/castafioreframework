package org.castafiore.shoppingmall.employee.ui;

import java.util.Calendar;
import java.util.Date;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;

public class EXBasicInfoEmployeeForm extends EXFieldSet{

	public EXBasicInfoEmployeeForm(String name, Employee e) {
		super(name, "Basic employee info", false);
		addField("Title :", new EXAutoComplete("title", e.getTitle()).addInDictionary("Mr.", "Mrs.", "Miss"));
		addField("First name :", new EXInput("firstName", e.getFirstName()));
		addField("Last name :", new EXInput("lastName", e.getLastName()));
		addField("Email :", new EXInput("email", e.getEmail()));
		addField("Phone :", new EXInput("phone", e.getPhone()));
		addField("Mobile :", new EXInput("mobile", e.getMobile()));
		addField("Fax :", new EXInput("fax", e.getFax()));
		addField("Marital Status :", new EXAutoComplete("maritalStatus", e.getMaritalStatus()).addInDictionary("Single", "Married", "Divorced"));
		addField("Gender :", new EXAutoComplete("gender", e.getGender()).addInDictionary("Male", "Female"));
		addField("Date of Birth :", new EXDatePicker("dob", e.getDateOfBirth().getTime()));
		addField("NIC :", new EXInput("nic", e.getNic()));
	}
	
	public void setEmployee(Employee e){
		getField("title").setValue(e.getTitle());
		getField("firstName").setValue(e.getFirstName());
		getField("lastName").setValue(e.getLastName());
		getField("email").setValue(e.getEmail());
		getField("phone").setValue(e.getPhone());
		getField("mobile").setValue(e.getMobile());
		getField("fax").setValue(e.getFax());
		getField("maritalStatus").setValue(e.getMaritalStatus());
		getField("gender").setValue(e.getGender());
		getField("nic").setValue(e.getNic());
		getField("dob").setValue(e.getDateOfBirth().getTime());
		setAttribute("path", e.getAbsolutePath());
	}
	
	public void fillEmployee(Employee e){
		e.setTitle(getField("title").getValue().toString());
		e.setFirstName(getField("firstName").getValue().toString());
		e.setLastName(getField("lastName").getValue().toString());
		e.setEmail(getField("email").getValue().toString());
		e.setPhone(getField("phone").getValue().toString());
		e.setMobile(getField("mobile").getValue().toString());
		e.setFax(getField("fax").getValue().toString());
		e.setMaritalStatus(getField("maritalStatus").getValue().toString());
		
		e.setGender(getField("gender").getValue().toString());
		e.setNic(getField("nic").getValue().toString());
		Calendar dob = Calendar.getInstance();
		dob.setTime((Date)getField("dob").getValue());
		
		e.setDateOfBirth(dob);
		
	}

}
