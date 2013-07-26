package org.castafiore.shoppingmall.payment;

import java.math.BigDecimal;
import java.math.MathContext;

import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXCardInformation extends EXXHTMLFragment implements PaymentMethod{

	static String[] labels = new String[]{
	
		"cardNumber",
		"nameOnCard",
		
	};
	public EXCardInformation(String name) {
		super(name, "templates/EXCardInformation.xhtml");
		addChild(new EXInput("cardNumber"));
		addChild(new EXInput("nameOnCard"));
		addChild(new EXSelect("expirationMonth", new DefaultDataModel<Object>().addItem("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")));
		addChild(new EXSelect("expirationYear", new DefaultDataModel<Object>().addItem("2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024")));
		addChild(new EXSelect("cardType", new DefaultDataModel<Object>().addItem("VISA", "MASTERCARD")));
	}
	@Override
	public boolean confirm() {
		return true;
	}
	@Override
	public BigDecimal getNumberOfCreditsFor(BigDecimal rupees) {
		BigDecimal transactionFee = rupees.multiply(new BigDecimal(0.03),MathContext.DECIMAL32);
		transactionFee = transactionFee.add(new BigDecimal(7));
		return rupees.subtract(transactionFee);
	}
	

}
