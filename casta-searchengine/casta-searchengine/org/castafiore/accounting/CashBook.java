package org.castafiore.accounting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;


import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;

@Entity
public class CashBook extends Article{
	
	public List<Account> getAccountsConfigured(){
		if(StringUtil.isNotEmpty(getAbsolutePath())){
			List l = getSession().executeQuery(new QueryParameters().setEntity(Account.class).addSearchDir(getAbsolutePath()));
			return l;
			
		}else{
			return new ArrayList<Account>();
		}
		//return getFiles(Account.class).toList();
	}
	
	public Account createAccount(String name){
		
		Directory accounts = getFile("accounts", Directory.class); 
		if(accounts == null){
			accounts = createFile("accounts", Directory.class);
		}
		return accounts.createFile(name, Account.class);
	}
	
	
	public CashBookEntry createEntry(String name){
		Directory entries = getFile("entries", Directory.class);
		boolean n=false;
		if(entries == null){
			entries = createFile("entries", Directory.class);
			n=true;
			
		}
		
		Directory year = entries.getFile(Calendar.getInstance().get(Calendar.YEAR) + "", Directory.class);
		
		if(year == null){
			year = entries.createFile(Calendar.getInstance().get(Calendar.YEAR) + "", Directory.class);
		}
		
		Directory date = year.getFile(new SimpleDateFormat("MMdd").format(Calendar.getInstance().getTime()), Directory.class);
		if(date == null){
			date = year.createFile(new SimpleDateFormat("MMdd").format(Calendar.getInstance().getTime()), Directory.class);
		}
			
		
		return date.createFile(name, CashBookEntry.class);
		//return createFile(name, CashBookEntry.class);
	}

}
