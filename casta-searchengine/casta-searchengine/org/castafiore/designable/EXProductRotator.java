package org.castafiore.designable;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.product.EXProduct;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;

public class EXProductRotator extends AbstractXHTML implements Event{

	public EXProductRotator(String name) {
		super(name);
		addClass("cart-widget").setStyle("margin-top", "30px");
		addChild(new EXContainer("divs", "div").setStyle("height", "150px").addClass("slideshow"));
		
	}
	
	
	public void setProducts(List<Product> products){
		Container ul =getChild("divs");
		ul.getChildren().clear();
		ul.setRendered(false);
		for(Product p : products){
			Container img = new EXContainer("", "img").setAttribute("src", p.getImageUrl("")).setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			ul.addChild(img);
		}
		
		if(ul.getChildren().size() > 0){
			ul.getChildren().get(0).addClass("active");
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String path = container.getAttribute("path");
		Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		EXEcommerce ecom = container.getAncestorOfType(EXEcommerce.class);
		EXProduct uip = (EXProduct)ecom.getPageOfType(EXProduct.class);
		if(uip == null){
			uip = new EXProduct("productCard");
			ecom.getBody().addChild(uip);
		}
		uip.setProduct(p);
		ecom.showPage(uip.getClass());
		return true;
		
		
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
