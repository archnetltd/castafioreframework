package org.castafiore.reconcilliation;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.StringUtil;

public class ReconcilationDTO {
	
	private String refNumber = "";
	private String name = "";
	private String amount = "";
	private String comment= "";
	private String accountNumber = "";
	private String date = "";
	private String bank = "";
	private boolean ok;
	
	private String originalLine = "";
	
	private String status = "New";
	
	
	
	
	public ReconcilationDTO(String originalLine) {
		super();
		this.originalLine = originalLine;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public boolean isOk() {
		if(status.equalsIgnoreCase("Automatic FS") || status.equalsIgnoreCase("Automatic") || status.equalsIgnoreCase("Manual") || status.equalsIgnoreCase("Matched")){
			ok = true;
		}else
			ok=false; 
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if(status.equalsIgnoreCase("Automatic FS") || status.equalsIgnoreCase("Automatic") || status.equalsIgnoreCase("Manual") || status.equalsIgnoreCase("Matched")){
			ok = true;
		}else
			ok=false; 
	}
	public String getOriginalLine() {
		return originalLine;
	}
	public void setOriginalLine(String originalLine) {
		this.originalLine = originalLine;
	}
	
	public static ReconcilationDTO toDto(String lines){
		String[] ass = StringUtils.splitByWholeSeparator(lines, "|->");
		
		String line = ass[0];
		
		String[] as = StringUtils.split(line, "|");
		
		StringBuilder b = new StringBuilder();
		
		
		for(int i =8; i < as.length;i++){
			if(b.length() > 0){
				b.append("|");
			}
			b.append(as[i]);
		}
		ReconcilationDTO dto = new ReconcilationDTO(b.toString());
		dto.setRefNumber(as[0]);
		dto.setName(as[1]);
		dto.setAmount(as[2]);
		dto.setComment(as[3]);
		dto.setAccountNumber(as[4]);
		dto.setDate(as[5]);
		dto.setBank(as[6]);
		dto.setOk(Boolean.parseBoolean(as[7]));
		
		if(ass.length >1){
			dto.status = ass[1];
		}else
			dto.status = "New";
		return dto;
		
	}
	public String toString(){
		StringBuilder b = new StringBuilder();
		if(!StringUtil.isNotEmpty(refNumber)){
			refNumber = "??";
		}
		
		if(!StringUtil.isNotEmpty(name)){
			name = "??";
		}
		
		if(!StringUtil.isNotEmpty(bank)){
			bank = "??";
		}
		
		if(!StringUtil.isNotEmpty(this.amount)){
			amount = "??";
		}
		
		if(!StringUtil.isNotEmpty(this.comment)){
			comment = "??";
		}
		
		if(!StringUtil.isNotEmpty(this.date)){
			date = "??";
		}
		
		if(!StringUtil.isNotEmpty(this.originalLine)){
			originalLine = "??";
		}else{
			String[] pp = StringUtils.splitByWholeSeparator(originalLine, "|->");
			originalLine = pp[0];
		}
		
		
		
		b.append(refNumber).append( "|").append(this.name).append("|").append(amount).append("|").append(comment).append("|").append(accountNumber).append("|");
		b.append(date).append("|").append(bank).append("|").append(ok).append("|").append(originalLine);
		if(status != null){
			b.append("|=>").append(status);
		}
		return b.toString();
	}

}
