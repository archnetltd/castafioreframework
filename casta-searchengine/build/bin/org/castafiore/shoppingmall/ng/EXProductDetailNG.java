package org.castafiore.shoppingmall.ng;

import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.cart.EXMerchantInfo;
import org.castafiore.shoppingmall.merchant.EXMerchantCardV2;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.ng.v2.EXMall;
import org.castafiore.shoppingmall.product.ui.EXMiniCarts;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.ui.tabbedpane.TabRenderer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Link;

public class EXProductDetailNG extends EXProductItemNG implements PopupContainer, TabModel , TabRenderer{

	public EXProductDetailNG(String name) {
		super(name);
		getChild("image").setStyle("width", "250px").setStyle("height", "300px");
		setTemplateLocation("templates/ng/EXProductDetailNG.xhtml");
		addChild(new EXOverlayPopupPlaceHolder("popup"));
		getChild("merchant").setStyle("color", "green");
		for(int i = 0; i < 5;i++){
			Container c = new EXContainer("star" + i, "img").setAttribute("src", "emimg/detail/star.png").setStyle("cursor", "pointer");
			addChild(c.addEvent(this, CLICK));
		}
		//getChild("summary").remove();
		EXTabPanel panel = new EXTabPanel("productDetail", this);
		panel.setTabRenderer(this);
		addChild(panel);
		for(int i =0; i < 4; i ++){
			Container c = new EXContainer("tn" + i, "img").addClass("uibutton").setStyle("width", "140px").setStyle("height", "140px").setAttribute("src", "emimg/detail/img1.png").setStyle("cursor", "pointer");
			addChild(c.addEvent(this, CLICK));
		}
//		
//		for(int i =0; i < 4; i ++){
//			Container c = new EXContainer("sp" + i, "img").setAttribute("src", "emimg/detail/img1.png").setStyle("cursor", "pointer");
//			addChild(c.addEvent(this, CLICK));
//			
//			Container cc = new EXContainer("hap" + i, "img").setAttribute("src", "emimg/detail/img1.png").setStyle("cursor", "pointer");
//			addChild(cc.addEvent(this, CLICK));
//		}
//		
//		addChild(new EXContainer("addToCartTxt","label").setText("Add to cart").setAttribute("style", "display: block;color: #026fb9"));
	}

	
	@Override
	public EXProductItemNG setProduct(Product p){
		super.setProduct(p);
		if(getAncestorOfType(EXPanel.class) != null)
			getAncestorOfType(EXPanel.class).setTitle(p.getTitle());
		setAttribute("path", p.getAbsolutePath());
		List<Link> images = p.getImages().toList();
		for(int i =0; i < 4; i ++){
			if(i == 0){
				getChild("tn" + i).setStyleClass("uibutton uibutton_active");
			}else
				getChild("tn" + i).setStyleClass("uibutton");
			if(images.size() > i)
				getChild("tn" + i).setAttribute("src", images.get(i).getUrl()).setStyle("cursor", "pointer");
			else
				getChild("tn" + i).setAttribute("src", "emimg/detail/img1.png").setStyle("cursor", "pointer");
		}
		
		
		getDescendentOfType(EXTabPanel.class).setModel(this);
		
		
		
		
		
		return this;
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("title") || container.getName().equals("image") || container.getName().equals("img")){
			
			return false;
		}
		
		if(container.getName().startsWith("sp")){
			String path = container.getAttribute("path");
			if(StringUtil.isNotEmpty(path) && !path.equalsIgnoreCase("null")){
				Product p= (Product)SpringUtil.getRepositoryService().getFile(container.getAttribute("path"), Util.getRemoteUser());
				setProduct(p);
			}
			return true;
		}
		
		
		if(container.getName().startsWith("star")){
			int index = Integer.parseInt(container.getName().replace("star", "")) + 1;
			Product p = (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			for(int i = 0; i < index;i++){
				getChild("star" + i).setStyle("background", "green");
			}
			
			String u = Util.getRemoteUser();
			if(!StringUtil.isNotEmpty(u)){
				u = "anonymous";
			}
			Directory votes = (Directory)p.getFile("votes");
			if(votes == null){
				votes = p.createFile("votes", Directory.class);
				
			}
			
			String cur = votes.getProperty(u);
			
			if(cur == null){
				cur = "0";
			}
			try{
				Integer.parseInt(cur);
				
			}catch(Exception e){
				cur="0";
			}
			
			votes.setProperty(u, (Integer.parseInt(cur) + index) + "");
			
			p.save();
			return true;
		
		}
		
		
		if(container.getName().startsWith("tn")){
			for(Container c : getChildren()){
				if(c.getName().startsWith("tn")){
					c.setStyleClass("uibutton");
					
				}
			}
			container.setStyleClass("uibutton uibutton_active");
			getChild("image").setAttribute("src", container.getAttribute("src"));
			return true;
			
		}
		
		if(container.getName().equals("merchant")){
			Merchant m = MallUtil.getMerchant(container.getAttribute("merchant"));
			//EXMerchantInfo info = new EXMerchantInfo("");
			EXMerchantCardV2 info = new EXMerchantCardV2("sd", m);
			EXPanel panel = new EXPanel("mm", m.getCompanyName());
			panel.setBody(info);
			//info.setMerchant(m);
			container.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "3000").setStyle("width", "704px"));
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
			if(container.getAncestorOfType(EXMall.class) != null){
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
				CartItem  citem = cart.addToCart(p, value,container);
			}else{
				Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
				getRoot().getDescendentOfType(EXMiniCart.class).addToCart(p, 1,container);	
			}
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


