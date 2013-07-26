package org.castafiore.shoppingmall.accounting.cashbook;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.finance.worksheet.OSWorksheetCell;
import org.castafiore.finance.worksheet.OSWorksheetCellRenderer;
import org.castafiore.finance.worksheet.inputs.EXOSCurrencyInput;
import org.castafiore.finance.worksheet.inputs.EXOSDateStringInput;
import org.castafiore.finance.worksheet.inputs.EXOSLookupInput;
import org.castafiore.finance.worksheet.inputs.EXOSReadOnlyInput;
import org.castafiore.finance.worksheet.inputs.EXOSTextInput;

public class CashBookCellRenderer implements OSWorksheetCellRenderer{

	@Override
	public OSWorksheetCell getCell(String title, int colIndex, int rowIndex,Object value) {
		if(colIndex == 0){
			return new EXOSReadOnlyInput(title, value.toString());
		}else if(colIndex == 1){
			return new EXOSDateStringInput(title);
		}else if(colIndex == 2){
			return new EXOSLookupInput(title,getAccountTypes());
		}else if(colIndex == 3){
			return new EXOSCurrencyInput(title, value.toString());
		}else{
			return new EXOSTextInput(title, value.toString());
		}
		
	}
	
	
	public static List<Object> getAccountTypes(){
		
		List<Object> result = new ArrayList<Object>();
		result.add("Bank account");
		result.add("Petty cash");
		result.add("Load payment");
		result.add("Travelling");
		result.add("Sales");
		result.add("Misc");
		return result;
	}

}
