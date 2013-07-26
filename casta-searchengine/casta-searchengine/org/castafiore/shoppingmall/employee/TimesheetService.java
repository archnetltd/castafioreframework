package org.castafiore.shoppingmall.employee;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.shoppingmall.checkout.BillingInformation;

public interface TimesheetService extends Serializable{
	
	public Workbook getDetailView(Calendar from, Calendar to);
	
	public void registerEmployee(BillingInformation bi);
	
	public List<Employee> searchEmployees(String notation);

}
