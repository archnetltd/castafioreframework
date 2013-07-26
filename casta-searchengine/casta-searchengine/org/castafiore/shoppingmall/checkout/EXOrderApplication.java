package org.castafiore.shoppingmall.checkout;

import java.util.Map;

import org.castafiore.security.ui.OnLoginHandler;
import org.castafiore.shoppingmall.orders.CustomerPaymentCellRenderer;
import org.castafiore.shoppingmall.orders.CustomerPaymentsModel;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXOrderApplication extends EXApplication implements  Event, PopupContainer{

	public EXOrderApplication() {
		super("orders");
		
		EXXHTMLFragment fragment = new EXXHTMLFragment("login", "templates/orders/EXOrderApplicationLogin.xhtml");
		fragment.addChild(new EXInput("fsNumber").addClass("form-login").setStyle("width", "342px"));
		fragment.addChild(new EXContainer("login", "a").setText("<img width='103' height='42' style='margin-left: 90px;' src=blueprint/images/login-btn.png></img>").addEvent(this, Event.CLICK));
		addChild(fragment);
		
//		String user = Util.getRemoteUser();
//		if(user== null){
//			addChild(new EXLoginForm("login").addOnLoginHandler(this).setRegister(false));
//		}else{
//			onLogin(this, user);
//		}
	}
	
	
	
	



	public void onLogin(Application app, String username) {
		this.getChildren().clear();
		setRendered(false);
		addChild(new EXOverlayPopupPlaceHolder("ov"));
		EXTable table = new EXTable("t", new CustomerPaymentsModel(username));
		table.setCellRenderer(new CustomerPaymentCellRenderer());
		
		addChild(table.addClass("ui-widget-content"));
		
	}







	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}







	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String FS = getDescendentOfType(EXInput.class).getValue().toString();
		if(StringUtil.isNotEmpty(FS)){
			onLogin(this, FS);
		}
		
		return true;
	}







	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void addPopup(Container popup) {
		getChild("ov").addChild(popup);
		
	}

}