	@Override
	public void addPopup(Container popup) {
		getChild("popup").addChild(popup);
		
	}

	private String[] labels = new String[]{"Description","Specifications", "Customer Reviews","Same Provider", "Related Products", };

	@Override
	public int getSelectedTab() {
		
		return 0;
	}


	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		
		
		
		if(index == 3){
			Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			Container c = new EXContainer("", "div");
			
			List<Product> sameProvider = MallUtil.getMerchant(p.getProvidedBy()).getManager().getMyProducts(Product.STATE_PUBLISHED);
			for(int i = 0; i < sameProvider.size();i++){
				
				Product pp = sameProvider.get(i);
				if(pp.getAbsolutePath().equalsIgnoreCase(getAttribute("path")))
					continue;
				String url = pp.getImageUrl("");
				if(!StringUtil.isNotEmpty(url)){
					url = "emimg/detail/img1.png";
				}
				Container cc = new EXContainer("sp" + i, "img").setAttribute("src", "emimg/detail/img1.png").setStyle("cursor", "pointer");
				c.addChild(cc.setAttribute("src",url).setAttribute("path", pp.getAbsolutePath()));
			}
			return c.setStyle("min-height", "250px");
			
		}else if(index == 4){
			Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			Container c = new EXContainer("", "div");
			
			List<Product> sameProvider = MallUtil.getMerchant(p.getProvidedBy()).getManager().getMyProducts(Product.STATE_PUBLISHED);
			for(int i = 0; i < sameProvider.size();i++){
				
				Product pp = sameProvider.get(i);
				if(pp.getAbsolutePath().equalsIgnoreCase(getAttribute("path")))
					continue;
				String url = pp.getImageUrl("");
				if(!StringUtil.isNotEmpty(url)){
					url = "emimg/detail/img1.png";
				}
				Container cc = new EXContainer("hap" + i, "img").setAttribute("src", "emimg/detail/img1.png").setStyle("cursor", "pointer");
				c.addChild(cc.setAttribute("src",url).setAttribute("path", pp.getAbsolutePath()));
			}
			return c.setStyle("min-height", "250px");
		}else if(index == 0){
			if(StringUtil.isNotEmpty(getAttribute("path"))){
				Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
				return new EXContainer("summary", "div").setText(p.getSummary()).setStyle("min-height", "250px");
			}else{
				return new EXContainer("sd", "div");
			}
			
		}else if(index == 2){
			return new EXContainer("", "div").addChild(new EXContainer("sas", "h5").setText("Coming soon")).setStyle("min-height", "250px");
		}else if(index == 1){
			Product p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			List<KeyValuePair> vnps = p.getCategories();
			EXFieldSet fs = new EXFieldSet("categories", "Specifications", false);
			for(KeyValuePair vk : vnps)
				fs.addField(vk.getKey(), new EXLabel("",vk.getValue()));
			
			return new EXContainer("", "div").addChild(fs).setStyle("min-height", "250px");
			//return new EXContainer("sas", "h3").setText("Coming soon");
		}else
			return null;
			
		
	}


	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}


	@Override
	public int size() {
		return labels.length;
	}


	public Container getComponentAt(TabPanel pane, TabModel model,
			int index) {
		Container tab = ComponentUtil.getContainer("tt", "a" ,"", null);
		tab.setAttribute("href", "#tabs-" + index).setStyle("font-size", "11px");
		tab.setText(model.getTabLabelAt(pane, index, false));
		EXContainer tTab = new EXContainer("", "li");
		tTab.setAttribute("class", EXTabPanel.TABS_INACTIVE_TAB_STYLE);
		tTab.addChild(tab);
		return tTab;
	}

	public void onSelect(TabPanel pane, TabModel model, int index,
			Container tab) {
		tab.setAttribute("class", EXTabPanel.TABS_ACTIVE_TAB_STYLE);	
	}

	public void onDeselect(TabPanel pane, TabModel model, int index,Container tab) {
		tab.setAttribute("class", EXTabPanel.TABS_INACTIVE_TAB_STYLE);	
	}
	

	
	
	
	

}
