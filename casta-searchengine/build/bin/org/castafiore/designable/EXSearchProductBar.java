package org.castafiore.designable;

import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXSearchProductBar  extends AbstractXHTML implements Event{

	public EXSearchProductBar(String name) {
		super(name);
		
		addChild(new EXInput("searchInput"));
		addChild(new EXIconButton("search", Icons.ICON_SEARCH).removeClass("ui-corner-all").setAttribute("title", "Search Products").addEvent(this, Event.CLICK));
		addChild(new EXIconButton("myCart",Icons.ICON_CART).removeClass("ui-corner-all").setAttribute("title", "Show my shopping cart").setStyle("float", "left").addEvent(this, Event.CLICK));
		try{
			addChild(new EXSelect("currency", new DefaultDataModel<Object>((List)FinanceUtil.getCurrencies())).setStyle("float", "right").setStyle("font-size", "10px").setStyle("margin", "4px").addEvent(this, CHANGE));
			
			String path = (String)getRoot().getConfigContext().get("portalPath");
			String username = MallUtil.getEcommerceMerchant();
			
			Merchant m = MallUtil.getMerchant(username);
			getDescendentOfType(EXSelect.class).setValue(new SimpleKeyValuePair(m.getCurrency(), ""));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getCurrency(){
		return ((KeyValuePair)getDescendentOfType(EXSelect.class).getValue()).getKey();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		
		

		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("currency")){
			
		 	ComponentUtil.iterateOverDescendentsOfType(getAncestorOfType(EXEcommerce.class), CurrencySensitive.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					// TODO Auto-generated method stub
					try{
					((CurrencySensitive)c).changeCurrency();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			
		 	return true;
		}
		EXEcommerce ecommerce = container.getAncestorOfType(EXEcommerce.class);
		String path = (String)getRoot().getConfigContext().get("portalPath");
		String username = MallUtil.getEcommerceMerchant();
		if(container.getName().equals("search")){
			String value = getDescendentOfType(EXInput.class).getValue().toString();
			
			if(StringUtil.isNotEmpty(value)){
				
				
				
				EXCatalogue cat = (EXCatalogue)ecommerce.getPageOfType(EXCatalogue.class);
				if(cat == null){
					cat = new EXCatalogue("");
					ecommerce.getBody().addChild(cat);
				}
				cat.search("fulltext", value,username);
				ecommerce.showPage(EXCatalogue.class);
				return true;
			}
		}else{
			EXCartDetail cart =(EXCartDetail)ecommerce.getPageOfType(EXCartDetail.class);
			if(cart == null){
				cart = new EXCartDetail("");
				ecommerce.getBody().addChild(cart);
			}
			cart.init(ecommerce.getDescendentOfType(EXMiniCart.class));
			ecommerce.showPage(EXCartDetail.class);
			return true;
		}
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("msg")){
			container.alert(request.get("msg"));
		}
		
	}

}
