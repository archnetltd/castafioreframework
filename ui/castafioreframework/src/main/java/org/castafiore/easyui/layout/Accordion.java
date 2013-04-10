package org.castafiore.easyui.layout;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;

public class Accordion extends EXContainer{

	private JMap options = new JMap();
	private AccordionModel model;
	public Accordion(String name) {
		super(name, "div");
		
	}
	
	public Accordion setFit(boolean fit){
		options.put("fit", fit);
		return this;
	}
	
	public Accordion setAnimate(boolean animate){
		options.put("animate", animate);
		return this;
	}
	
	public Accordion showBorder(boolean border){
		options.put("border", border);
		return this;
	}
	
	public Accordion setModel(AccordionModel model){
		this.model = model;
		recreate();
		return this;
	}
	
	
	private void recreate(){
		this.getChildren().clear();
		setRendered(false);
		int toselect = model.getSelectedTab();
		for(int i =0; i < model.size();i++){
			Container content = model.getTabContentAt(this, i);
			
			String title = model.getTabLabelAt(this, i);
			boolean selected = toselect == i;
			String iconCls = model.getIconCls(i);
			StringBuilder opts = new StringBuilder();
			if(StringUtil.isNotEmpty(iconCls)){
				opts.append("iconCls:'").append(iconCls).append("'");
			}
			
			if(selected){
				if(opts.length() > 0){
					opts.append(",");
				}
				opts.append("selected").append(selected);
			}
			content.setAttribute("title", title);
			if(opts.length() >0){
				content.setAttribute("data-options", opts.toString());
			}
			addChild(content);
			
			
		}
	}
	
	public void onReady(ClientProxy proxy){
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js", proxy.clone().addMethod("accordion", options));
	}

}
