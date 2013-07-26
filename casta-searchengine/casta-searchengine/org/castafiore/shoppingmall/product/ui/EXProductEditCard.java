package org.castafiore.shoppingmall.product.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.logs.Log;
import org.castafiore.security.logs.Logger;
import org.castafiore.shoppingmall.product.ui.tab.ProductCardTabModel;
import org.castafiore.shoppingmall.product.ui.tab.ProductEditForm;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXProductEditCard extends EXXHTMLFragment implements EventDispatcher,  PopupContainer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean simple = false;

	public EXProductEditCard(String name, boolean simple) {
		super(name, "templates/EXProductEditCard.xhtml");
		addClass("msr").addClass("productcard");
		EXTabPanel tab = new EXTabPanel("tab");
		//tab.setAttribute("class", "mall-tab fieldset");
		//tab.setTabRenderer(new ProductTabRenderer());
		//tab.setTabContentDecorator(null);
		
		addChild(tab);
		addChild(new EXOverlayPopupPlaceHolder("pc"));
		addChild(new EXButton("draft", "Save to draft").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXButton("cancel", "Cancel").addEvent(DISPATCHER, Event.CLICK));
		this.simple = simple;
	}
	
	
	public void setProduct(Product product){
		if(product == null){
			setAttribute("path", (String)null);
		}else
		{
			product.getCharacteristics();
			setAttribute("path", product.getAbsolutePath());
		}
		EXTabPanel tab = getDescendentOfType(EXTabPanel.class);
		try{
		tab.setModel(new ProductCardTabModel(product, simple));
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public void saveProduct(boolean publish){
		Product product = null;
		
		ShoppingMallUser user = MallUtil.getCurrentUser();
		if(StringUtil.isNotEmpty(getAttribute("path"))){
			
			String path = getAttribute("path");
			product = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			Logger.log("Saving product " + product.getCode() + " to draft", Log.INFO);
		}else{
			product =user.getMerchant().createProduct();
			Logger.log("Updating product " + product.getCode() + " to draft", Log.INFO);
		}
		
		List<Container> contents = new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, contents, ProductEditForm.class);
		for(Container c : contents){
			((ProductEditForm)c).fillProduct(product);
		}
		
		user.getMerchant().updateProduct(product);
		if(publish){
			user.getMerchant().publishProduct(product);
			Logger.log("publishing product " + product.getCode(), Log.INFO);
			getAncestorOfType(ProductContainerPanel.class).showProductList(Product.STATE_PUBLISHED);
		}else{
			getAncestorOfType(ProductContainerPanel.class).showProductList(Product.STATE_DRAFT);
		}
	}
	
	public void back(){
		getAncestorOfType(ProductContainerPanel.class).showProductList();
	}

	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);	
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("draft")){
			saveProduct(false);
		}else if(source.getName().equalsIgnoreCase("cancel")){
			back();
		}
		
	}

}
