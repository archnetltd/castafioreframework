package org.castafiore.security.sessions;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;

public class EXLoggedSessionsTab extends EXContainer{

	public EXLoggedSessionsTab(String name) {
		super(name, "div");
		EXTable table = new EXTable("p", new SessionsManagerModel());
		table.setCellRenderer(new SessionsManagerCellRenderer());
		EXPagineableTable p = new EXPagineableTable("",table );
		addChild(p);
	}

}
