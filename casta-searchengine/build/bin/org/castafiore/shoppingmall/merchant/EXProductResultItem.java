package org.castafiore.shoppingmall.merchant;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.product.ui.EXMiniCarts;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;

public class EXProductResultItem extends EXXHTMLFragment implements EventDispatcher, MallLoginSensitive{

	public EXProductResultItem(String name) {
		super(name, "templates/EXProductResultItem.xhtml");
		addClass("products");
	
		addChild(new EXContainer("img", "img").addEvent(DISPATCHER, Event.CLICK).setStyle("cursor", "pointer"));
		addChild(new EXContainer("title", "label").addEvent(DISPATCHER, Event.CLICK).setStyle("cursor", "pointer"));
		addChild(new EXContainer("price", "label").addClass("price"));
		//<div id="20013161" style="background-image: url(&quot;icons-2/fugue/icons/shopping-basket.png&quot;);" name="shopping-basket" __path="0/0/4/1/1/14"></div>
		addChild(new EXContainer("cart", "div").setStyle("background-image", "url('icons-2/fugue/icons/shopping-basket.png')").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("favorit", "div").setStyle("background-image", "url('icons-2/fugue/icons/heart.png')").addEvent(DISPATCHER, Event.CLICK));
		
		
		if(Util.getRemoteUser() == null){
			getChild("favorit").setDisplay(false);
		}
		
		
	}
	
	
	public void setProduct(Product product){
		setAttribute("path", product.getAbsolutePath());
		getChild("title").setText(product.getTitle());
		getChild("img").setAttribute("src", product.getImageUrl(""));
		getChild("price").setText(product.getTotalPrice().toPlainString());
	}
	
	public void addToCart(Container source){
		Product p = getProduct();
		getAncestorOfType(EXMall.class).getDescendentOfType(EXMiniCarts.class).getMiniCart(p.getProvidedBy()).addToCart(p,1,source);
		//MallUtil.getCurrentUser().
	}
	
	public void addToFavorit(){
		MallUtil.getCurrentUser().addToFavorite(getAttribute("path"));
	}
	
	public  Product getProduct(){
		return (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
	}

	
	public void goToProduct(){
		Product p = getProduct();
		getAncestorOfType(EXMall.class).showProductCard(p);
	}
	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("cart")){
			addToCart(source);
		}else if(source.getName().equalsIgnoreCase("favorit")){
			addToFavorit();
		}else if(source.getName().equalsIgnoreCase("title") || source.getName().equalsIgnoreCase("img")){
			goToProduct();
		}
		
	}


	@Override
	public void onLogin(String username) {
//		getChild("cart").setDisplay(true);
		getChild("favorit").setDisplay(true);
		
	}

}
