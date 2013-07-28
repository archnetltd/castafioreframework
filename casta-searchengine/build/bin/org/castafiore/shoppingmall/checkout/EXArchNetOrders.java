package org.castafiore.shoppingmall.checkout;

import java.util.List;

import org.castafiore.security.ui.EXLoginForm;
import org.castafiore.security.ui.OnLoginHandler;
import org.castafiore.shoppingmall.orders.EXCustomerViewOrdersPanel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXArchNetOrders extends EXApplication implements OnLoginHandler{

	public EXArchNetOrders() {
		super("adminorders");
		String user = Util.getRemoteUser();
		if(user== null){
			addChild(new EXLoginForm("login").addOnLoginHandler(this).setRegister(false));
		}else{
			onLogin(this, user);
		}
	}
	
	
	@Override
	public void onLogin(Application app, String username) {
		if(!username.equals("archnet")){
			throw new UIException("Get the hell out of here");
		}
		this.getChildren().clear();
		setRendered(false);
		EXCustomerViewOrdersPanel panel = new EXCustomerViewOrdersPanel("Panel", "Orders");
		
		addChild(panel);
		QueryParameters params = new QueryParameters().setEntity(Order.class);
		params.addRestriction(Restrictions.in("status", new Integer[]{10,12}));
		List orders = SpringUtil.getRepositoryService().executeQuery(params, username);
		panel.init(orders, "Orders to qualify by archnet");
		
	}

}
