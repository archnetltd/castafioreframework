package org.castafiore.shoppingmall.employee;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;

import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.wfs.types.Directory;
import org.hibernate.annotations.Type;
@Entity()
public class Employee extends MerchantSubscription{
	
	
	
	@Type(type="text")
	private String tan;
	
	@Type(type="text")
	private String bankAccountNumber;
	
	@Type(type="text")
	private String bank;
	
	@Type(type="text")
	private String SSNumber;
	
	
	private BigDecimal basicSalary;
	
	
	@Type(type="text")
	private String job;
	
	private String department;


	public String getTan() {
		return tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSSNumber() {
		return SSNumber;
	}

	public void setSSNumber(String sSNumber) {
		SSNumber = sSNumber;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	public TimesheetEntry saveOrUpdateEntry(Calendar date, Calendar from, Calendar to, Calendar prevFrom, Calendar prevTo, String project){
		String dirName = new SimpleDateFormat("yyyyMM").format(date.getTime());
		Directory month = getFile(dirName, Directory.class);
		if(month == null){
			month = createFile(dirName, Directory.class);
		}
		String name = new SimpleDateFormat("yyyyMMddHHmm").format(from.getTime()) + "_" + project;
		TimesheetEntry entry = month.getFile(name, TimesheetEntry.class);
		if(entry == null){
			entry = month.createFile(name, TimesheetEntry.class);
		}
		
		entry.setDate(date);
		entry.setPrevisionalStartTime(prevFrom);
		entry.setPrevisionalEndTime(prevTo);
		entry.setStartTime(from);
		entry.setEndTime(to);
		
		month.save();
		return entry;
	}
	

}
