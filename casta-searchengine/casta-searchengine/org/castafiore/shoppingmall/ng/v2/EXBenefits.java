package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;

public class EXBenefits extends EXContainer implements Event{

	public EXBenefits(String name, String title, String template) {
		super(name, "div");
		
		setStyle("margin-top", "30px");
		Container billing = new EXContainer("billing", "div").addClass("cart-widget");
		billing.addChild(new EXContainer("head", "div").addChild(new EXContainer("", "h4").addClass("ui-widget-header").addClass("ui-corner-top").setText(title).addChild(new EXContainer("close", "a").setStyle("float", "right").addEvent(this, CLICK).setStyleClass("ui-dialog-titlebar-close ui-corner-all").setText("<span class=\"ui-icon ui-icon-closethick\">close</span>"))));
		
		Container body = new EXContainer("body", "div").addClass("ui-widget-content");
		body.addChild(new EXContainer("fieldset", "fieldset"));
		
		body.getChild("fieldset").addChild(new EXContainer("content", "div"));
		
		billing.addChild(body);
		
		body.setText(ResourceUtil.getTemplate(template, getRoot()));
		
		setStyle("z-index", "4000");
		setStyle("width", "709px");
		
		addChild(billing);
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("close")){
			remove();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
