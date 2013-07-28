package org.castafiore.shoppingmall.ng;

import java.util.List;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXSearchProductBarNG extends EXXHTMLFragment implements Event{

	public EXSearchProductBarNG(String name) {
		super(name, "templates/ng/EXSearchProductBarNG.xhtml");
		addChild(new EXInput("searchInput"));
		//addChild(new EXButton("search", "Search").setStyle("background-image", "url('emalltheme/statePaleRed.png')").setStyleClass("ui-state-default ui-corner-all fg-button-small").addEvent(this, Event.CLICK));
		
		addChild(new EXContainer("search", "a").setAttribute("title", "Search").addEvent(this, CLICK).setAttribute("href", "#").setText("<img src=\"http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/24/search-icon.png\">"));
		//addChild(new EXIconButton("myCart",Icons.ICON_CART).setStyleClass("ui-state-default ui-corner-all").setStyle("float", "left").setStyle("margin", "2px").setStyle("padding", "3px").addEvent(this, Event.CLICK));
		try{
			addChild(new EXSelect("currency", new DefaultDataModel<Object>((List)FinanceUtil.getCurrencies())).setStyle("float", "right").setStyle("font-size", "10px").addEvent(this, CHANGE));
			getDescendentOfType(EXSelect.class).setValue(new SimpleKeyValuePair("MUR", ""));
		}catch(Exception e){
			e.printStackTrace();
		}
		//addChild(new EXContainer("skinning", "style"));
		
		//
	}
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "block")).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("search")){
			String value = getDescendentOfType(EXInput.class).getValue().toString();
			//List<Product> products = MallUtil.getCurrentMall().searchProducts("full:" + value, 0	, 10);
			getRoot().getDescendentOfType(EXCatalogueNG.class).search("full:" + value, "Blue");
		}else{
			ComponentUtil.iterateOverDescendentsOfType(getRoot(), CurrencySensitive.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					try{
					((CurrencySensitive)c).changeCurrency();
					}catch(Exception e){
						
					}
					
				}
			});
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "none"));
		
	}
	
	

}
