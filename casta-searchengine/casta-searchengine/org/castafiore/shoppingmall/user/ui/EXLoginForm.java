package org.castafiore.shoppingmall.user.ui;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EXLoginForm extends EXXHTMLFragment implements Event{
	
	private final static Random rand = new Random();

	public EXLoginForm(String name) {
		super(name, "templates/EXLoginForm.xhtml");
		addClass("span-6").addClass("malladvlist");
		addChild(new EXContainer("error", "span").addClass("error").addClass("span-6").setDisplay(false));
		addChild(new EXInput("username"));
		addChild(new EXPassword("password"));
		addChild(new EXContainer("walkIn", "button").setText("Walk In").addEvent(this, Event.CLICK));
		addChild(new EXContainer("newUser", "button").setText("New User ?").addEvent(this, Event.CLICK));
		addChild(new EXContainer("forgotPassword", "a").setText("Forgot password ?").setAttribute("href", "#").addEvent(this, Event.CLICK));
		
		EXXHTMLFragment registerSection = new EXXHTMLFragment("registerSection", "templates/EXRegisterSection.xhtml");
		registerSection.addChild(new EXInput("email")).addChild(new EXInput("phone")).addChild(new EXInput("mobile")).addChild(new EXCheckBox("termsandconditions")).setDisplay(false);
		
		addChild(registerSection);
		
		
	}
	
	
	public boolean doLogin(){
		final String username = ((EXInput)getChild("username")).getValue().toString();
		String password = ((EXInput)getChild("password")).getValue().toString();
		
		
		boolean valid = true;
		if(username == null || username.trim().length() == 0){
			getChild("username").addClass("ui-state-error");
			getChild("error").setText("Please fill in the fields").setDisplay(true);
			valid = false;
		}
		
		if(password == null || password.trim().length() == 0){
			getChild("password").addClass("ui-state-error");
			getChild("error").setText("Please fill in the fields").setDisplay(true);
			if(valid){
				valid = false;
			}
		}
		
		if(!valid){
			return false;
		}
		
		
		try{
			ShoppingMallUser user  = SpringUtil.getBeanOfType(ShoppingMallUserManager.class).login(username, password);
			if(user == null){
				getChild("error").setText("The username and password do not match").setDisplay(true);
				return false;
			}
			User u = user.getUser();
			if(!u.isEnabled()){
				getChild("error").setText("Your account is not activated yet.").setDisplay(true);
				return false;
			}
			if(user!= null){
				
				getRoot().getDescendentByName("loginLink").setDisplay(false);
				getRoot().getDescendentByName("login").setStyle("display", "block");
				getRoot().getDescendentByName("messages").setStyle("display", "block");
				getRoot().getDescendentByName("logout").setStyle("display", "block");
				ComponentUtil.iterateOverDescendentsOfType(getRoot(), MallLoginSensitive.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
						((MallLoginSensitive)c).onLogin(username);
						
					}
				});
				
				return true;
				
				//getAncestorOfType(EXMall.class).walkIn(username);
			}else{
				getChild("error").setText("Password does not match").setDisplay(true);
				return false;
			}
		}catch(UsernameNotFoundException unfe){
			getChild("error").setText("Username does not exist").setDisplay(true);
			return false;
		}catch(Exception e){
			e.printStackTrace();
			getChild("error").setText("Unknown error occured.").setDisplay(true);
			return false;
		}
	}
	
	
	public void newUser(){
		
		getChild("registerSection").setDisplay(true);
		getChild("walkIn").setText("Register");
		getChild("newUser").setText("Cancel");
		
	}
	
	
	public void cancelRegister(){
		getChild("registerSection").setDisplay(false);
		getChild("walkIn").setText("Walk In");
		getChild("newUser").setText("New User ?");
	}
	
	
	public void registerUser(){
		String username = ((EXInput)getChild("username")).getValue().toString();
		String password = ((EXInput)getChild("password")).getValue().toString();
		String email = ((EXInput)getDescendentByName("email")).getValue().toString();
		String phone = ((EXInput)getDescendentByName("phone")).getValue().toString();
		String mobile = ((EXInput)getDescendentByName("mobile")).getValue().toString();
		
		boolean valid = true;
		if(!getDescendentOfType(EXCheckBox.class).isChecked()){
			getChild("error").setText("Please read and accept the privacy policy").setDisplay(true);
			return;
		}
		
		if(username == null || username.trim().length() == 0){
			getChild("error").setText("Please fill in the fields marked with a <span style=color:red>*</span>").setDisplay(true);
			getChild("username").addClass("ui-state-error");
			if(valid)
				valid = false;
		}else{
			try{
				User u = SpringUtil.getSecurityService().loadUserByUsername(username);
				if(u != null){
					getChild("error").setText("Username already exists. Please try another one").setDisplay(true);
					getChild("username").addClass("ui-state-error");
					return;
				}
			}
			catch(Exception e){
				
			}
		}
		
		if(password == null || password.trim().length() == 0){
			getChild("error").setText("Please fill in the fields marked with a <span style=color:red>*</span>").setDisplay(true);
			getChild("password").addClass("ui-state-error");
			if(valid)
				valid = false;

		} 
		
		if(email == null || email.trim().length() == 0){
			getChild("error").setText("Please fill in the fields marked with a <span style=color:red>*</span>").setDisplay(true);
			getDescendentByName("email").addClass("ui-state-error");
			if(valid)
				valid = false;
			
		}else{
			User u = SpringUtil.getSecurityService().loadUserByEmail(email);
			
			if(u!= null){
				getChild("error").setText("We already have a user with this email in our database").setDisplay(true);
				getDescendentByName("email").addClass("ui-state-error");
				
				return;
			}
		}
		
		if(phone == null || phone.trim().length() == 0){
			getChild("error").setText("Please fill in the fields marked with a <span style=color:red>*</span>").setDisplay(true);
			getDescendentByName("phone").addClass("ui-state-error");
			if(valid)
				valid = false;
			
		}
		
		if(mobile == null || mobile.trim().length() == 0){
			getChild("error").setText("Please fill in the fields marked with a <span style=color:red>*</span>").setDisplay(true);
			getDescendentByName("mobile").addClass("ui-state-error");
			if(valid)
				valid = false;
			
		}else{
			User u = SpringUtil.getSecurityService().loadUserByMobilePhone(email);
			if(u!= null){
				getChild("error").setText("We already have a user with this mobile phone in our database").setDisplay(true);
				getDescendentByName("mobile").addClass("ui-state-error");
				return;
			}
		}
		
		if(!valid){
			return;
		}
		
		//send email with a unique number in it
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setEmail(email);
		u.setPhone(phone);
		u.setMobile(mobile);
		u.setEnabled(false);
		long secret = System.currentTimeMillis();
		u.setSecret(secret + "");
		Application app = getRoot();
		try{
			SpringUtil.getSecurityService().registerUser(u);
			
			//getAncestorOfType(EXMall.class).walkIn(username);
			
			
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("Activate your eMall of Mauritius account");
			helper.setFrom("contact@archnetltd.com");
			helper.setTo(email);
			
			String contextPath = app.getContextPath();
			String serverPort = app.getServerPort();
			String servaerName = app.getServerName();
			
			String templateLocation = "castafiore/register/?ss=" + secret;
			if(!contextPath.startsWith("/")){
				contextPath = "/" + contextPath;
			}
			if(contextPath.endsWith("/") && templateLocation.startsWith("/")){
				templateLocation = templateLocation.substring(1);
			}
			if(!contextPath.endsWith("/") && ! templateLocation.startsWith("/")){
				templateLocation = "/" + templateLocation;
			}
			//String url = "";
			if(!templateLocation.startsWith("http")){
				templateLocation = "http://" + servaerName + ":" + serverPort  + contextPath + "" + templateLocation;
			}
			
			String link ="<a href=" + templateLocation + ">" + templateLocation + "</a>";
			String content = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/user/ui/RegisterMail.properties"));
			content = content.replace("$link", link);
			helper.setText(content, true);
			sender.send(message);
			getChild("error").setText("You should recieve a mail in a few seconds. Please follow the instruction to activate your account.").setDisplay(true);
			
		}catch(Exception e){
			getChild("error").setText(e.getMessage()).setDisplay(true);
		}
		
	}



	public void executeAction(Container source) {
		
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXPanel.class)).makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container source, Map<String, String> request)
			throws UIException {
		//executeAction(container);
		
		if(source.getName().equalsIgnoreCase("walkIn")){
			if(doLogin())
			{
				request.put("torem", getId());
				getParent().setRendered(false).getChildren().clear();
			}
		}else if(source.getText().equalsIgnoreCase("New User ?")){
			newUser();
		}else if(source.getText().equalsIgnoreCase("Register")){
			registerUser();
		}else if(source.getText().equalsIgnoreCase("Cancel")){
			cancelRegister();
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("torem")){
			container.mergeCommand(new ClientProxy("#" + request.get("torem")).fadeOut(100));
		}
		
	}

}
