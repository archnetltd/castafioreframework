package org.castafiore.shoppingmall.ng;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.catalogue.ui.EXProductDetail;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.cart.EXMerchantInfo;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.product.ui.EXMiniCarts;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXProductItemNG extends EXXHTMLFragment implements Event, MallLoginSensitive, CurrencySensitive{

	public EXProductItemNG(String name) {
		super(name, "templates/ng/EXProductItemNG.xhtml");
		addChild(new EXContainer("price","span"));
		
		addChild(new EXContainer("title","a").setStyle("color", "red").addEvent(this, CLICK));
		addChild(new EXContainer("summary","span"));
		addChild(new EXContainer("weight","span"));
		addChild(new EXInput("qty"));
		addChild(new EXContainer("image", "img").addEvent(this, CLICK));
		getDescendentOfType(EXInput.class).setValue("1");
		addChild(new EXContainer("addQty","img").addEvent(this, CLICK).setAttribute("src", "emimg/detail/plus.png"));
		addChild(new EXContainer("delQty","img").addEvent(this, CLICK).setAttribute("src", "emimg/detail/minus.png"));
		addChild(new EXContainer("addToCart","img").setAttribute("title", "Add to cart").addEvent(this, CLICK).setAttribute("src", "emimg/detail/cart.png"));
		addChild(new EXContainer("thumbUp","img").setStyle("display", "none").addEvent(this, CLICK).setAttribute("src", "icons-2/fugue/icons/thumb-up.png").setStyle("width", "24px").setStyle("height", "24px"));
		addChild(new EXContainer("thumbDown","img").setStyle("display", "none").addEvent(this, CLICK).setAttribute("src", "icons-2/fugue/icons/thumb.png").setStyle("width", "24px").setStyle("height", "24px"));
		addChild(new EXContainer("addToWishList","label").setStyle("display", "none").setText( "Add to wish list").setStyle("font-weight", "normal").setStyle("color", "#C22A39").setStyle("font-size", "10px").setStyle("cursor", "pointer").addEvent(this, CLICK));
		addChild(new EXContainer("merchant", "a").setAttribute("href", "#G").addEvent(this, CLICK));
		
		
		setTemplateLocation("templates/ng/EXProductItemNG"+name+".xhtml");
		if(Util.getRemoteUser() != null){
			getChild("addToWishList").setDisplay(true);
			getChild("thumbUp").setDisplay(true);
			getChild("thumbDown").setDisplay(true);
		}
	}
	
	
	public String getTitle(){
		return getChild("title").getAttribute("title");
	}
	
	public Double getPrice(){
		Double d = Double.parseDouble(getAttribute("price"));
		return d;
	}
	
	public Long getDateCreate(){
		return Long.parseLong(getAttribute("date"));
	}
	
	public EXProductItemNG setProduct(Product p){
		setAttribute("date", p.getDateCreated().getTime().getTime() + "");
		getChild("thumbUp").setAttribute("title", p.getLikeIt() + " people liked this product");
		getChild("thumbDown").setAttribute("title", p.getDislikeIt() + " people did not like this product");
		
		setAttribute("price", p.getTotalPrice().doubleValue() + "");
		setAttribute("path", p.getAbsolutePath());
		getChild("price").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), p.getTotalPrice()));
		if(p.getWeight() == null){
			p.setWeight(new BigDecimal(1));
		}
		getChild("weight").setText(StringUtil.toCurrency("KG", p.getWeight()));
		String title = p.getTitle();
		if(!StringUtil.isNotEmpty(title))
			title = "No title";
		if(title.length() > 25){
			title = title.substring(0,25) + "...";
		}
		getChild("title").setAttribute("title",p.getTitle()).setText(title);
		getChild("summary").setText(p.getSummary());
		String imgUrl = p.getImageUrl("");
		if(imgUrl != null){
			if(imgUrl.contains("ecm:")){
				imgUrl = imgUrl;
			}
			getChild("image").setAttribute("src",imgUrl);
		}
		
		getChild("merchant").setText(p.getProvidedBy()).setAttribute("merchant", p.getProvidedBy());
		return this;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("title") || container.getName().equals("image") || container.getName().equals("img")){
			showDetail(container);
			return true;
		}
		
		if(container.getName().equals("merchant")){
			EXMerchantInfo info = new EXMerchantInfo("");
			EXPanel panel = new EXPanel("mm");
			panel.setBody(info);
			info.setMerchant(MallUtil.getMerchant(container.getAttribute("merchant")));
			container.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "3000").setStyle("width", "700px"));
			return true;
		}
		
		if(container.getName().equals("thumbUp")){
			Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			p.thumbUp();
			p.save();
			return true;
		}
		
		if(container.getName().equals("thumbDown")){
			Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			p.thumbDown();
			p.save();
			return true;
		}
		
		 
		EXInput in = getDescendentOfType(EXInput.class);
		int value = Integer.parseInt(in.getValue().toString());
		if(container.getName().equals("addQty")){
			in.setValue((value + 1) + "");
		}else if(container.getName().equals("delQty")){
			if(value > 0){
				in.setValue((value - 1) + "");
			}
		}else if(container.getName().equals("addToCart")){
			Product p = (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			EXMiniCarts carts = getRoot().getDescendentOfType(EXMiniCarts.class);
			if(carts.getChildren().size() == 0){
				carts.addChild(new EXContainer("totalll", "h4").addClass("ui-widget-header").setStyle("margin", "0").setStyle("width", "100%").setStyle("text-align", "center"));
			}
			EXMiniCart cart = getRoot().getDescendentOfType(EXMiniCarts.class).getMiniCart(p.getProvidedBy());
			if(!cart.getTemplateLocation().endsWith("EXMinicartNG.xhtml")){
				cart.setTemplateLocation("templates/ng/EXMinicartNG.xhtml");
				cart.getParent().setStyle("float", "right").setStyle("margin-top", "12px");
				cart.addChild(new EXContainer("images", "div").setStyle("height", "20px"));
				cart.removeClass("cart-widget").setStyle("margin", "0").setStyle("padding", "0").setStyle("margin-top", "0");
				cart.addChild(new EXContainer("checkout", "a").addEvent(new CartDetailEvent(), Event.CLICK).setAttribute("href", "#").setText("<img src=\"blueprint/images/checkout.png\"></img>"));
				
			}
			CartItem item = cart.getItem(p.getAbsolutePath());
			if(item == null){
				cart.getChild("images").addChild(
						
						new EXContainer("img", "img").setStyle("cursor", "pointer").setAttribute("path", p.getAbsolutePath()).addEvent(this, CLICK).setStyle("margin", "4px 4px 0px 4px").setStyle("width", "30px").setStyle("height", "30px").setAttribute("src", p.getImageUrl(""))
						);
			}
			CartItem  citem = cart.addToCart(p, value, container);
		}else{
			Product p = (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			try{
			MallUtil.getCurrentUser().addToFavorite(p.getAbsolutePath());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	public void showDetail(Container source){
		
		Product p =  null;
		if(source.getName().equals("img")){
			p = (Product)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		}else{
			p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		}
		EXPanel panel = new EXPanel("pd", "Product detail");
		panel.setShowFooter(true);
		EXProductDetailNG pd = new EXProductDetailNG("detail");
		//pd.setStyle("width", "586px");
		//pd.setStyle("height", "420px");
		pd.setStyle("overflow-x", "hidden");
		pd.setProduct(p);
		panel.setBody(pd);
		panel.setTitle(p.getTitle());
		//pd.getParent().setStyle("margin", "0");
		PopupContainer pc =getAncestorOfType(PopupContainer.class);
		if(pc == null){
			pc = getRoot().getDescendentOfType(PopupContainer.class);
		}
		panel.setWidth(Dimension.parse("652px"));
		pc.addPopup(panel.setStyle("z-index", "3000"));
		panel.getDescendentByName("content").setAttribute("style", "height: auto; overflow: auto; width: auto; margin: 0px !important; font-weight: normal; padding: 0pt; min-height: 61px;");
		panel.setStyle("border", "none");
		//panel.setHeight(Dimension.parse("470px"));
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "none"));
		
	}

	@Override
	public void onLogin(String username) {
		
		getChild("addToWishList").setDisplay(true);
		getChild("thumbUp").setDisplay(true);
		getChild("thumbDown").setDisplay(true);
		
	}

	@Override
	public void changeCurrency() throws Exception {
		String path = getAttribute("path");
		Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		Merchant m = MallUtil.getMerchant(p.getProvidedBy());
		String currency = FinanceUtil.getCurrentCurrency();
		if(!currency.equals(m.getCurrency())){
			BigDecimal curr = FinanceUtil.convert(p.getTotalPrice(), m.getCurrency(), currency);
			getChild("price").setText(StringUtil.toCurrency(currency,curr));
		}else{
			BigDecimal curr = p.getTotalPrice();
			getChild("price").setText(StringUtil.toCurrency(currency,curr));
		}
		
	}

}
