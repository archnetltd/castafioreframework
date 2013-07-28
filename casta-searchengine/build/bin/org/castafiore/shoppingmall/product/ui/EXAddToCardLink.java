package org.castafiore.shoppingmall.product.ui;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXAddToCardLink extends EXContainer implements Event{

	public EXAddToCardLink(String name, String tagName) {
		super(name, tagName);
		addEvent(this, CLICK);
	}
	
	
	public String getProduct(){
		return getAttribute("product");
	}
	
	public void setProduct(String path, String soldBy){
		setAttribute("product", path);
		setAttribute("soldby", soldBy);
	}
	
	
	protected void addToCart(){
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String product = getProduct();
		if(StringUtil.isNotEmpty(product)){
			Product p = (Product)SpringUtil.getRepositoryService().getFile(getProduct(), Util.getRemoteUser());
			getAncestorOfType(EXMall.class).getDescendentOfType(EXMiniCarts.class).getMiniCart(p.getProvidedBy()).addToCart(p,1,container);
		}else{
			throw new UIException("Product not set yet");
		}
		
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
