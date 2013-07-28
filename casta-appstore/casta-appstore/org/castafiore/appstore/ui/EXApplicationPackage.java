package org.castafiore.appstore.ui;

import org.castafiore.appstore.AppPackage;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXApplicationPackage extends EXXHTMLFragment{

	public EXApplicationPackage(String name) {
		super(name, "templates/EXApplicationPackage.xhtml");
		
	}
	
	
	public void setApplication(AppPackage application){

	}

}
