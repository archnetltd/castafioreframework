package org.castafiore.easyui.layout;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class Panel extends EXContainer implements org.castafiore.ui.ex.panel.Panel{
	private JMap options = new JMap();
	
	public Panel(String name) {
		super(name, "div");
		addChild(new EXContainer("tools", "div"));
		addChild(new EXContainer("bodyContainer", "div"));
		options.put("tools", "#" + getChildByIndex(0).getId());
	}
	
	

	@Override
	public org.castafiore.ui.ex.panel.Panel setTitle(String title) {
		options.put("title", title);
		return this;
	}

	@Override
	public org.castafiore.ui.ex.panel.Panel setBody(Container container) {
		getChildByIndex(1).getChildren().clear();
		getChildByIndex(1).setRendered(false).addChild(container);
		
		return this;
	}

	@Override
	public Container getBody() {
		return getChildByIndex(1).getChildByIndex(0);
	}

	@Override
	public org.castafiore.ui.ex.panel.Panel setShowHeader(boolean showHeader) {
		options.put("noheader", !showHeader);
		return this;
	}

	@Override
	public org.castafiore.ui.ex.panel.Panel setShowFooter(boolean display) {
		return this;
	}

	@Override
	public org.castafiore.ui.ex.panel.Panel setShowCloseButton(boolean b) {
		options.put("closable", b);
		return this;
	}
	
	public Panel setIconCls(String icon){
		options.put("iconCls", icon);
		return this;
	}
	
	public Panel setHeaderCls(String cls){
		options.put("headerCls", cls);
		return this;
	}
	
	public Panel setFit(boolean fit){
		options.put("fit", fit);
		return this;
	}
	
	public Panel showBorder(boolean show){
		options.put("border", show);
		return this;
	}
	
	public Panel setDoSize(boolean size){
		options.put("doSize", size);
		return this;
	}
	
	public Panel setCollapsible(boolean b){
		options.put("collapsible", b);
		return this;
	}
	
	public Panel setMinimizable(boolean b){
		options.put("minimizable", b);
		return this;
	}
	
	public Panel setMaximizable(boolean b){
		options.put("maximizable", b);
		return this;
	}
	
	public Panel setClosable(boolean b){
		options.put("closable", b);
		return this;
	}
	
	public Panel addTool(String name, String iconCls, Event event){
		Container tool=new EXContainer(name, "a").setAttribute("href", "#").setAttribute("iconCls",iconCls).setAttribute("plain", "true").addClass("easyui-linkbutton");
		getChild("tools").addChild(tool);
		return this;
	}
	
	public void onReady(ClientProxy proxy){
		proxy.getDescendentByName("tools").appendTo(new ClientProxy("body"));
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/icon.css");
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js", proxy.clone().addMethod("panel", options));
	}
}
