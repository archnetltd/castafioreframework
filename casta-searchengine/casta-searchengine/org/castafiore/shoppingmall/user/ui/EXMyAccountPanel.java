package org.castafiore.shoppingmall.user.ui;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.crm.subscriptions.EXSubscriptionList;
import org.castafiore.shoppingmall.crm.subscriptions.EXSubscriptionListItem;
import org.castafiore.shoppingmall.product.ui.EXProductEditCard;
import org.castafiore.shoppingmall.product.ui.EXProductList;
import org.castafiore.shoppingmall.product.ui.EXProductListItem;
import org.castafiore.shoppingmall.product.ui.EXReportPanel;
import org.castafiore.shoppingmall.product.ui.ProductContainerPanel;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXMyAccountPanel extends EXContainer implements EventDispatcher, PopupContainer, ProductContainerPanel{

	public EXMyAccountPanel(String name, String title) {
		super(name, "div");
		//setStyle("margin-left", "-10px");
		addChild(new EXContainer("", "h3").addClass("MessageListTitle").addChild(new EXContainer("title", "div").setText(title)));
		addChild(new EXContainer("btnCtn", "div").addClass("btnCtn oo-btn"));
		addChild(new EXAboutMe());
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
	}
	public void setTitle(String title){
		getDescendentByName("title").setText(title);
	}
	
	public void toggle(Class<? extends Container> clazz, String title){
		setTitle(title);
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			
			if( c.getClass().isAssignableFrom(clazz)){
				c.setDisplay(true);
				found = true;
				
				((BaseEXUserProfile)c).fill();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXCarreer().fill());
		}
	}
	
	public void showCarreer(){
		setTitle("Jobs Carreer");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			
			if( c instanceof EXCarreer){
				c.setDisplay(true);
				found = true;
				((BaseEXUserProfile)c).fill();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXCarreer().fill());
		}
	}
	
	public void showContactInfo(){
		setTitle("Contact info");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			if( c instanceof EXContactInfo){
				c.setDisplay(true);
				found = true;
				((BaseEXUserProfile)c).fill();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXContactInfo().fill());
		}
	}
	
	public void showShopSettings(){
		setTitle("My shop settings");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			if( c instanceof EXShopSettings){
				c.setDisplay(true);
				found = true;
				((BaseEXUserProfile)c).fill();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXShopSettings().fill());
		}
	}
	
	
	public void showProductList(int  state){
		//setRendered(false);
		boolean favorit = false;
		if(state == Product.STATE_DRAFT)
			setTitle("My Products and services (Draft)");
		else if(state == Product.STATE_PUBLISHED)
			setTitle("My Products and services (Published)");
		else if(state == Product.STATE_DELETED)
			setTitle("My Products and services (Deleted)");
		else{
			setTitle("My wish list");
			favorit = true;
		}
		
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				
				if(!favorit){
					c.addChild(new EXContainer("newproduct","button").setText("New Product").addEvent(DISPATCHER, Event.CLICK));
					c.addChild(new EXContainer("draft","button").setText("Draft").addEvent(DISPATCHER, Event.CLICK));
					c.addChild(new EXContainer("published","button").setText("Published").addEvent(DISPATCHER, Event.CLICK));
					c.addChild(new EXContainer("bin","button").setText("Bin").addEvent(DISPATCHER, Event.CLICK));
					
					String plan = MallUtil.getCurrentMerchant().getPlan();
					if(!plan.equalsIgnoreCase("free")){
						c.addChild(new EXContainer("upload","button").setText("Upload Products").addEvent(DISPATCHER, Event.CLICK));
					}
					c.addChild(new EXContainer("reports","button").setText("reports").addEvent(DISPATCHER, Event.CLICK));
					
				}else{
					c.addChild(new EXContainer("deleteFavorit","button").addClass("ui-widget-header").setText("Delete").addEvent(DISPATCHER, Event.CLICK));
				}
				c.setStyle("display", "block");
				continue;
			}
			else if( c instanceof EXProductList){
				c.setDisplay(true);
				found = true;
			}else{
				c.setDisplay(false);
			}
			
		}
		
		if(!found){
			
			addChild(new EXProductList(""));
		}
		if(!favorit){
			List<Product> products = MallUtil.getCurrentUser().getMerchant().getMyProducts(state);
			getDescendentOfType(EXProductList.class).setProducts(products);
		}else{
			List<Product> favorites = MallUtil.getCurrentUser().getFavorite();
			getDescendentOfType(EXProductList.class).setProducts(favorites);
		}
		//setRendered(favori)
	}
	
	
	public void showProductList(){
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				c.addChild(new EXContainer("newproduct","button").setText("New Product").addEvent(DISPATCHER, Event.CLICK));
				c.addChild(new EXContainer("draft","button").setText("Draft").addEvent(DISPATCHER, Event.CLICK));
				c.addChild(new EXContainer("published","button").setText("Published").addEvent(DISPATCHER, Event.CLICK));
				c.addChild(new EXContainer("bin","button").setText("Bin").addEvent(DISPATCHER, Event.CLICK));
				c.addChild(new EXContainer("upload","button").setText("Upload Products").addEvent(DISPATCHER, Event.CLICK));
				c.addChild(new EXContainer("reports","button").setText("reports").addEvent(DISPATCHER, Event.CLICK));
				continue;
			}
			if( c instanceof EXProductList){
				c.setDisplay(true);
				
			}else{
				c.setDisplay(false);
			}
		}
	}
	
	public void showWishList(){
		showProductList(-1);
	}
	
	
	public void showMySubscriptions(){
		
		setTitle("My Subscriptions");
			
		
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				

				c.addChild(new EXContainer("deleteSubscription","button").addClass("ui-widget-header").setText("Delete").addEvent(DISPATCHER, Event.CLICK));
				
				continue;
			}
			if( c instanceof EXSubscriptionList){
				c.setDisplay(true);
				found = true;
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			
			addChild(new EXSubscriptionList(""));
		}
		
			
		getDescendentOfType(EXSubscriptionList.class).showSubscriptions();
		
	}
	public void showInterests(){
		setTitle("Interests");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			if( c instanceof EXInterests){
				c.setDisplay(true);
				found = true;
				((BaseEXUserProfile)c).fill();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXInterests().fill());
		}
	}
	public void showAboutMe(){
		setTitle("About me");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			if( c instanceof EXAboutMe){
				c.setDisplay(true);
				found = true;
				((BaseEXUserProfile)c).fill();
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXAboutMe().fill());
		}
	}
	
	public void showProductEditCard(Product product){
		setTitle("Create a new product");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				if(c.getChildren().size() > 0){
					c.getChildren().clear();
					c.setRendered(false);
				}
				continue;
			}
			if( c instanceof EXProductEditCard){
				c.setDisplay(true);
				found = true;
				((EXProductEditCard)c).setProduct(product);
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			EXProductEditCard card = new EXProductEditCard("editCard", false);
			card.setProduct(product);
			addChild(card);
		}
	}
	
	public void showReports(){
		setTitle("Reports");
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if(c.getName().equalsIgnoreCase("btnCtn")){
				
				continue;
			}
			if( c instanceof EXReportPanel){
				c.setDisplay(true);
				found = true;
				
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			EXReportPanel card = new EXReportPanel("editCard");
			addChild(card);
		}
	}
	
	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);	
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("draft")){
			showProductList(Product.STATE_DRAFT);
		}else if(source.getName().equalsIgnoreCase("published")){
			showProductList(Product.STATE_PUBLISHED);
		}else if(source.getName().equalsIgnoreCase("bin")){
			showProductList(Product.STATE_DELETED);
		}else if(source.getName().equalsIgnoreCase("newproduct")){
			showProductEditCard(null);
		}else if(source.getName().equalsIgnoreCase("upload")){
			addPopup(new EXUploadExcel("gfdgdfgdfgd").setStyle("z-index", "3003").setStyle("width", "500px"));
		}else if(source.getName().equals("reports")){
			showReports();
		}else if(source.getName().equals("deleteFavorit")){
			ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXProductList.class), EXProductListItem.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					EXProductListItem item = (EXProductListItem)c;
					item.deleteFavorit();
					
				}
			});
			showProductList(-1);
		}else if(source.getName().equals("deleteSubscription")){
			ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXSubscriptionList.class), EXSubscriptionListItem.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					EXSubscriptionListItem item = (EXSubscriptionListItem)c;
					item.deleteSubscription();
					
				}
			});
			showMySubscriptions();
				
		}
		
	}
	
	

}
