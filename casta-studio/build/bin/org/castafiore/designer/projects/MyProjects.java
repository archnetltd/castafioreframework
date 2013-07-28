package org.castafiore.designer.projects;

import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;

public class MyProjects extends EXPanel{

	public MyProjects(String name) {
		super(name, "My Projects");
		MyProjectsModel model = new MyProjectsModel();
		EXTable table = new EXTable("", model, model);
		setBody(table);
	}

}
