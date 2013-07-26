package org.castafiore.shoppingmall.employee.ui;

import java.math.BigDecimal;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;

public class EXFinanceInfoEmployeeForm extends EXFieldSet{

	public EXFinanceInfoEmployeeForm(String name, Employee e) {
		super(name, "Finance info", false);
		addField("TAN :", new EXInput("tan", e.getTan()));
		addField("SSNumber :", new EXInput("ssNumber", e.getSSNumber()));
		
		addField("Basic Salary :", new EXInput("basicSalary",  e.getBasicSalary().toPlainString()));
		addField("Travelling :", new EXInput("travelling", e.getProperty("travelling")));
		addField("EDF :", new EXInput("edf",e.getProperty("edf")));
	}
	
	
	public void fillEmployee(Employee e){
		e.setTan(getField("tan").getValue().toString());
		e.setSSNumber(getField("ssNumber").getValue().toString());
		e.setBasicSalary(new BigDecimal(getField("basicSalary").getValue().toString()));
		e.setProperty("travelling", getField("travelling").getValue().toString());
		e.setProperty("edf", getField("edf").getValue().toString());
		
	}

}
