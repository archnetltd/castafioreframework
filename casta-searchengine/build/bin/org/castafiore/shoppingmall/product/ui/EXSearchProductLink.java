package org.castafiore.shoppingmall.product.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.ShoppingMallManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXSearchProductLink extends EXContainer implements EventDispatcher{

	public EXSearchProductLink(String name) {
		super(name, "a");
		setAttribute("href", "#");
		addEvent(DISPATCHER, Event.CLICK);
	}
	public EXSearchProductLink(String name, String tag) {
		super(name, tag);
		//setAttribute("href", "#");
		addEvent(DISPATCHER, Event.CLICK);
	}
	
	public void setSearchTerm(String term){
		setAttribute("searchterm", term);
	}
	
	public String getSearchTerm(){
		return getAttribute("searchterm");
	}
	@Override
	public void executeAction(Container source) {
		getAncestorOfType(EXMall.class).showSearchResults(getSearchTerm());
		
	}
	
	
	

}
