package org.castafiore.designable;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXShopDepartments extends AbstractXHTML implements Event{

	public EXShopDepartments(String name) {
		super(name);
		addClass("cart-widget");
		setStyle("margin-top", "10px");
		addChild(new EXContainer("title", "span").setText("Shop departments"));
		addChild(new EXContainer("departments", "ul").addClass("ui-widget-content product-categories"));
	}
	
	
	public void setCategories(List<String> categories){
		Container ul =getChild("departments");
		ul.getChildren().clear();
		ul.setRendered(false);
		Container lii = new EXContainer("", "li").addClass("ui-state-default ui-corner-all")
		.addChild(
				new EXContainer("", "a").addEvent(this, Event.CLICK).setAttribute("href", "#category").setText("All categories")
					.addChild(new EXContainer("", "span").addClass("ui-icon ui-icon-arrowthick-1-e"))
			);
		ul.addChild(lii);
		for(String s : categories){
			Container li = new EXContainer("", "li").addClass("ui-state-active ui-corner-all")
				.addChild(
						new EXContainer("", "a").addEvent(this, Event.CLICK).setAttribute("href", "#category").setText(s)
							.addChild(new EXContainer("", "span").addClass("ui-icon ui-icon-arrowthick-1-e"))
					);
			ul.addChild(li);
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String category = container.getText();
		String username = MallUtil.getEcommerceMerchant();
		
		
		EXEcommerce ecommerce = container.getAncestorOfType(EXEcommerce.class);
		EXCatalogue cat = (EXCatalogue)ecommerce.getPageOfType(EXCatalogue.class);
		if(cat == null){
			cat = new EXCatalogue("");
			ecommerce.getBody().addChild(cat);
		}
		if(category.equalsIgnoreCase("all categories")){
			cat.search("recent", "Demo", username);
		}else{
			cat.search("category", category,username);
		}
		ecommerce.showPage(EXCatalogue.class);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
