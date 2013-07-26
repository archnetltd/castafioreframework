package org.castafiore.shoppingmall.merchant;

import java.math.BigDecimal;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.StringUtil;

public class EXRedeemPanel extends EXContainer implements EventDispatcher{
	public EXRedeemPanel() {
		super("EXRedeemPanel", "div"); 
		
		Merchant merchant = MallUtil.getCurrentMerchant();
		
		addChild(new EXContainer("", "h3").addClass("MessageListTitle"));
		addChild(new EXContainer("btnCtn", "div").addClass("oo-btn").addClass("btnCtn"));
		
		//Container redeem = new EXContainer("redeem", "button").setText("Redeem Credits").addEvent(DISPATCHER, Event.CLICK);
		
		//getChild("btnCtn").addChild(redeem);
		
		addChild(new EXPagineableTable("", new EXTable("", new RedeemTableModel())).addClass("ex-content"));
	}

	
	public void showRedeemList(){
	
		boolean found = false;
		for(Container c : getChildren()){
			if(c.getTag().equalsIgnoreCase("h3")){
				continue;
			}
			if( c instanceof EXPagineableTable){
				c.setDisplay(true);
				found = true;
			}else if(c .getName().equals("btnCtn")){
				c.setDisplay(true);
			}else{
				c.setDisplay(false);
			}
		}
		
		if(!found){
			addChild(new EXPagineableTable("", new EXTable("", new RedeemTableModel())).addClass("ex-content"));
		}
		
		
		
	}

	@Override
	public void executeAction(Container source) {
		
//		if(source.getName().equals("redeem")){
			//BigDecimal tored = MallUtil.getCurrentMerchant().getCreditsToRedeem();
//			if(tored == null || tored.doubleValue() <=0){
//				throw new UIException("You don't have any credits to redeem");
//			}
//			EXDynaformPanel panel = new EXDynaformPanel("panel", "Redeem credits");
//			panel.addButton(new EXButton("save", "Confirm"));
//			panel.addButton(new EXButton("cancel", "Cancel"));
//			panel.addField("Amount : ", new EXInput("amount"));
//			panel.addField("Name on Cheque :", new EXInput("checkTo"));
//			panel.getDescendentByName("save").addEvent(DISPATCHER, Event.CLICK);
//			panel.getDescendentByName("cancel").addEvent(panel.CLOSE_EVENT, Event.CLICK);
//			addChild(panel.setStyle("width", "340px"));
//		}else{
//			try{
//				EXDynaformPanel panel = (EXDynaformPanel)getChild("panel");
//				BigDecimal amount = new BigDecimal(panel.getField("amount").getValue().toString());
//				String account = panel.getField("checkTo").getValue().toString();
//				if(!StringUtil.isNotEmpty(account)){
//					throw new UIException("Please enter the name on cheque");
//				}
//				Merchant m = MallUtil.getCurrentMerchant();
//				if(m.getCreditsToRedeem().doubleValue() < amount.doubleValue()){
//					throw new UIException("Please enter a value less or equal to the amount you can redeem");
//				}
//				m.redeem(amount, account);
//				panel.remove();
//				getDescendentOfType(EXTable.class).setModel(new RedeemTableModel());
//				
//				getDescendentByName("title").setText("Total of " + StringUtil.toCurrency(m.getCreditsToRedeem()) + " credit(s) to redeem");
//			}catch(NumberFormatException nfe){
//				throw new UIException("Please enter a numeric value for the amount");
//			}
//			
//			catch(Exception e){
//				throw new UIException(e);
//			}
//		}
		
	}
}
