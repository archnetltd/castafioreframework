package org.castafiore.shoppingmall.product.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.shoppingmall.user.ui.EXMerchantCardLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Link;

public class EXProductSearchResultItem extends EXXHTMLFragment implements EventDispatcher, MallLoginSensitive{

	public EXProductSearchResultItem(String name) {
		super(name, "templates/EXProductSearchResultItem.xhtml");
		addClass("msr").addClass("exsi");
		
		
		addChild(new EXProductCardLink("title"));
		addChild(new EXContainer("price", "h3"));
		addChild(new EXMerchantCardLink("provider").setStyle("float", "left").setStyle("margin", "8px 0"));
		addChild(new EXContainer("img", "img").addClass("span-3").setStyle("height", "90px"));
		addChild(new EXContainer("description", "div").addClass("span-7").setStyle("height", "90px").setStyle("overflow", "hidden"));
		addChild(new EXContainer("cart", "div").setStyle("background-image", "url(icons-2/fugue/icons/shopping-basket.png)").addEvent(DISPATCHER, Event.CLICK));
		if(Util.getRemoteUser() != null){
			
			addChild(new EXContainer("favorit", "div").setStyle("background-image", "url(icons-2/fugue/icons/heart.png)").addEvent(DISPATCHER, Event.CLICK));
		}
	}
	
	
	public void setProduct(Product product){
		setAttribute("path", product.getAbsolutePath());
		getDescendentOfType(EXProductCardLink.class).setProduct(product);
		getChild("description").setText(product.getSummary());
		FileIterator<Link> iter = product.getImages();
		if(iter != null && iter.hasNext()){
			Link f = iter.next();
			String url =  f.getUrl();
			getChild("img").setAttribute("src", url);
			getChild("img").setDisplay(true);
		}else{
			getChild("img").setDisplay(false);
		}
		getDescendentOfType(EXMerchantCardLink.class).setMerchantUsername(product.getProvidedBy());
		getChild("price").setText(product.getTotalPrice().toPlainString());
	}
	
	public void addToFavorit(){
		MallUtil.getCurrentUser().addToFavorite(getAttribute("path"));
	}
	
	public void addToCart(Container source){
		Product p = getProduct();
		getAncestorOfType(EXMall.class).getDescendentOfType(EXMiniCarts.class).getMiniCart(p.getProvidedBy()).addToCart(p,1,source);
		
	}


	
	public  Product getProduct(){
		return (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
	}
	@Override
	public void executeAction(Container source){
		if(source.getName().equalsIgnoreCase("heart")){
			addToFavorit();
		}else if(source.getName().equalsIgnoreCase("cart")){
			addToCart(source);
		}
	}


	@Override
	public void onLogin(String username) {
		addChild(new EXContainer("favorit", "div").setStyle("background-image", "url(icons-2/fugue/icons/heart.png)").addEvent(DISPATCHER, Event.CLICK));
		
	}

}
