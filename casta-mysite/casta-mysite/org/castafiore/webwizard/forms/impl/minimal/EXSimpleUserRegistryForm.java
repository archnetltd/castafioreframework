package org.castafiore.webwizard.forms.impl.minimal;

import java.util.List;

import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.webwizard.WizardForm;

public class EXSimpleUserRegistryForm extends EXDynaformPanel implements WizardForm{

	public EXSimpleUserRegistryForm(String name) {
		super(name, "Register");
		
		addField("First name (*) :", new EXInput("firstname"));
		addField("Last name (*) :", new EXInput("lastname"));
		addField("Email : (*) :", new EXInput("email"));
		addField("Username (*) :", new EXInput("username"));
		addField("Password (*) :", new EXPassword("password"));
		addField("Confirm password (*) :", new EXPassword("confirmpassword"));
		addButton(new EXButton("Register", "Register !"));
	}
	
	
	public void saveUser()throws Exception{
		String username = getField("username").getValue().toString();
		String password = getField("password").getValue().toString();
		String confirmPassword = getField("confirmpassword").getValue().toString();
		String firstName = getField("firstname").getValue().toString();
		String email = getField("email").getValue().toString();
		String lastname = getField("lastname").getValue().toString();
		
		
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastname);
		user.setEmail(email);
		user.setEnabled(true);
		
		SecurityService service = SpringUtil.getSecurityService();
		service.registerUser(user);
		
		
		//create a simple portal with border layout container
		//header,left menu,body,footer
		//this user will be able to create different pages in a portal
		//we will unlock only xylayout container.
	}


	public void initForm(List<WizardForm> previousForms) {
		
		
	}


	public boolean validate() {
		
		return false;
	}

}
