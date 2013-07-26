package org.castafiore.searchengine.top;

import java.util.Map;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.middle.EXBody;
import org.castafiore.shoppingmall.merchant.EXMerchantRotatorPanel;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.js.Expression;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.StringUtil;

public class EXSearchBar extends EXContainer implements EventDispatcher, Event{

	public EXSearchBar(String name) {
		super(name, "div");
		addClass("prepend-top").addClass("EXSearchBar");
		addChild(new EXContainer("logoarea", "div").addClass("logoarea").addClass("span-4").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("mainback", "button").addClass("main-back").setText("").addEvent(DISPATCHER, Event.CLICK));
		EXSelect searchTypes = new EXSelect("searchTypes", new DefaultDataModel<Object>().addItem("Products", "Shops"));
		
		addChild(searchTypes.addEvent(this, Event.CHANGE));
		searchTypes.setValue("Products");
		addChild(new EXInput("searchinput").addEvent(this, Event.KEY_PRESS).addClass("mallsearchinput").addClass("span-14"));
		addChild(new EXContainer("search", "button"));
		getChild("search").addEvent(DISPATCHER, Event.CLICK);
	}


	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("search") || source.getName().equalsIgnoreCase("searchinput")){
			String search = getDescendentOfType(EXInput.class).getValue().toString();
			if(StringUtil.isNotEmpty(search)){
				
				String type = getDescendentOfType(EXSelect.class).getValue().toString();
				if(type.equalsIgnoreCase("Shops")){
					getAncestorOfType(EXMall.class).showSearchMerchantResults("full:" + search);
				}else{
					getAncestorOfType(EXMall.class).showSearchResults("full:" + search);
					
				}
			}
		}else if(source.getName().equalsIgnoreCase("logoarea")){
			source.getAncestorOfType(EXMall.class).showPublicHome();
		}else if(source.getName().equalsIgnoreCase("mainback")){
			getAncestorOfType(EXMall.class).getWorkingSpace().back();
		}else if(source.getName().equals("searchTypes")){
			EXSelect select = (EXSelect)source;
			if(select.getValue().equals("Products")){
				getAncestorOfType(EXMall.class).getDescendentOfType(EXBody.class).getDescendentOfType(EXMerchantRotatorPanel.class).setDisplay(false);
				getAncestorOfType(EXMall.class).getActionPanel().showOnly("categories");
			}else{
				getAncestorOfType(EXMall.class).getDescendentOfType(EXBody.class).getDescendentOfType(EXMerchantRotatorPanel.class).setDisplay(true);
				getAncestorOfType(EXMall.class).getActionPanel().setDisplay(false);
			}
		}
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		
		ClientProxy p = new ClientProxy("#mainLoading").setStyle("display", "block");
		ClientProxy all =container.clone().mergeCommand(p).makeServerRequest(this);
		container.IF(new Expression("event.keyCode==13"), all,container.clone());
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		System.out.println(request.get("code"));
		executeAction(container);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		ClientProxy p = new ClientProxy("#mainLoading").setStyle("display", "none");
		container.mergeCommand(p);
		
	}
	
	
	

}
