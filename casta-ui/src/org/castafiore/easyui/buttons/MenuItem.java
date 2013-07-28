package org.castafiore.easyui.buttons;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class MenuItem extends EXContainer{

	public MenuItem(String name, String label) {
		super(name, "div");
		setLabel(label);
	}
	
	public MenuItem setLabel(String label){
		setText("<span>" + label + "</span>");
		return this;
	}
	
	public MenuItem setDisabled(boolean dis){
		addDataOption("disabled", dis + "");
		return this;
	}
	
	public MenuItem setIconCls(String icon){
		addDataOption("iconCls", icon);
		return this;
	}
	
	public MenuItem setIconAlign(String pos){
		addDataOption("iconAlign", pos);
		return this;
		
	}
	
	
	protected void addDataOption(String key, String value){
		String opts = getAttribute("data-options");
		String[] parts = StringUtil.split(opts, ",");
		Map<String,String> data = new LinkedHashMap<String,String>();
		for(String part : parts){
			String[] sects = StringUtil.split(part, ":");
			if(sects != null && sects.length > 2){
				data.put(sects[0], sects[1]);
			}
		}
		
		data.put(key, value);
		
		Iterator<String> keys = data.keySet().iterator();
		StringBuilder b = new StringBuilder();
		while(keys.hasNext()){
			String k = keys.next();
			String v = data.get(k);
			b.append(k).append(":").append(v);
			if(keys.hasNext()){
				b.append(",");
			}
		}
		
		setAttribute("data-options", b.toString());
		
	}
	public MenuItem addSubMenu(Menu menu){
		addChild(menu.setAttribute("displaylater", "true"));
		return this;
	}

}
