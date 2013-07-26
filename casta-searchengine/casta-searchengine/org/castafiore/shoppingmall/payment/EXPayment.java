package org.castafiore.shoppingmall.payment;

import java.math.BigDecimal;
import java.math.MathContext;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXPayment extends EXXHTMLFragment implements EventDispatcher{

	public EXPayment(String name) {
		super(name,"templates/EXPayment.xhtml");
		setStyle("z-index", "3001");
		setStyle("position", "fixed");
		addChild(new EXContainer("credits", "label").setText("0"));
		EXInput money = new EXInput("money");
		money.setValue("1000");
		addChild(money.addEvent(DISPATCHER, Event.BLUR));
		addChild(new EXContainer("price", "label").setText("0"));
		Container paymentTypes = new EXContainer("paymentTypes", "div").addClass("paymentTypes");
		paymentTypes.addChild(new EXContainer("", "label").setText("Select payment method :"));
		paymentTypes.addChild(new EXContainer("creditCard", "img").setAttribute("src", "http://cdn5.iconfinder.com/data/icons/woocons1/Credit%20Card.png").addEvent(DISPATCHER, Event.CLICK));
		paymentTypes.addChild(new EXContainer("mobileBanking", "img").setAttribute("src", "http://png.findicons.com/files/icons/1620/crystal_project/32/sms.png").addEvent(DISPATCHER, Event.CLICK));
		
		addChild(paymentTypes);
		
		addChild(new EXBillingInformation("billingInformation"));
		
		Container methods = new EXContainer("methods", "div");
		methods.addChild(new EXCardInformation("cardInformation"));
		
		addChild(methods);
		
		addChild(new EXContainer("confirm", "button").setText("Confirm").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("cancel", "button").setText("Cancel").addEvent(DISPATCHER, Event.CLICK));
		refreshCredits();
		

	}
	
	private void refreshCredits(){
		double money =  Integer.parseInt(((EXInput)getChild("money")).getValue().toString());
		BigDecimal credits = getDescendentOfType(PaymentMethod.class).getNumberOfCreditsFor( new BigDecimal(money));
		getChild("credits").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),credits)) ;
		BigDecimal price = new BigDecimal(money).divide(credits,MathContext.DECIMAL32);
		getChild("price").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),price));
	}
	
	private boolean validateMoney(){
		return true;
	}
	private boolean validateBillingInfo(){
		return true;
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("cancel")){
			Container parent = getParent();
			parent.getChildren().clear();
			parent.setRendered(false);
			parent.getParent().setRendered(false);
		}else if(source.getName().equalsIgnoreCase("confirm")){
			if(validateMoney()){
				if(validateBillingInfo()){
					double credits = Double.parseDouble(getChild("credits").getText());
					boolean confirmed =getChild("methods").getDescendentOfType(PaymentMethod.class).confirm();
					if(confirmed){
						User u = MallUtil.getCurrentUser().getUser();
						//u.setCredits(u.getCredits().add(new BigDecimal(credits)));
						//getAncestorOfType(EXMall.class).getDescendentOfType(EXCreditsInfo.class).update();
						Container parent = getParent();
						parent.getChildren().clear();
						parent.setRendered(false);
						parent.getParent().setRendered(false);
					}
					
				}
			}
		}else if(source.getName().equalsIgnoreCase("creditCard")){
			Container methods = getChild("methods");
			if(methods.getDescendentOfType(EXCardInformation.class) == null){
				methods.getChildren().clear();
				methods.setRendered(false);
				methods.addChild(new EXCardInformation("cardInformation"));
				refreshCredits();
				
			}
		}else if(source.getName().equalsIgnoreCase("mobileBanking")){
			Container methods = getChild("methods");
			if(methods.getDescendentOfType(EXPayMobile.class) == null){
				methods.getChildren().clear();
				methods.setRendered(false);
				methods.addChild(new EXPayMobile("payMobile"));
				refreshCredits();
				
			}
		}else if(source.getName().equalsIgnoreCase("money")){
			if(validateMoney()){
				
				refreshCredits();
			}
			
		}
		
	}

}
