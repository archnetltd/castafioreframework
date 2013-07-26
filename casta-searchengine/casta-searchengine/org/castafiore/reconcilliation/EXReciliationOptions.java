package org.castafiore.reconcilliation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.castafiore.accounting.Account;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXReciliationOptions extends EXContainer {

	public EXReciliationOptions(String name) {
		super(name, "div");
		setStyle("background-color", "steelblue").setStyle("border", "solid 1px silver").setStyle("padding", "5px").setStyle("position", "absolute").setStyle("width", "600px");
	}
	
	
	
	public void filterItemsFromCode(List completeList, String code){
		
		int i = 0;
		for(Object o : completeList){
			Object[] as = (Object[])o;
			if(as[4] != null && code.equalsIgnoreCase(as[4].toString())){
				EXXHTMLFragment fragment = new EXXHTMLFragment("","templates/ReconcilliationTemplate.xhtml");
				fragment.setStyle("height", "18px").setStyle("cursor", "pointer");
				if((i %2) == 0){
					fragment.setStyle("background-color", "white");
				}else{
					fragment.setStyle("background-color", "transparent");
				}
				fragment.addChild(new EXContainer("name", "span").setText(as[1] !=null? as[1].toString() :"" + " "+ as[2]!= null ? as[2].toString() :""));
				fragment.addChild(new EXContainer("refNo", "span").setText(as[4].toString().split("-")[0]));
				
				if(as[6] != null)
					fragment.addChild(new EXContainer("price", "span").setText(StringUtil.toCurrency("MUR",new BigDecimal(as[6].toString()))));
				else
					fragment.addChild(new EXContainer("price", "span").setText("MUR -"));
				
				

				fragment.addChild(new EXContainer("accNo", "span").setText(as[5] !=null? as[5].toString() :""));
				fragment.setAttribute("username", as[0].toString());
				fragment.setAttribute("price",  as[6] != null ? as[6].toString(): "0");
				i++;
				
				addChild(fragment);
				eventDecorate(fragment);
			}
		}
		
		if(i ==0){
			for(Object o : completeList){
				Object[] as = (Object[])o;
				if(true){
					EXXHTMLFragment fragment = new EXXHTMLFragment("","templates/ReconcilliationTemplate.xhtml");
					fragment.setStyle("height", "18px").setStyle("cursor", "pointer");
					if((i %2) == 0){
						fragment.setStyle("background-color", "white");
					}else{
						fragment.setStyle("background-color", "transparent");
					}
					fragment.addChild(new EXContainer("name", "span").setText(as[1] !=null? as[1].toString() :"" + " "+ as[2]!= null ? as[2].toString() :""));
					fragment.addChild(new EXContainer("refNo", "span").setText(as[4].toString().split("-")[0]));
					
					if(as[6] != null)
						fragment.addChild(new EXContainer("price", "span").setText(StringUtil.toCurrency("MUR",new BigDecimal(as[6].toString()))));
					else
						fragment.addChild(new EXContainer("price", "span").setText("MUR -"));
					
					
					fragment.addChild(new EXContainer("accNo", "span").setText(as[5] !=null? as[5].toString() :""));
					fragment.setAttribute("username", as[0].toString());
					fragment.setAttribute("price",  as[6] != null ? as[6].toString(): "0");
					i++;
					
					addChild(fragment);
					eventDecorate(fragment);
				}
			}
		}
	}
	
	
	public void eventDecorate(Container fragment){
		fragment.addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.addClass("ui-state-hover");
				
			}
		},Event.MOUSE_OVER);
		
		fragment.addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.removeClass("ui-state-hover");
				
			}
		},Event.MOUSE_OUT);
		
		fragment.addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				EXReciliationOptions option = container.getAncestorOfType(EXReciliationOptions.class);
				int row = Integer.parseInt(option.getAttribute("realrow"));
				
				EXTable table = container.getAncestorOfType(EXTable.class);
				ReconciliationModel model = (ReconciliationModel) table.getModel();
				
				String username = container.getAttribute("username");
				String names = container.getChild("name").getText(false);
				String refNo = container.getChild("refNo").getText(false);
				String accNo = container.getChild("accNo").getText(false);
				String amount = container.getChild("price").getText(false);
				double iAmount = Double.parseDouble(container.getAttribute("price"));
				
				container.getAncestorOfType(EXReciliationOptions.class).setDisplay(false);
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}
		},Event.CLICK);
	}

}
