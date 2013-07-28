package org.castafiore .accounting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.shoppingmall.checkout.BookEntry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Shortcut;
import org.hibernate.criterion.Restrictions;

@Entity
public class CashBookEntry extends BookEntry<Account>{
	
	private String accountCode;

	private Date dateOfTransaction = new Date();
	
	private String url;

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	
	
	public void setAccount(Account account){
		accountCode = account.getCode();
		Shortcut saccount = getFirstChild(Shortcut.class);
		if(saccount == null){
			saccount = createFile("account", Shortcut.class);
		}
		saccount.setReference(account.getAbsolutePath());
		
		
	}
	
	
	
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public Account getAccount(){
		Shortcut account = getFirstChild(Shortcut.class);
		
		if(account != null){
			
			return (Account)account.getReferencedFile();
		}else{
			QueryParameters param = new QueryParameters().setEntity(Account.class).addRestriction(Restrictions.eq("code", accountCode));
			
			List result = SpringUtil.getRepositoryService().executeQuery(param, Util.getRemoteUser());
			if(result.size() > 0){
				return (Account)result.get(0);
			}
			
		}
		return null;
		
		
	}
	

	public String getAccountCode() {
		return accountCode;
	}

	@Override
	public List<Account> getDetails() {
		
		Shortcut account = getFirstChild(Shortcut.class);
		List<Account> result = new ArrayList<Account>();
		if(account != null){
			
			result.add((Account)account.getReferencedFile());
		}
		
		return result;
		
		
	}
	
	
	

}
