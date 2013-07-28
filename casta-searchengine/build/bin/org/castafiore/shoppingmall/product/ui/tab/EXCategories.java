package org.castafiore.shoppingmall.product.ui.tab;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXCategories extends EXAbstractProductTabContent implements Event {

	public EXCategories() {
		String plan = MallUtil.getCurrentMerchant().getPlan();
		String msg = "";
		if(plan.equalsIgnoreCase("free")){
			msg = "";
		}
		//addChild(new EXContainer("maxCategories", "h4").setText(msg));
		addChild(new EXContainer("addCategory", "button").setText("Add New").addEvent(this, CLICK));
		addChild(new EXContainer("error", "p").addClass("error"));
		addChild(new EXProductCategory());
	}

	@Override
	public void fillProduct(Product product) {
		getChild("error").setText("");
		getChild("error").setDisplay(false);
		getDescendentOfType(EXProductCategory.class).fillProduct(product);
		
	}
	
	public void setError(String error){
		getChild("error").setDisplay(true).setText(error);
	}

	@Override
	public Container setProduct(Product product) {
		getDescendentOfType(EXProductCategory.class).setProduct(product);
		return this;
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getParent().getDescendentOfType(EXProductCategory.class).addRawLine();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
