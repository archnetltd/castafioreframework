package org.castafiore.easyui.layout;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class RegionPanel extends EXContainer{
	private String region;

	public RegionPanel(String name, String title, String region) {
		super(name, "div");
		setTitle(title).setRegion(region);
		
	}
	
	private void setOption(String key, Object val){
		String o = getAttribute("data-options");
		Map<String, String> p = new LinkedHashMap<String, String>();
		if(StringUtil.isNotEmpty(o)){
			String[] parts = StringUtil.split(o, ",");
			for(String part : parts){
				String[] as = StringUtil.split(part, ":");
				if(as != null && as.length == 2){
					p.put(as[0], as[1]);
				}
			}
		}
		
		
		if(val instanceof String){
			p.put(key, "'" + val + "'");
		}else{
			p.put(key, val.toString());
		}
		
		
		Iterator<String> keys = p.keySet().iterator();
		StringBuilder b = new StringBuilder();
		while(keys.hasNext()){
			String k = keys.next();
			String v = p.get(k);
			b.append(k).append(":").append(v);
			if(keys.hasNext()){
				b.append(",");
			}
		}
		setAttribute("data-options", b.toString());
	}
	public RegionPanel setRegion(String region){
		setOption("region", region);
		this.region = region;
		return this;
	}
	
	public String getRegion(){
		return region;
	}
	
	public RegionPanel setTitle(String title){
		setOption("title", title);
		return this;
	}
	
	public RegionPanel showBorder(boolean border){
		setOption("border", border);
		return this;
	}
	
	public RegionPanel setSplit(boolean split){
		setOption("split", split);
		return this;
	}
	
	public RegionPanel setIconCls(String icon){
		setOption("iconCls", icon);
		return this;
	}

}
