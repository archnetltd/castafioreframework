package org.castafiore.designable.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.AbstractXHTML;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXCatalogue;
import org.castafiore.designable.EXEcommerce;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Link;

public class EXProduct extends AbstractXHTML implements Event, CurrencySensitive{

	public EXProduct(String name) {
		super(name);
		addChild(new EXContainer("title", "h1"));
		addChild(new EXContainer("price", "span"));
		addChild(new EXContainer("description", "div").addClass("ui-widget"));
		addChild(new EXContainer("image", "img").setStyle("width", "280px").setStyle("-moz-box-shadow", "5px 5px 5px #333333").setStyle("box-shadow", "5px 5px 5px #333333").setStyle("webkit-box-shadow", "5px 5px 5px #333333"));
		addChild(new EXContainer("thumbnails", "ul"));
		addChild(new EXButton("addToCart", "Add to cart").setStyle("width", "130px").setStyleClass("ui-state-default ui-corner-all fg-button-small").addEvent(this, Event.CLICK));
		addChild(new EXButton("continueShopping", "Continue shopping").setStyle("width", "175px").setStyle("float", "none").setStyleClass("ui-state-default ui-corner-all fg-button-small").addEvent(this, Event.CLICK));
	}
	
	public void setProduct(Product p){
		setAttribute("path", p.getAbsolutePath());
		getChild("title").setText(p.getTitle());
		
		Merchant m = MallUtil.getMerchant(MallUtil.getEcommerceMerchant());
		String currency = FinanceUtil.getCurrentCurrency();
		if(!currency.equals(m.getCurrency())){
			try{
				BigDecimal curr = FinanceUtil.convert(p.getTotalPrice(), m.getCurrency(), currency);
				getChild("price").setText(StringUtil.toCurrency(currency,curr));
			}catch(Exception e){
				BigDecimal curr = p.getTotalPrice();
				getChild("price").setText(StringUtil.toCurrency(m.getCurrency(),curr));
			}
		}else{
			BigDecimal curr = p.getTotalPrice();
			getChild("price").setText(StringUtil.toCurrency(currency,curr));
		}
		
		//getChild("price").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),p.getTotalPrice()));
		getChild("description").setText(p.getSummary());
		getChild("image").setAttribute("src", p.getImageUrl(""));
		List<Link> images = p.getImages().toList();
		Container t = getChild("thumbnails");
		t.getChildren().clear();
		t.setRendered(false);
		if(images.size() > 1){
			for(Link l : images){
				Container li = new EXContainer("", "li").addChild(new EXContainer("a", "a").setAttribute("href", "#image").addEvent(this, CLICK).addChild(new EXContainer("", "img").setStyle("width", "66px").setStyle("height", "66px").setAttribute("src", l.getUrl())));
				t.addChild(li);
			}
		}
		
		if(p.getCurrentQty()==null || p.getCurrentQty().doubleValue() <=0){
			getChild("addToCart").setText("SOLD OUT !!").setStyle("font-weight", "bold").setStyle("font-size", "15px").addClass("ui-state-error").removeClass("ui-state-default").getEvents().clear();
			
		}else{
			getChild("addToCart").getEvents().clear();
			getChild("addToCart").setText("Add to cart").setStyle("font-weight", "normal").setStyle("font-size", "12px").addClass("ui-state-default").removeClass("ui-state-error").addEvent(this, Event.CLICK);
		}
		
		addOptions(p);
	}
	
	
	private void addOptions(Product product){
		try{
			BinaryFile bf = product.getOption();
			if(bf != null){
				Container c = DesignableUtil.buildContainer(bf.getInputStream(), false);
				if(c.getDescendentOfType(EXDynaformPanel.class) != null && c.getDescendentOfType(EXDynaformPanel.class).getFields().size() > 0){
					c.setAttribute("path", product.getAbsolutePath());
					c.setAttribute("qty", 1 + "");
					c.getDescendentOfType(EXDynaformPanel.class).addButton(new EXButton("saveoptions", "Save"));
					c.getDescendentOfType(EXDynaformPanel.class).addButton(new EXButton("canceloptions", "Cancel"));
					
					c.setStyle("z-index", "3001");
					c.getDescendentByName("saveoptions").setAttribute("qty", 1 + "").addEvent(this, CLICK).setAttribute("path", product.getAbsolutePath());
					c.getDescendentByName("canceloptions").addEvent(this, CLICK);
					c.getDescendentByName("titleBar").setStyle("display", "none");
					c.getDescendentByName("panelFooter").setStyle("display", "none");
					c.getDescendentOfType(EXDynaformPanel.class).setStyle("width", "380px").removeClass("ui-widget-header").setStyle("background", "beige");
					c.setName("options");
					addChild(c);	
					
				}
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Container  root = container.getRoot();
		if(container.getName().equals("addToCart")){
			
			Panel panel = container.getAncestorOfType(Panel.class);
			String path = getAttribute("path");
			Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			Container options = getDescendentByName("options");
			root.getDescendentOfType(EXMiniCart.class).addToCart(p,1,container, options);
			Container parent = getParent();
			remove();
			parent.setRendered(false);
			if(panel != null){
				panel.getParent().setRendered(false);
				panel.remove();
			}
		}else if(container.getName().equals("continueShopping")){
			container.getAncestorOfType(EXEcommerce.class).showPage(EXCatalogue.class);
		}else{
			String src = container.getChildByIndex(0).getAttribute("src");
			getChild("image").setAttribute("src", src);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeCurrency() throws Exception {
		String path = getAttribute("path");
		Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		Merchant m = MallUtil.getMerchant(MallUtil.getEcommerceMerchant());
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
