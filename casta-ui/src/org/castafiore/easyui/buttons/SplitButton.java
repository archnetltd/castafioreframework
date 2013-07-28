package org.castafiore.easyui.buttons;

import org.castafiore.ui.engine.ClientProxy;

public class SplitButton extends MenuButton{

	public SplitButton(String name, String label, Menu menu) {
		super(name, label, menu);
	}

	@Override
	public void onReady(ClientProxy proxy) {
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/icon.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js",
				proxy.clone().addMethod("splitbutton", options));
	}
	
	
}
