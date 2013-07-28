package org.castafiore.reconcilliation.matching;

import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXMatchingPanel extends EXPanel{

	public EXMatchingPanel(String name)throws Exception {
		super(name, "Matching rules management");
		MatchingModel model = new MatchingModel();
		EXTable table = new EXTable("matchingtable", model, model);
		EXPagineableTable ptable = new EXPagineableTable("paginmatchningtable", table);
		
		setBody(ptable);
	}
	

}
