package org.castafiore.shoppingmall.imports;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.castafiore.accounting.CashBook;
import org.castafiore.accounting.CashBookEntry;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

public class CashBookEntryImporterTemplate extends AbstractExcelImporterTemplate{

	private CashBook cashBook ;
	
	private String[] months = new String[]{ "Apr","May","June","July","August","September"};
	
	private int startCode = 100;
	@Override
	public void doImportInstance(Map<String, String> data) {
		StatelessSession session = SpringUtil.getBeanOfType(Dao.class).getHibernateTemplate().getSessionFactory().openStatelessSession();
		
		if(data.containsKey("code")){
			String code = data.get("code");
			
			for(String m : months){
				
				if(data.containsKey(m)){
					CashBookEntry entry = cashBook.createEntry(new Date().getTime() + StringUtil.nextString(10));
					startCode = startCode + 1;
					String value = data.get(m);
					entry.setCode(startCode + "");
					entry.setTitle("Installment " + code + " " + m + " 2011");
					entry.setAccountCode(data.get("code"));
					entry.setSummary(entry.getTitle());
					entry.setTotal(new BigDecimal(value));
					entry.setPaymentMethod("Standing Order");
					session.insert(entry);
				}
			}
		}

	}

	@Override
	protected void init() {
		super.init();
		if(cashBook == null)
			cashBook = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		
		
	}

	@Override
	public void flush() {
		Session s = SpringUtil.getBeanOfType(Dao.class).getSession();
		s.flush();
		s.clear();
		System.gc();
		
	}

	@Override
	public boolean hasNextSource() {
		if( super.hasNextSource()){
			if(_currentRowNum == 1425){
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> transform(Row instance) {
		Map<String, String> result = new HashMap<String, String>();
		String code = instance.getCell(2).getStringCellValue();
		if(StringUtil.isNotEmpty(code)){
			result.put("code", code);
			int row =instance.getRowNum();
			result.put("row", (row + 100) + "");
			for(int i = 0; i < months.length;i++){
				try{
				Double value = instance.getCell(6 + i).getNumericCellValue();
				result.put(months[i], value.toString());
				}catch(Exception e){
					
				}
			}
		}
		return result;
	}

}
