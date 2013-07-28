package org.castafiore.shoppingmall.accounting;

import org.castafiore.finance.worksheet.EXOSWorksheet;
import org.castafiore.finance.worksheet.EXOSWorksheetScrollContainer;
import org.castafiore.shoppingmall.accounting.cashbook.CashBookCellRenderer;
import org.castafiore.shoppingmall.accounting.cashbook.CashBookColumnModel;
import org.castafiore.shoppingmall.accounting.cashbook.CashBookColumnRenderer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXAccounting extends EXXHTMLFragment{
	
	public EXAccounting(){
		super("EXAccounting", "templates/EXAccounting.xhtml");
		addChild(new EXOSWorksheetScrollContainer("worksheet", new EXOSWorksheet("")));
		showCashBook();
	}
	
	
	public void showCashBook(){
		EXOSWorksheet worksheet = getDescendentOfType(EXOSWorksheet.class);
		
		worksheet.setColumnModel(new CashBookColumnModel());
		worksheet.setCellRenderer(new CashBookCellRenderer());
		worksheet.setColumnRenderer(new CashBookColumnRenderer());
		worksheet.build();
		//worksheet.setColumnRenderer(columnRenderer)
	}

}
