package org.castafiore.shoppingmall.product.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.KeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.user.ui.EXUploadExcel;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXProfocusEvent extends EXBorderLayoutContainer implements EventDispatcher, PopupContainer, ProductContainerPanel{
	public EXProfocusEvent(String name, String title) {
		super(name);
		
		
		getChild("popupContainer").remove();
		EXToolBar tb = new EXToolBar("btnCtn");
		addChild(tb, TOP);
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
		getContainer(CENTER).setStyle("vertical-align", "top").setStyle("padding", "0");
		getContainer(LEFT).setStyle("width", "0px").setStyle("padding", "0");
		getContainer(RIGHT).setStyle("padding", "0");
		getContainer(TOP).setStyle("padding", "0");
	}
	public void setTitle(String title){
		getAncestorOfType(Panel.class).setTitle(title);
	}
	
	
	public void showProductList(int  state , String type){
		
		if(getAncestorOfType(Panel.class) != null){
		if(state == Product.STATE_DRAFT)
			getAncestorOfType(Panel.class).setTitle(type + " (Draft)");
		else if(state == Product.STATE_PUBLISHED)
			getAncestorOfType(Panel.class).setTitle(type + " (Published)");
		else if(state == Product.STATE_DELETED)
			getAncestorOfType(Panel.class).setTitle(type + " (Deleted)");
		}
		
		EXToolBar tb = getDescendentOfType(EXToolBar.class);
		tb.getChildren().clear();
		
		tb.addChild(new EXButton("newevent","Create an event").addEvent(DISPATCHER, Event.CLICK));
		tb.addChild(new EXButton("newdestination","Create a destination").addEvent(DISPATCHER, Event.CLICK));
		
		
		
		String plan = MallUtil.getCurrentMerchant().getPlan();
		if(!plan.equalsIgnoreCase("free")){
			tb.addChild(new EXButton("upload","Upload Items").addEvent(DISPATCHER, Event.CLICK));
			//tb.addChild(new EXButton("reports","Reports").addEvent(DISPATCHER, Event.CLICK));
		}
		
		
		if(getDescendentOfType(EXProductList.class) == null){
			Container c =new EXContainer("leftButtons", "div").addClass("fieldset").setStyle("width", "180px").setStyle("float", "left").setStyle("padding", "9px");
			addChild(c, LEFT);
			
			c.addChild(new EXButton("draftEvents","Drafted Events").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "180px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("draftDestinations","Drafted Destinations").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "180px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("publishedEvents","Published Events").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "180px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("publishedDestinations","Published Destinations").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "180px").setStyle("margin", "12px 0"));
			c.addChild(new EXButton("bin","Deleted Items").addEvent(DISPATCHER, Event.CLICK).setStyle("width", "180px").setStyle("margin", "12px 0"));
			getContainer(LEFT).setStyle("vertical-align", "top");
			addChild(new EXProductList("").setStyle("width", "100%"), CENTER);
			
		}
		
		List<Product> products = MallUtil.getCurrentUser().getMerchant().getMyProducts(state);
		
		if(state != Product.STATE_DELETED){
			List<Product> filtered = new ArrayList<Product>();
			for(Product p : products){
				KeyValuePair kv = p.getCategory("Type");
				if(kv != null && kv.getValue().equalsIgnoreCase(type))
					filtered.add(p);
			}
			getDescendentOfType(EXProductList.class).setProducts(filtered);
		}else{
			getDescendentOfType(EXProductList.class).setProducts(products);
		}
		getDescendentOfType(EXProductList.class).setDisplay(true);
		getDescendentByName("leftButtons", LEFT).setDisplay(true);
		
		if(getDescendentOfType(EXProfocusEventEditCard.class) !=null){
			getDescendentOfType(EXProfocusEventEditCard.class).setStyle("display", "none");
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
		
		ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXProductList.class), Container.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				if(c.getName().equalsIgnoreCase("qty") || c.getName().equalsIgnoreCase("price") || c.getName().equalsIgnoreCase("td_qty") || c.getName().equalsIgnoreCase("td_price")){
					c.setDisplay(false);
				}
				
			}
		});
		
		
	}
	
	
	public void showProductList(int  state){
		
		showProductList(state, "Events");
		
		
	}
	
	public void showProductList(){
		showProductList(Product.STATE_PUBLISHED, "Events");
		
	}
	
	public void showProductEditCard(Product product){
		String type = "Events";
		if(product != null){
			type = product.getCategory("Type").getValue();
		}
		
		showProductEditCard(product, type);
	}
	
	public void showProductEditCard(Product product, String type){
		getAncestorOfType(Panel.class).setTitle("Create a new " + type);
		if(getDescendentOfType(EXProfocusEventEditCard.class)==null){
			EXProfocusEventEditCard card = new EXProfocusEventEditCard("editCard");
			card.setAttribute("Type", type);
			card.setProduct(product);
			addChild(card, CENTER);
		}else{
			getDescendentOfType(EXProfocusEventEditCard.class).setStyle("display", "block");
			getDescendentOfType(EXProfocusEventEditCard.class).setProduct(product);
			getDescendentByName("leftButtons", LEFT).setDisplay(true);
			getDescendentOfType(EXProfocusEventEditCard.class).setAttribute("Type", type);
		}
		
		if(getDescendentOfType(EXProductList.class) !=null){
			getDescendentOfType(EXProductList.class).setStyle("display", "none");
			getDescendentByName("leftButtons", LEFT).setDisplay(false);
		}
		
		if(getDescendentOfType(EXReportPanel.class) !=null){
			getDescendentOfType(EXReportPanel.class).setStyle("display", "none");
		}
	}
	
	
	
	
	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);	
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("draftEvents")){
			showProductList(Product.STATE_DRAFT, "Events");
		}else if(source.getName().equalsIgnoreCase("publishedEvents")){
			showProductList(Product.STATE_PUBLISHED, "Events");
		}else if(source.getName().equalsIgnoreCase("draftDestinations")){
			showProductList(Product.STATE_DRAFT, "Destinations");
		}else if(source.getName().equalsIgnoreCase("publishedDestinations")){
			showProductList(Product.STATE_PUBLISHED, "Destinations");
		}else if(source.getName().equalsIgnoreCase("bin")){
			showProductList(Product.STATE_DELETED);
		}else if(source.getName().equalsIgnoreCase("newevent")){
			showProductEditCard(null, "Events");
		}else if(source.getName().equalsIgnoreCase("newdestination")){
			showProductEditCard(null, "Destinations");
		}else if(source.getName().equalsIgnoreCase("upload")){
			addPopup(new EXUploadExcel("gfdgdfgdfgd").setStyle("z-index", "3003").setStyle("width", "500px"));
		}else if(source.getName().equalsIgnoreCase("products")){
			showProductList(Product.STATE_PUBLISHED, "Events");
		}
	}
}
