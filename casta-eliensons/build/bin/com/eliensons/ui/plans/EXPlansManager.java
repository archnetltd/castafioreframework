package com.eliensons.ui.plans;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.product.ui.EXProductEditCard;
import org.castafiore.shoppingmall.product.ui.EXProductList;
import org.castafiore.shoppingmall.product.ui.EXProductsPanel;
import org.castafiore.shoppingmall.product.ui.EXReportPanel;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class EXPlansManager extends EXProductsPanel{

	public EXPlansManager(String name, String title) {
		super(name, title);
		
	}
	
	
public void showProductList(int  state){
		
		if(state == Product.STATE_DRAFT)
			getAncestorOfType(Panel.class).setTitle("My Products and services (Draft)");
		else if(state == Product.STATE_PUBLISHED)
			getAncestorOfType(Panel.class).setTitle("My Products and services (Published)");
		else if(state == Product.STATE_DELETED)
			getAncestorOfType(Panel.class).setTitle("My Products and services (Deleted)");
		
		EXToolBar tb = getDescendentOfType(EXToolBar.class);
		tb.getChildren().clear();
		
		tb.addChild(new EXButton("products","Products").addEvent(DISPATCHER, Event.CLICK));
		tb.addChild(new EXButton("newproduct","Create Product").addEvent(DISPATCHER, Event.CLICK));
		
		
		String plan = MallUtil.getCurrentMerchant().getPlan();
		if(!plan.equalsIgnoreCase("free")){
			tb.addChild(new EXButton("upload","Upload Products").addEvent(DISPATCHER, Event.CLICK));
			
		}
		tb.addChild(new EXButton("reports","Reports").addEvent(DISPATCHER, Event.CLICK));
		
		if(getDescendentOfType(EXProductList.class) == null){
			Container c =new EXContainer("leftButtons", "div").addClass("fieldset").setStyle("width", "80px").setStyle("float", "left").setStyle("padding", "9px");
			addChild(c, LEFT);
			
			c.addChild(new EXButton("draft","Draft").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("published","Published").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("bin","Deleted").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px").setStyle("margin", "12px 0"));
			getContainer(LEFT).setStyle("vertical-align", "top");
			addChild(new EXProductList("").setStyle("width", "100%"), CENTER);
			
		}
		
		List<Product> products = MallUtil.getCurrentUser().getMerchant().getMyProducts(state);
		getDescendentOfType(EXProductList.class).setProducts(products);
		getDescendentOfType(EXProductList.class).setDisplay(true);
		getDescendentByName("leftButtons", LEFT).setDisplay(true);
		
		if(getDescendentOfType(EXProductEditCard.class) !=null){
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "none");
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
		
		
	}
	
	public void showProductList(){
		EXToolBar tb = getDescendentOfType(EXToolBar.class);
		tb.getChildren().clear();
		
		tb.addChild(new EXButton("products","Products").addEvent(DISPATCHER, Event.CLICK));
		tb.addChild(new EXButton("newproduct","Create Product").addEvent(DISPATCHER, Event.CLICK));
		
		
		String plan = MallUtil.getCurrentMerchant().getPlan();
		if(!plan.equalsIgnoreCase("free")){
			tb.addChild(new EXButton("upload","Upload Products").addEvent(DISPATCHER, Event.CLICK));
			
		}
		tb.addChild(new EXButton("reports","Reports").addEvent(DISPATCHER, Event.CLICK));
		
		if(getDescendentOfType(EXProductList.class) == null){
			Container c =new EXContainer("leftButtons", "div").addClass("fieldset").setStyle("width", "80px").setStyle("float", "left").setStyle("padding", "9px");
			addChild(c, LEFT);
			
			c.addChild(new EXButton("draft","Draft").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px"));
			c.addChild(new EXButton("published","Published").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px"));
			c.addChild(new EXButton("bin","Deleted").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "80px"));
			addChild(new EXProductList("").setStyle("width", "100%"), CENTER);
		}else{
			getDescendentOfType(EXProductList.class).setDisplay(true);
			getDescendentByName("leftButtons", LEFT).setDisplay(true);
		}
		
		if(getDescendentOfType(EXProductEditCard.class) !=null){
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "none");
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
		
	}
	public void showProductEditCard(Product product){
		getAncestorOfType(Panel.class).setTitle("Create a new product");
		if(getDescendentOfType(EXProductEditCard.class)==null){
			EXProductEditCard card = new EXProductEditCard("editCard", false);
			card.setProduct(product);
			addChild(card, CENTER);
		}else{
			getDescendentOfType(EXProductEditCard.class).setStyle("display", "block");
			getDescendentOfType(EXProductEditCard.class).setProduct(product);
			getDescendentByName("leftButtons", LEFT).setDisplay(true);
		}
		
		if(getDescendentOfType(EXProductList.class) !=null){
			getDescendentOfType(EXProductList.class).setStyle("display", "none");
			getDescendentByName("leftButtons", LEFT).setDisplay(false);
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
	}

}
