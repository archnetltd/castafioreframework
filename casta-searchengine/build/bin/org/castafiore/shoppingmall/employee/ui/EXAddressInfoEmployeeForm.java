package org.castafiore.shoppingmall.employee.ui;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;

public class EXAddressInfoEmployeeForm extends EXFieldSet{

	public EXAddressInfoEmployeeForm(String name, Employee e) {
		super(name, "Address", false);
		addField("Line 1 :", new EXInput("line1", e.getAddressLine1()));
		addField("Line 2 :", new EXInput("line2", e.getAddressLine2()));
		addField("PO Box :", new EXInput("postalcode", e.getZipPostalCode()));
		addField("City :", new EXInput("city", e.getCity()));
		addField("Country :", new EXInput("country", e.getCountry()));
	}
	
	public void fillEmployee(Employee e){
		e.setAddressLine1(getField("line1").getValue().toString());
		e.setAddressLine2(getField("line2").getValue().toString());
		e.setZipPostalCode(getField("postalcode").getValue().toString());
		e.setCity(getField("city").getValue().toString());
		e.setCountry(getField("country").getValue().toString());
	}

}
