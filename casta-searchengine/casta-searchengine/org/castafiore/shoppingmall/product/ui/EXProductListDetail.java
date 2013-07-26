package org.castafiore.shoppingmall.product.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;



/**
 * The Class EXProductListDetail.
 */
public class EXProductListDetail extends EXContainer implements EventDispatcher{

	/**
	 * Instantiates a new eX product list detail.
	 *
	 * @param name the name
	 */
	public EXProductListDetail(String name) {
		super(name, "div");
		addClass("body").setStyle("margin-top", "0.8em");
		addChild(new EXContainer("summary", "p"));
		addChild(
				new EXContainer("buttons", "div")
				.addChild(new EXContainer("delete", "button").setText("Delete").addEvent(DISPATCHER, Event.CLICK))
				.addChild(new EXContainer("edit", "button").setText("Edit").addEvent(DISPATCHER, Event.CLICK))
				.addChild(new EXContainer("publish", "button").setText("Publish").addEvent(DISPATCHER, Event.CLICK))
		);
		
	}
	
	
	/**
	 * Sets the product.
	 *
	 * @param product the new product
	 */
	public void setProduct(Product product){
		
		
		getChild("summary").setText(product.getSummary());
		if(product != null && product.getStatus() == Product.STATE_PUBLISHED){
			getDescendentByName("publish").setText("Un-Publish");
		}else{
			getDescendentByName("publish").setText("Publish");
		}
	}

	
	

	/* (non-Javadoc)
	 * @see org.castafiore.searchengine.EventDispatcher#executeAction(org.castafiore.ui.Container)
	 */
	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("delete")){
			Product comment = getAncestorOfType(EXProductListItem.class).getItem();
			MallUtil.getCurrentUser().getMerchant().deleteProduct(comment);
			source.getAncestorOfType(EXProductListItem.class).setDisplay(false);
		}else if(source.getName().equals("publish")){
			Product comment = getAncestorOfType(EXProductListItem.class).getItem();
			
			if(source.getText().equalsIgnoreCase("publish"))
				MallUtil.getCurrentUser().getMerchant().publishProduct(comment);
			else{
				comment.setStatus(Product.STATE_DRAFT);
				comment.save();
			}
			source.getAncestorOfType(EXProductListItem.class).setDisplay(false);
		}else if(source.getName().equals("edit")){
			Product product = getAncestorOfType(EXProductListItem.class).getItem();
			source.getAncestorOfType(ProductContainerPanel.class).showProductEditCard(product);
		}
		
	}
}
