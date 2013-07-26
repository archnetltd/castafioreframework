package org.castafiore.designable;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.product.EXProduct;
import org.castafiore.shoppingmall.ng.EXProductDetailNG;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;

public class EXRecentProducts extends AbstractXHTML implements Event{

	public EXRecentProducts(String name) {
		super(name);
		addClass("cart-widget");
		setStyle("margin-top", "30px");
		addChild(new EXContainer("title", "span").setText("Recent products"));
		addChild(new EXContainer("recentProductsList", "ul").addClass("ui-widget-content").setStyle("padding", "10px 30px").setStyle("margin", "0"));
	}
	
	
	public void setProducts(List<Product> products){
		Container ul =getChild("recentProductsList");
		ul.getChildren().clear();
		ul.setRendered(false);
		for(Product p : products){
			Container li = new EXContainer("", "li").addChild(new EXContainer("", "a").setAttribute("path", p.getAbsolutePath()).addEvent(this, CLICK).setAttribute("href", "#product").setText(p.getTitle()));
			ul.addChild(li);
		}
	}

	public void showDetail(Container source){
		
		Product p =  null;
		
		p= (Product)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		
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
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
//		String path = container.getAttribute("path");
//		Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
//		EXEcommerce ecom = container.getAncestorOfType(EXEcommerce.class);
//		EXProduct uip = (EXProduct)ecom.getPageOfType(EXProduct.class);
//		if(uip == null){
//			uip = new EXProduct("productCard");
//			ecom.getBody().addChild(uip);
//		}
//		uip.setProduct(p);
//		ecom.showPage(uip.getClass());
		showDetail(container);
		return true;
		
		
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
