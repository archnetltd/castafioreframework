package org.castafiore.easyui.buttons;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class Menu extends EXContainer{
	
	private JMap options = new JMap();

	public Menu(String name) {
		super(name, "div");
	}
	
	public Menu addItem(MenuItem item){
		addChild(item);
		return this;
	}
	
	public Menu addSeparator(){
		addChild(new EXContainer("", "div").addClass("menu-sep"));
		return this;
	}
	
	
	public void onReady(ClientProxy proxy){
		if(!"true".equals(getAttribute("displaylater"))){
			proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
			proxy.getCSS("http://www.jeasyui.com/easyui/themes/icon.css");
			proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js",
					proxy.clone().addMethod("menu", options));
		}
	}

}
