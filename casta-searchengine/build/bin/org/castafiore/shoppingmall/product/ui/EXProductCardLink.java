package org.castafiore.shoppingmall.product.ui;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXProductCardLink extends EXContainer implements EventDispatcher{

	public EXProductCardLink(String name) {
		super(name, "a");
		setAttribute("href", "#");
		setText("");
		addEvent(DISPATCHER, Event.CLICK);
	}
	
	public EXProductCardLink(String name, String tag) {
		super(name, tag);
		
		
		addEvent(DISPATCHER, Event.CLICK);
	}
	
	
	public void setProduct(Product product){
		setAttribute("product", product.getAbsolutePath());
		if(getTag().equalsIgnoreCase("a"))
			setText(product.getTitle());
		
	}


	@Override
	public void executeAction(Container container) {
		if(StringUtil.isNotEmpty(getAttribute("product"))){
			Product p = (Product)SpringUtil.getRepositoryService().getFile(getAttribute("product"), Util.getRemoteUser());
			container.getAncestorOfType(EXMall.class).showProductCard(p);
		}else{
			
			throw new UIException("You clicked a linked not yet initialised");
		}
		
		
	}

}
