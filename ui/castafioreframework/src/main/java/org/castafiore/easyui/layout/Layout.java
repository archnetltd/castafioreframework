package org.castafiore.easyui.layout;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class Layout extends EXContainer {

	private JMap options = new JMap();
	
	public Layout(String name) {
		super(name, "div");
	}
	
	public Layout addRegion(RegionPanel panel){
		addChild(panel);
		return this;
	}
	
	public RegionPanel getRegion(String region){
		for(Container c : getChildren()){
			if(c instanceof RegionPanel){
				if(((RegionPanel) c).getRegion().equals(region)){
					return (RegionPanel)c;
				}
			}
		}
		return null;
	}


	public Layout setFit(boolean fit){
		options.put("fit", fit);
		return this;
	}
	
	
	public void onReady(ClientProxy proxy){
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js", proxy.clone().addMethod("layout", options));
	}

}
