package org.castafiore.searchengine.back;

import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class OSBar extends EXContainer{

	public OSBar(String name) {
		super(name, "div");
		addClass("ex-os-bar");
		List<OSBarItem> items= SpringUtil.getBeanOfType(OSManager.class).getOSBarIcons();
		
		addChild(new EXContainer("", "label").setText("Powered by <a href=#>Castafiore framework</a>"));
		for(OSBarItem item : items){
			addChild(item);
		}
		
	}
	public void onReady(ClientProxy me){
		super.onReady(me);
		me.addEvent("mouseout", me.clone().addMethod("stop").animate(new JMap().put("height", "15px"), "10", null));
		me.addEvent("mouseover", me.clone().addMethod("stop").animate(new JMap().put("height", "75px"), "10", null));
		
	}
	

}
