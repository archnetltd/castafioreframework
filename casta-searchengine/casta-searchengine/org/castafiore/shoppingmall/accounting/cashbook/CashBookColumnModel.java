package org.castafiore.shoppingmall.accounting.cashbook;

import org.castafiore.finance.worksheet.OSWorksheetColumnModel;
import org.castafiore.finance.worksheet.OSWorksheetTypes;
import org.castafiore.finance.worksheet.WorksheetCellSpan;

public class CashBookColumnModel implements OSWorksheetColumnModel{

	
	private final static String[]LABELS = new String[]{"Id", "Date", "Account", "Amount", "Description"};
	
	private final static OSWorksheetTypes[] TYPES = new OSWorksheetTypes[]{OSWorksheetTypes.NUMBER, OSWorksheetTypes.DATE, OSWorksheetTypes.STRING, OSWorksheetTypes.CURRENCY, OSWorksheetTypes.TEXT};
	
	@Override
	public OSWorksheetTypes getDefaultType(int index) {
		return TYPES[index];
	}

	@Override
	public int getSize() {
		return LABELS.length;
	}

	@Override
	public WorksheetCellSpan getSpan(int index) {
		if(index == 4){
			return WorksheetCellSpan.span_5;
		}else if(index == 0){
			return WorksheetCellSpan.span_1;
		}else
			return WorksheetCellSpan.span_2;
	}

	@Override
	public String getTitle(int index) {
		return LABELS[index];
	}

}
