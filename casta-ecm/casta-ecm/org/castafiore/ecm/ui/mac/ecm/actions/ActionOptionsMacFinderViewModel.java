package org.castafiore.ecm.ui.mac.ecm.actions;

import org.castafiore.ecm.ui.mac.ecm.AbstractFileManipOptionsMacFinderViewModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;

public class ActionOptionsMacFinderViewModel extends AbstractFileManipOptionsMacFinderViewModel{

	
	private String filePath ;

	public ActionOptionsMacFinderViewModel(String filePath) {
		super();
		addLabel("List Actions", "ui-icon-carat-2-e-w", "listActions").addLabel("Add Action", "ui-icon-plusthick", "addAction");
		this.filePath = filePath;
	}

	public EXMacFinderColumn listActions(Container caller){
		return null;
	}
	
	public EXMacFinderColumn addAction(Container caller){
		return null;
	}
}
