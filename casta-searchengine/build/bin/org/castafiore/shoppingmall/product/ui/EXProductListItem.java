package org.castafiore.shoppingmall.product.ui;


import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.shoppingmall.util.list.ListItem;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;
import org.hibernate.criterion.Restrictions;


public class EXProductListItem extends AbstractListItem implements ListItem<Product>, Event, ComponentVisitor{

	private boolean open = false;
	public EXProductListItem(String name) {
		super(name);
		
//		if((getRoot() instanceof EXSearchEngineApplication)== false){
//			super.getChildren().clear();
//		}
		addChild(new EXContainer("to_code", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("code","div")));
		addChild(new EXContainer("td_title", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("title","a").addEvent(this, CLICK).setAttribute("href", "#").setText("").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_qty", "td").setStyle("vertical-align","top" ).setStyle("text-align", "right").addChild(new EXContainer("qty","div")));
		addChild(new EXContainer("td_price", "td").setStyle("vertical-align","top" ).setStyle("text-align", "right").addChild(new EXContainer("price","div")));
	}

	
	public void open(){
		if(!open){
			EXProductList list = getAncestorOfType(EXProductList.class);
			ComponentUtil.iterateOverDescendentsOfType(list, EXProductListItem.class,this);
			Container code = getDescendentByName("code");
			Container title = getDescendentByName("td_title");
			Container userImg = getDescendentByName("userimg");
			EXProductListDetail detail = getDescendentOfType(EXProductListDetail.class);
			if(userImg == null){
				userImg = new EXContainer("userimg", "img").setAttribute("style", "width: 60px; height: 60px; margin-top: 0.8em;display:block").setAttribute("src", "http://www.space.com/common/forums/images/avatars/gallery/All/Avatar_gear.jpg");
				code.getParent().addChild(userImg);
			}
			
			userImg.setDisplay(true);
			Product p = getItem();
			userImg.setAttribute("src", p.getImageUrl(""));
			
			if(detail == null){
				detail = new EXProductListDetail("listDetail");
				
				title.addChild(detail);
				detail.setProduct(getItem());
			}
			detail.setDisplay(true);

			open = true;
		}
	}
	
	public void toggle(){
		if(open){
			close();
		}else{
			open();
		}
	}
	
	
	public void deleteFavorit(){
		if(isChecked()){
			String path = getAttribute("path");
			QueryParameters params = new QueryParameters().setEntity(Shortcut.class).addRestriction(Restrictions.eq("reference", path));
			params.addSearchDir(ShoppingMallUser.USER_FAVORIT_DIR.replace("$user",Util.getRemoteUser()));
			File f = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser()).get(0);
			Directory parent = f.getParent();
			f.remove();
			parent.save();
			//remove();
			setDisplay(false);
		}
	}
	
	public void close(){
		if(open){
			Container userImg = getDescendentByName("userimg");
			if(userImg != null){
				userImg.setDisplay(false);
			}
			EXProductListDetail detail = getDescendentOfType(EXProductListDetail.class);
			if(detail != null){
				detail.setDisplay(false);
			}
			open = false;
		}
		
	}
	
	@Override
	public Product getItem() {
		Product comment = (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		
		return comment;
	}

	

	@Override
	public void setItem(Product file) {
		setAttribute("path", file.getAbsolutePath());
		getDescendentByName("code").setText(file.getCode());
		if(StringUtil.isNotEmpty(file.getTitle()))
			getDescendentByName("title").setText(file.getTitle());
		else
			getDescendentByName("title").setText("No title");
		getDescendentByName("price").setText(file.getTotalPrice()==null?"0":file.getTotalPrice().toString());
		getDescendentByName("qty").setText(file.getCurrentQty()==null?"0":file.getCurrentQty().toPlainString());
		
		Container qty = getDescendentByName("qty");
		//if(getAncestorOfType(OSApplication.class) != null){
			qty.addEvent(this, Event.DOUBLE_CLICK);
		//}
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("qty")){
			String text = container.getText();
			container.setText("");
			container.addChild(new EXInput("",text).addEvent(this, BLUR));
			return true;
		}else if(container instanceof EXInput){
			String qty = ((EXInput)container).getValue().toString();
			try{
				int iqty = Integer.parseInt(qty);
				if(iqty >= 0){
					Product p = getItem();
					p.setCurrentQty(new BigDecimal(iqty));
					p.save();
					container.getParent().setText(iqty + "");
					container.remove();
				}else{
					container.addClass("ui-state-error");
				}
			}catch(Exception e){
				container.addClass("ui-state-error");
			}
			return true;
		}
		toggle();
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doVisit(Container c) {
		if(c.getId().equals(getId())){
			
		}else{
			((EXProductListItem)c).close();
		}
		
	}

}
