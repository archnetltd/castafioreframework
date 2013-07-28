package org.castafiore.searchengine;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.left.CategoriesTreeNode;
import org.castafiore.searchengine.left.EXActionPanel;
import org.castafiore.searchengine.middle.EXBody;
import org.castafiore.searchengine.middle.EXWorkingSpace;
import org.castafiore.searchengine.right.EXAdvertisementPanel;
import org.castafiore.searchengine.top.EXSearchBar;
import org.castafiore.searchengine.top.EXTopBar;
import org.castafiore.shoppingmall.ShoppingMallImpl;
import org.castafiore.shoppingmall.checkout.EXCheckoutPanel;

import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.merchant.EXMerchantCardPanel;
import org.castafiore.shoppingmall.merchant.EXMerchantRotator;
import org.castafiore.shoppingmall.merchant.EXMerchantRotatorPanel;
import org.castafiore.shoppingmall.merchant.EXMerchantSearchResultList;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.message.ui.EXMessagePanel;
import org.castafiore.shoppingmall.message.ui.EXNewMessage;
import org.castafiore.shoppingmall.message.ui.MessageActionsTreeNode;
import org.castafiore.shoppingmall.orders.EXCustomerViewOrdersPanel;
import org.castafiore.shoppingmall.product.ui.EXAdvProductListBar;
import org.castafiore.shoppingmall.product.ui.EXProductCard;
import org.castafiore.shoppingmall.product.ui.EXProductSearchResultList;
import org.castafiore.shoppingmall.product.ui.EXProductSliderPanel;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.shoppingmall.user.ui.EXMyAccountPanel;
import org.castafiore.shoppingmall.user.ui.UserActionsTreeNode;
import org.castafiore.shoppingmall.user.ui.UserTransactionsTreeNode;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXMall extends EXContainer implements EventDispatcher {

	public EXMall(String name) {
		super(name,"div");
		addClass("mall");
		addChild(new EXTheMessage("themessage"));
		addChild(new EXAnimator());
		addChild(SpringUtil.getBeanOfType(EXTopBar.class));
		addChild(new EXSearchBar("SearchBar"));
		addChild(new EXBody("Body"));
		showSearchMerchantResults("latest");
		getWorkingSpace().showRight();
		
	}
	
	public void showMessage(String message){
		getDescendentOfType(EXTheMessage.class).showMessage(message);
	}
	public EXAdvertisementPanel getRightPanel(){
		
		return getDescendentOfType(EXAdvertisementPanel.class);
	}
	
	
	
	public EXAnimator getAnimator(){
		return getDescendentOfType(EXAnimator.class);
	}
	
	public EXWorkingSpace getWorkingSpace(){
		return getDescendentOfType(EXWorkingSpace.class);
	}
	
	public EXActionPanel getActionPanel(){
		return getDescendentOfType(EXActionPanel.class);
	}
	/**
	 * EXProductSliderPanel
	 */
	public  void showPublicHome(){
		showSearchMerchantResults("latest");
		
	}
	/**
	 * view a product card
	 * @param p
	 * 
	 * EXProductCard
	 */
	public void showProductCard(Product p){
		EXProductCard card = getWorkingSpace().getDescendentOfType(EXProductCard.class);
		if(card != null){
			card.setProduct(p);
		}else{
			card = new EXProductCard("");
			card.setProduct(p);
			getWorkingSpace().addChild(card);
		}
		getWorkingSpace().viewOnly(EXProductCard.class);
		
		getActionPanel().showOnly("categories");
		getWorkingSpace().showRight();
	}
	

	/**
	 * EXProductSearchResultList
	 * @param products
	 */
	public void showSearchResults(String searchTerm){
		EXWorkingSpace workspace = getWorkingSpace();
		EXProductSearchResultList panel = workspace.getDescendentOfType(EXProductSearchResultList.class);
		if(panel == null){
			panel = new EXProductSearchResultList("EXProductSearchResultList", 4);
			workspace.addChild(panel);
		}
		panel.showSearchResults(searchTerm);
		workspace.viewOnly(EXProductSearchResultList.class);
		getWorkingSpace().showRight();
		getActionPanel().addBar("categories", "Categories", new CategoriesTreeNode(null));
		getActionPanel().showOnly("categories");
		
	}
	
	
	public void showSearchMerchantResults(String searchTerm){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMerchantSearchResultList panel = workspace.getDescendentOfType(EXMerchantSearchResultList.class);
		if(panel == null){
			panel = new EXMerchantSearchResultList("EXMerchantSearchResultList");
			workspace.addChild(panel);
		}
		panel.showSearchResults(searchTerm);
		workspace.viewOnly(EXMerchantSearchResultList.class);
		getWorkingSpace().showRight();
		//getActionPanel().showOnly("categories");
		getDescendentOfType(EXBody.class).getDescendentOfType(EXMerchantRotatorPanel.class).setDisplay(true);
		getActionPanel().setDisplay(false);
		
	}
	
	/**
	 * EXMyAccountPanel
	 */
	public void showMyAccount(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMyAccountPanel panel = workspace.getDescendentOfType(EXMyAccountPanel.class);
		if(panel == null){
			panel = new EXMyAccountPanel("EXMyAccountPanel", "My account");
			workspace.addChild(panel);
		}
		panel.showAboutMe();
		workspace.viewOnly(EXMyAccountPanel.class);
		getActionPanel().addBar("myAccount", "My account", new UserActionsTreeNode());
		//workspace.hideRight();
		getWorkingSpace().showRight();
	}
	
	public void showWishList(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMyAccountPanel panel = workspace.getDescendentOfType(EXMyAccountPanel.class);
		if(panel == null){
			panel = new EXMyAccountPanel("EXMyAccountPanel", "My account");
			workspace.addChild(panel);
		}
		panel.showWishList();
		workspace.viewOnly(EXMyAccountPanel.class);
		getActionPanel().addBar("myAccount", "My account", new UserActionsTreeNode());
		//workspace.hideRight();
		getWorkingSpace().showRight();
	}
//	public void showCart(boolean display){
//		EXWorkingSpace workspace = getWorkingSpace();
//		EXShoppingCart panel = workspace.getDescendentOfType(EXShoppingCart.class);
//		if(panel == null){
//			panel = new EXShoppingCart("EXMyAccountPanel", "Shopping cart - <span class='cart-title-info'>Your shopping cart is empty right now</span>");
//			workspace.addChild(panel);
//			panel.setDisplay(false);
//		}
//		if(display)
//		{
//			workspace.viewOnly(EXShoppingCart.class);
//			workspace.hideRight();
//		}
//	}
	
	public void signOut(){
		
	}
	
	
	public void showOrdersHistory(String status){
		EXWorkingSpace workspace = getWorkingSpace();
		EXCustomerViewOrdersPanel list = workspace.getDescendentOfType(EXCustomerViewOrdersPanel.class);
		if(list == null){
			list = new EXCustomerViewOrdersPanel("EXCustomerViewOrdersPanel", "Transactions history");
			workspace.addChild(list);
		}
		list.init(status);
		workspace.viewOnly(EXCustomerViewOrdersPanel.class);
		getWorkingSpace().hideRight();
		getActionPanel().addBar("transaction", "My transactions", new UserTransactionsTreeNode());
	}
	
	
	public void showCheckedOut(List<OrderInfo> orders){
		EXWorkingSpace workspace = getWorkingSpace();
		EXCheckoutPanel list = workspace.getDescendentOfType(EXCheckoutPanel.class);
		if(list == null){
			list = new EXCheckoutPanel("EXCheckoutPanel");
			workspace.addChild(list);
		}
		list.init(orders);
		workspace.viewOnly(EXCheckoutPanel.class);
		getWorkingSpace().hideRight();
		getActionPanel().addBar("myAccount", "My account", new UserActionsTreeNode());
	}
	
	public void showInvoices(List<OrderInfo> orders){
		EXWorkingSpace workspace = getWorkingSpace();
		EXCustomerViewOrdersPanel list = workspace.getDescendentOfType(EXCustomerViewOrdersPanel.class);
		if(list == null){
			list = new EXCustomerViewOrdersPanel("EXCustomerViewOrdersPanel", "Transactions history");
			workspace.addChild(list);
		}
		list.init(orders, "Transaction history");
		workspace.viewOnly(EXCustomerViewOrdersPanel.class);
		getWorkingSpace().showRight();
		
		getActionPanel().addBar("myAccount", "My account", new UserActionsTreeNode());
	}
	
	public void showInbox(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMessagePanel list = workspace.getDescendentOfType(EXMessagePanel.class);
		if(list == null){
			list = new EXMessagePanel("EXMessagePanel", "Personal messages");
			workspace.addChild(list);
		}
		list.showInbox();
		workspace.viewOnly(EXMessagePanel.class);
		getActionPanel().addBar("messages", "Inbox folders", new MessageActionsTreeNode());
		EXNewMessage nm = list.getDescendentOfType(EXNewMessage.class);
		if(nm != null){
			nm.setDisplay(false);
		}
		getWorkingSpace().showRight();
		
		
	}
	
	
	public String recreateCategories(String name){
		((ShoppingMallImpl)MallUtil.getCurrentMall()).reCreateCategories();
		return "success";
	}
	
	public void showMerchantCard(String merchant){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMerchantCardPanel card = workspace.getDescendentOfType(EXMerchantCardPanel.class);
		if(card == null){
			card = new EXMerchantCardPanel("EXMerchantCardPanel");
			workspace.addChild(card);
		}
		Merchant m = MallUtil.getCurrentMall().getMerchant(merchant);
		card.setMerchant(m);
		workspace.viewOnly(EXMerchantCardPanel.class);
		getWorkingSpace().showRight();
		
		EXAdvertisementPanel advPanel= getRightPanel();
		EXAdvProductListBar bar = advPanel.getDescendentOfType(EXAdvProductListBar.class);
		if(bar == null){
			
			bar = new EXAdvProductListBar("EXAdvProductListBar", "Products from " + m.getTitle(), new ArrayList<Product>());
			getRightPanel().addChild(bar);
		}
		
		List<Product> products = m.getManager().getMyProducts(Product.STATE_PUBLISHED, 0, 5);
		bar.setProducts(products);
		bar.setTitle("Products from " + m.getTitle());
		bar.setDisplay(true);
		
	}
	
	public void showAddedComments(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMessagePanel list = workspace.getDescendentOfType(EXMessagePanel.class);
		if(list == null){
			list = new EXMessagePanel("EXMessagePanel", "Comments");
			workspace.addChild(list);
		}
		list.showAddedComments();
		workspace.viewOnly(EXMessagePanel.class);
		EXNewMessage nm = list.getDescendentOfType(EXNewMessage.class);
		if(nm != null){
			nm.setDisplay(false);
		}
		//workspace.hideRight();
		getActionPanel().addBar("messages", "Inbox folders", new MessageActionsTreeNode());
		getWorkingSpace().showRight();
	}
	
	
	public void showSentMessages(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMessagePanel list = workspace.getDescendentOfType(EXMessagePanel.class);
		if(list == null){
			list = new EXMessagePanel("EXMessagePanel", "Inbox");
			workspace.addChild(list);
		}
		list.showSentMessage();
		workspace.viewOnly(EXMessagePanel.class);
		EXNewMessage nm = list.getDescendentOfType(EXNewMessage.class);
		if(nm != null){
			nm.setDisplay(false);
		}
		getWorkingSpace().showRight();
		getActionPanel().addBar("messages", "Inbox folders", new MessageActionsTreeNode());
	}
	
	
	public void showSharedMessages(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMessagePanel list = workspace.getDescendentOfType(EXMessagePanel.class);
		if(list == null){
			list = new EXMessagePanel("EXMessagePanel", "Inbox");
			workspace.addChild(list);
		}
		list.showSharedMessages();
		workspace.viewOnly(EXMessagePanel.class);
		EXNewMessage nm = list.getDescendentOfType(EXNewMessage.class);
		if(nm != null){
			nm.setDisplay(false);
		}
		getActionPanel().addBar("messages", "Inbox folders", new MessageActionsTreeNode());
		getWorkingSpace().showRight();
	}
	
	public void showAlerts(){
		
	}
	
//	public void checkout(){
//		List<Order> result = MallUtil.getCurrentUser().checkOut(getDescendentOfType(EXShoppingCart.class));
//		//go to orders
//		//showInvoices(result);
//		showCheckedOut(result);
//	}
	
	public void showMySubscriptions(){
		EXWorkingSpace workspace = getWorkingSpace();
		EXMyAccountPanel panel = workspace.getDescendentOfType(EXMyAccountPanel.class);
		if(panel == null){
			panel = new EXMyAccountPanel("EXMyAccountPanel", "My account");
			workspace.addChild(panel);
		}
		panel.showMySubscriptions();
		workspace.viewOnly(EXMyAccountPanel.class);
		getActionPanel().addBar("myAccount", "My account", new UserActionsTreeNode());
		//workspace.hideRight();
		getWorkingSpace().showRight();
		
		//todo
	}
	
	public void walkOut(){
		try{
			ShoppingMallUser user  = SpringUtil.getBeanOfType(ShoppingMallUserManager.class).login("anonymous", "anonymous");
		}catch(UsernameNotFoundException unfe){
			getChild("error").setText("Username does not exist").setDisplay(true);
		}catch(Exception e){
			e.printStackTrace();
			getChild("error").setText("Unknown error occured.").setDisplay(true);
		}
		
		this.getChildren().clear();
		setRendered(false);
		addChild(new EXAnimator());
		addChild(SpringUtil.getBeanOfType(EXTopBar.class));
		addChild(new EXSearchBar("SearchBar"));
		addChild(new EXBody("Body"));
		getWorkingSpace().addChild(new EXProductSliderPanel("ProductSlider"));
		getWorkingSpace().showRight();
		getWorkingSpace().viewOnly(EXProductSliderPanel.class);
		
	}
	
	public void walkIn(final String username){
		
		//SpringUtil.getBeanOfType(ShoppingMallUserManager.class).login(username, password)
		ComponentUtil.iterateOverDescendentsOfType(this, MallLoginSensitive.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				c.getAncestorOfType(MallLoginSensitive.class).onLogin(username);
			}
		});
		
		getWorkingSpace().showRight();
	}
	
	

	@Override
	public void executeAction(Container source) {
		String action = source.getAttribute("action");
		if(action.equalsIgnoreCase("new product")){
			//newProduct();
		}else if(action.equalsIgnoreCase("inbox")){
			showInbox();
		}else if(action.equalsIgnoreCase("my account")){
			showMyAccount();
		}else if(action.equalsIgnoreCase("walk out")){
			walkOut();
		}else if(action.equalsIgnoreCase("My subscriptions")){
			showMySubscriptions();
		}else if(action.equalsIgnoreCase("wish list")){
			showWishList();
		}else if(action.equalsIgnoreCase("transactions")){
			showOrdersHistory("-1");
		}
	}
	
	
	
}