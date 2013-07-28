package org.castafiore.finance;

import org.castafiore.finance.form.EXOSForm;
import org.castafiore.finance.ui.EXOSApplication;
import org.castafiore.finance.ui.EXOSExplorerTree;
import org.castafiore.finance.ui.EXOSHeader;
import org.castafiore.finance.worksheet.EXOSWorksheet;
import org.castafiore.finance.worksheet.EXOSWorksheetScrollContainer;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class Finance extends EXApplication {

	public Finance() {
		super("finance");
		addClass("container");
		//addChild(new EXOSHeader());
		//addChild(new EXContainer("app-hider", "div").addClass("app-hider").addClass("ui-icon").addClass("clear"));
		
		//addChild(new EXOSApplication("Cashbook"));
		
		//getOSApplication().getBody().getLeftColumn().addChild(new EXOSExplorerTree("tree"));
		//getOSApplication().getBody().getWorkspace().addChild(new EXOSWorksheetScrollContainer("", new EXOSWorksheet("")) );
		
		
//		EXOSForm form = new EXOSForm("", "This form can be used");
//		form.addField("Username :", new EXInput(""));
//		form.addField("Username :", new EXInput(""));
//		form.addField("Username :", new EXInput(""));
//		form.addField("Username :", new EXInput(""));
//		form.addField("Username :", new EXInput(""));
//		form.addButton(new EXButton("", "kdfsd"));
//		getOSApplication().getBody().getWorkspace().addChild(form);
		
		addChild(new EXOSWorksheetScrollContainer("", new EXOSWorksheet("")));
	}
	
	public EXOSApplication getOSApplication(){
		return getDescendentOfType(EXOSApplication.class);
	}
	
	

}
