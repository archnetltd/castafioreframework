package org.castafiore.shoppingmall.ng.v2.registrations;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXInput;

public class EXCompanyInformation extends AbstractRegistrationForm{

	public EXCompanyInformation(String name) {
		super(name, "templates/v2/registrations/EXCompanyInformation.xhtml");
		addChild(new EXInput("company.name").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.registration").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.tax").addClass("input-text"));
		addChild(new EXInput("company.email").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.addressline1").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.addressline2").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.city").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.postcode").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.country").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.mobile").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.telephone").addClass("input-text").addEvent(this, BLUR));
		addChild(new EXInput("company.fax").addClass("input-text"));

	}

	@Override
	public boolean validate(StatefullComponent field) {
		
		return validateEmpty(field);
	}

	

}
