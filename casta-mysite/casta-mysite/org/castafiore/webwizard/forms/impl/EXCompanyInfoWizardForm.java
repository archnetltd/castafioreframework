/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.webwizard.forms.impl;

import java.util.List;

import javax.ejb.PostActivate;

import jodd.util.StringUtil;

import org.castafiore.security.User;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.webwizard.WizardForm;
import org.omg.CORBA.FieldNameHelper;

public class EXCompanyInfoWizardForm extends EXDynaformPanel implements WizardForm{
 
	public EXCompanyInfoWizardForm() {
		super("EXCompanyInfoWizardForm", "General information about you and your company");
		setDraggable(false);
		setShowCloseButton(false);
		setWidth(Dimension.parse("100%"));
	}


	public void initForm(List<WizardForm> previousForms) {
		addField("First name (*) :", new EXInput("firstname"));
		addField("Last Name (*) :", new EXInput("lastname"));
		addField("Company Name (*) :", new EXInput("companyname"));
		addField("City :", new EXTextArea("city"));
		addField("Street :", new EXTextArea("street"));
		addField("Email (*):", new EXInput("email"));
		addField("Contact phone (999-9999):", new EXInput("tel", ""));
		addField("Contact cell phone (999-9999):", new EXInput("mobile", ""));
		
		DefaultDataModel companySizeModel = new DefaultDataModel().addItem("Small", "Medium", "Big", "Very big");
		addField("Company size :", new EXSelect("companysize", companySizeModel));
	}

	

	public User getUser(){
		User u = new User();
		if(checkNull("firstname") && checkNull("lastname") && checkNull("companyname") && checkNull("email")){
			u.setFirstName(getField("firstname").getValue().toString());
			u.setLastName(getField("lastname").getValue().toString());
			u.setEmail(getField("email").getValue().toString());
			u.setUsername(u.getEmail());
			u.setPassword("castafiore2010");
			return u;
		}else{
			return null;
		}
	}
	
	private boolean checkNull(String fielName){
		if(org.castafiore.utils.StringUtil.isNotEmpty(getField(fielName).getValue().toString())){
			getField(fielName).removeClass("ui-state-error");
			return true;
		}else{
			getField(fielName).addClass("ui-state-error");
			return false;
		}
		
	}
	
	private boolean checkPhoneFormat(String fielName){
		String value = getField(fielName).getValue().toString();
		if(!(value.length() == 8)){
			getField(fielName).addClass("ui-state-error");
			return false;
		}
		try{
			Integer.parseInt(value.replace("-", ""));
			getField(fielName).removeClass("ui-state-error");
			return true;
		}catch(Exception e){
			getField(fielName).addClass("ui-state-error");
			return false;
		}
	}
	
	
	private boolean checkEmailFormat(String fielName){
		String value = getField(fielName).getValue().toString();
		int posAt = value.indexOf("@");
		int point = value.indexOf(".");
		
		
		if(posAt <= 0 || point <= 0){
			getField(fielName).addClass("ui-state-error");
			return false;
		}
		if(posAt > point){
			getField(fielName).addClass("ui-state-error");
			return false;
		}
		getField(fielName).removeClass("ui-state-error");
		return true;
	}



	public boolean validate() {
		
		boolean valid = true;
		valid = checkNull("firstname");
		
		if(!valid){
			checkNull("lastname");
		}else{
			valid =  checkNull("lastname");
		}
		
		if(!valid){
			checkNull("companyname");
		}else{
			valid =  checkNull("companyname");
		}
		
		if(!valid){
			checkNull("email");
		}else{
			valid =  checkNull("email");
		}
		
		
		if(!valid){
			checkPhoneFormat("tel");
		}else{
			valid =  checkPhoneFormat("tel");
		}
		
		if(!valid){
			checkPhoneFormat("mobile");
		}else{
			valid =  checkPhoneFormat("mobile");
		}
		
		if(!valid){
			checkEmailFormat("email");
		}else{
			valid =  checkEmailFormat("email");
		}
		return valid;
	}

	
}
