package org.castafiore.shoppingmall.crm.newsletter;

import org.castafiore.shoppingmall.list.AbstractList;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;

public class EXNewsletterList extends AbstractList{
	public EXNewsletterList(String name) {
		super(name, "div");
		
		EXTable table = new EXTable("nn", new NewsletterListModel());
		table.setStyle("clear", "both").addClass("ex-content");
		table.setCellRenderer(new NewslettersCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("", table);
		addChild(pTable);
		
	}
	
	
	public void deleteSelected(){
		
	}

	
	
	
	public void showNewsletters(int state){
		getDescendentOfType(EXTable.class).setModel(new NewsletterListModel());
	}
}
