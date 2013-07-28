package org.castafiore.shoppingmall.home;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXShoppingMallPortal extends EXXHTMLFragment implements Event{
	
	private final static String[] MENU = new String[]{"cars", "house", "kitchen", "beauty", "services", "gifts", "souvenirs", "agro", "comfort"};
	private final static String[] MENU_TXT = new String[]{"Cars and Vehicles", "House Items", "Kitchen Items", "Beauty Products", "Services", "Gifts", "Souvenirs of Mauritius", "Agro", "Your comfort"};

	public EXShoppingMallPortal(String name) {
		super(name, "templates/EXShoppingMallPortal.xhtml");
		addChild(new EXInput("searchField").addClass("ac_input search_query"));
		addChild(new EXContainer("search", "span").setText("Go").addEvent(this, Event.CLICK));
		
		addChild(new EXContainer("logo", "a").addEvent(this, Event.CLICK).setAttribute("href", "#").addChild(new EXContainer("", "img").setAttribute("src", "http://cms.template-help.com/prestashop_31061/img/logo.jpg")));
		addChild(new EXSelect("currency",new DefaultDataModel<Object>().addItem("Rupee").addItem("Dollar").addItem("Euro").addItem("Pound")).addEvent(this, Event.CHANGE));

		addChild(new EXContainer("bigAdvertisement", "a").addEvent(this, Event.CLICK).setAttribute("href", "#").addChild(new EXContainer("", "img").setAttribute("src", "http://cms.template-help.com/prestashop_31061/modules/blockbanner1/advertising_custom.jpg")));
		
		addChild(new EXContainer("smallOne", "a").addEvent(this, Event.CLICK).setAttribute("href", "#").addChild(new EXContainer("", "img").setAttribute("src", "http://cms.template-help.com/prestashop_31061/modules/blockbanner2/advertising_custom.jpg")));
		
		addChild(new EXContainer("smallTwo", "a").addEvent(this, Event.CLICK).setAttribute("href", "#").addChild(new EXContainer("", "img").setAttribute("src", "http://cms.template-help.com/prestashop_31061/modules/blockbanner2/advertising_custom.jpg")));
		
		
		for(int i =0; i < MENU.length; i++){
			addChild(new EXContainer(MENU[i], "a").setText(MENU_TXT[i]).addEvent(this, Event.CLICK));
		}
		
		
		// TODO Auto-generated constructor stub
	}
	
	public void addToCart(Container source){
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("logo")){
			
		}else if(container.getName().equals(""))
		{
			
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
