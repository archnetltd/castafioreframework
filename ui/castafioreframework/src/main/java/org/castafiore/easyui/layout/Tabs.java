package org.castafiore.easyui.layout;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tab.TabPanel;
import org.castafiore.ui.ex.tab.TabRenderer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;

public class Tabs extends EXContainer implements TabPanel{

	private JMap options = new JMap();
	
	private TabModel model;
	
	public Tabs(String name) {
		super(name, "div");
		addChild(new EXContainer("tools", "div"));
		options.put("tools", "#" + getChild("tools").getId());
	}
	
	private void recreate(){
		for(int i =1; i < getChildren().size();i++){
			Container c = getChildByIndex(i);
			c.remove();
		}
		
		for(int i =0; i < model.size();i++){
			Container content = model.getTabContentAt(this, i);
			int selected = model.getSelectedTab();
			String title = model.getTabLabelAt(this, i, i==selected);
			boolean closable = model.isClosable(i);
			String iconCls = model.getIconCls(i);
			StringBuilder opts = new StringBuilder();
			if(StringUtil.isNotEmpty(iconCls)){
				opts.append("iconCls:'").append(iconCls).append("'");
			}
			
			if(closable){
				if(opts.length() > 0){
					opts.append(",");
				}
				opts.append("closable").append(closable);
			}
			content.setAttribute("title", title);
			if(opts.length() >0){
				content.setAttribute("data-options", opts.toString());
			}
			addChild(content);
			
			
		}
	}
	
	public Tabs setModel(TabModel model){
		this.model = model;
		recreate();
		return this;
	}
	
	public Tabs addTool(String name, String iconCls, Event event){
		Container tool=new EXContainer(name, "a").setAttribute("href", "#").setAttribute("iconCls",iconCls).setAttribute("plain", "true").addClass("easyui-linkbutton");
		getChild("tools").addChild(tool);
		return this;
	}
	
	public Tabs setPlain(boolean plain){
		options.put("plain", plain);
		return this;
	}
	
	public Tabs setFit(boolean fit){
		options.put("fit", fit);
		return this;
	}
	
	
	public Tabs showBorder(boolean border){
		options.put("border", border);
		return this;
	}
	
	public Tabs setScrollIncrement(int inc){
		options.put("scrollIncrement", inc);
		return this;
	}
	
	
	public Tabs setScrollDuration(int duration){
		options.put("scrollDuration", duration);
		return this;
	}
	
	public Tabs setToolPosition(String position){
		options.put("toolPosition", position);
		return this;
	}
	
	public Tabs setTabPosition(String position){
		options.put("tabPosition", position);
		return this;
	}
	
	public Tabs setHeaderWidth(int width){
		options.put("headerWidth", width);
		return this;
	}
	
	
	
	public void onReady(ClientProxy proxy){
		proxy.getDescendentByName("tools").appendTo(new ClientProxy("body"));
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js", proxy.clone().addMethod("tabs", options));
	}

	@Override
	public org.castafiore.ui.ex.tab.TabModel getModel() {
		return model;
	}

	@Override
	public TabRenderer getTabRenderer() {
		return null;
	}
	
	

}
