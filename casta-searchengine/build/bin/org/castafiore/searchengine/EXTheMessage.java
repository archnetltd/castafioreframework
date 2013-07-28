package org.castafiore.searchengine;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;

public class EXTheMessage extends EXContainer{

	public EXTheMessage(String name) {
		super(name, "div");
		addClass("themessage").addClass("error");
	}
	
	
	public void onReady(ClientProxy proxy){
		proxy.fadeIn(100);
		proxy.addEvent("mouseover", proxy.clone().fadeOut(100));
	}
	public void showMessage(String message){
		setText(message);
		
	}

}
