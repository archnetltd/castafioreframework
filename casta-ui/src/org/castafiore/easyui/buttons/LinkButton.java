package org.castafiore.easyui.buttons;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.js.JMap;

public class LinkButton extends EXContainer implements Button{

	protected JMap options = new JMap();
	public LinkButton(String name, String label) {
		super(name, "a");
		setAttribute("href", "#");
		setText("<span>" + label + "</span>");
		
	}
	
	public LinkButton setLabel(String label){
		setText("<span>" + label + "</span>");
		return this;
	}
	
	public LinkButton setDisabled(boolean dis){
		options.put("disabled", dis);
		return this;
	}
	
	public LinkButton setPlain(boolean plain){
		options.put("plain", plain);
		return this;
	}
	
	public LinkButton setIconCls(String icon){
		options.put("iconCls", icon);
		return this;
	}
	
	public LinkButton setIconAlign(String pos){
		options.put("iconAlign", pos);
		return this;
		
	}
	
	public void onReady(ClientProxy proxy){
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/icon.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js",
				proxy.clone().addMethod("linkbutton", options));
	}

}
