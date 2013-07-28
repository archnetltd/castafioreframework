package org.castafiore.easyui.buttons;

import org.castafiore.ui.engine.ClientProxy;

public class MenuButton extends LinkButton{

	public MenuButton(String name, String label, Menu menu) {
		super(name, label);
		super.setPlain(true);
		menu.setAttribute("displaylater", "true");
		addChild(menu);
		options.put("menu", "#" + menu.getId());
	}
	
	
	public MenuButton setDuration(int duration){
		options.put("duration", duration);
		return this;
	}


	@Override
	public void onReady(ClientProxy proxy) {
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/icon.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js",
				proxy.clone().addMethod("menubutton", options));
	}
	

}
