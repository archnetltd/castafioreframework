package org.castafiore.appstore.ui;

import java.util.Map;

import org.castafiore.appstore.AppPackage;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXApplicationPackageThumbnail extends EXXHTMLFragment implements Event{

	public EXApplicationPackageThumbnail(String name) {
		super(name, "templates/EXApplicationPackageThumbnail.xhtml");
		addChild(new EXContainer("title", "label").setStyle("font-weight", "bold"));
		addChild(new EXContainer("description", "p"));
		addChild(new EXIconButton("add", Icons.ICON_PLUSTHICK).setStyle("float", "left"));
		addChild(new EXContainer("price", "label").setStyle("float", "left").setStyle("padding", "3px"));
		addEvent(this, Event.CLICK);
	}
	
	
	public void setApplication(AppPackage app){
		setAttribute("path", app.getAbsolutePath());
		getChild("title").setText(app.getTitle());
		getChild("description").setText(app.getSummary());
		if(app.getTotalPrice().doubleValue() == 0){
			getChild("price").setText("Free");
		}else{
			getChild("price").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), app.getTotalPrice()));
		}
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getAncestorOfType(EXAppStore.class).showApplication(getAttribute("path"));
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
