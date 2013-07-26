package org.racingtips.ui.admin;

import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXImportForHorse extends EXDynaformPanel{

	public EXImportForHorse(String name) {
		super(name, "Import item");
		addField("Upload Page", new EXUpload("upload"));
		
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("cancel", "Cancel"));
		
	}
	
	
	

}
