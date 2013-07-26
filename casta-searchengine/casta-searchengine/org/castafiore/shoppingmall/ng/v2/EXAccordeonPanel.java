package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.ui.tabbedpane.TabRenderer;
import org.castafiore.utils.StringUtil;

public class EXAccordeonPanel extends EXContainer implements Event, TabPanel{

	private AccordeoPanelModel model;
	int curTab = 0;
	
	public EXAccordeonPanel(String name, AccordeoPanelModel model) {
		super(name,"div");
		setStyle("z-index", "4000");
		setStyle("width", "709px");
		setStyleClass("ui-dialog ui-widget ui-widget-content ui-corner-all");
		this.model = model;
		init();
		
		
	}

	public AccordeoPanelModel getModel() {
		return model;
	}

	public void setModel(AccordeoPanelModel model) {
		this.model = model;
	}
	
	public void init(){
		for(int i = 0; i < model.size();i++){
			Container shipping = new EXContainer("item_" + i, "div");
			if(i ==0)
				shipping.addChild(new EXContainer("head", "div").setStyleClass("ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix").setText("<h3 class=\"ui-dialog-title\">"+model.getTabLabelAt(this, i, false)+"</h3>"));
			else
				shipping.addChild(new EXContainer("head", "div").setStyleClass("ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix").setText("<h3 class=\"ui-dialog-title\">"+model.getTabLabelAt(this, i, false)+"</h3>"));
			addChild(shipping);
			shipping.addChild(new EXContainer("message", "h5").addClass("ui-state-error").setStyle("padding", "12px").setStyle("display", "none").setStyle("margin", "0"));
			shipping.addChild(new EXContainer("body", "div").addClass("ui-dialog-content ui-widget-content"));
			shipping.addChild(new EXContainer("ppfoot", "div").addClass("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix"));
			
			shipping.getChild("ppfoot").addChild(new EXButton("back", "Cancel").addEvent(this, CLICK));
			shipping.getChild("ppfoot").addChild(new EXButton("next", "Next").addEvent(this, CLICK));
			if(i == 0){
				shipping.getChild("body").addChild(model.getTabContentAt(this, i));
				shipping.getChild("ppfoot").setDisplay(true);
			}else{
				shipping.getChild("ppfoot").setDisplay(false);
			}
			
			
			
		}
	}
	
	
	public void next(){
		if(curTab < (model.size() )){
			curTab++;
			int from = curTab -1;
			if(model.onNext(getChildByIndex(from).getChild("body").getChildByIndex(0), from, this))
				load();
		}else{
			close();
		}
		
	}
	
	
	public void setMessage(String message, int index){
		if(StringUtil.isNotEmpty(message))
			getChildByIndex(index).getChild("message").setText(message).setStyle("display", "block");
		else{
			message = "";
			getChildByIndex(index).getChild("message").setText(message).setStyle("display", "none");
		}
	}
	
	public void load(){
		for(int i = 0; i < getChildren().size();i++){
			Container h = getChildByIndex(i);
			
			Container body = h.getChild("body");
			Container foot = h.getChild("ppfoot");
			if(i==curTab){
				body.setDisplay(true);
				foot.setDisplay(true);
				if(body.getChildren().size() == 0){
					body.addChild(model.getTabContentAt(this, i));
				}
				
				if(curTab == model.size()-1){
					foot.getDescendentByName("next").setText("Finish");
				}else{
					foot.getDescendentByName("next").setText("Next");
				}
				
				if(i > 0){
					foot.getDescendentByName("back").setText("Back");
				}
				
			}else if(i != curTab){
				body.setDisplay(false);
				foot.setDisplay(false);
				
			}
		}
	}
	
	
	public void previous(){
		if(curTab >0){
			curTab--;
			load();
		}else if(curTab == 0){
			close();
		}
		
	}
	
	
	public void close(){
		this.remove();
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("next"))
			next();
		else if(container.getName().equalsIgnoreCase("back"))
			previous();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public TabRenderer getTabRenderer() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
