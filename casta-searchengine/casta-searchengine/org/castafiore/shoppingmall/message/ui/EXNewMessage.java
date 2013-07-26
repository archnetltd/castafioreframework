package org.castafiore.shoppingmall.message.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.ui.MallForm;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Message;

public class EXNewMessage extends EXXHTMLFragment implements MallForm{
	
	
	

	public EXNewMessage()throws Exception {
		super("EXNewMessage", "templates/EXNewMessage.xhtml");
		List<User> users = SpringUtil.getSecurityService().getUsersForOrganization(Util.getLoggedOrganization());
		List<String> dictionary = new ArrayList<String>();
		for(User u : users){
			dictionary.add(u.getUsername());
		}
		addChild(new EXContainer("from", "div"))
		.addChild(new EXAutoComplete("to","",dictionary).addClass("span-12"))
		.addChild(new EXInput("subject").addClass("span-11").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter a subject"))
		.addChild(new EXTextArea("message").setStyle("border", "none").setStyle("margin", "0").setAttribute("rows", "16").addClass("span-11").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter a message"))
		.addChild(new EXContainer("sendMessage", "button").setText("Send Message").setAttribute("validate", "true"))
		.addChild(new EXContainer("cancel", "button").setText("Cancel"));
	}
	
	
	
	
	public void init()throws Exception{
		ShoppingMallUser user = MallUtil.getCurrentUser();
		getChild("from").setText(user.getUser().toString()).setAttribute("author", user.getUser().getUsername());
		
		List<Merchant> users =MallUtil.getCurrentMall().getMerchants();
		List<String> dictionary = new ArrayList<String>();
		for(Merchant u : users){
		
			dictionary.add(u.getUsername());
		}
		getDescendentOfType(EXAutoComplete.class).setDictionary(dictionary);
		((EXInput)getChild("to")).setValue("");
		((EXInput)getChild("subject")).setValue("");
		((EXTextArea)getChild("message")).setValue("");
	}
	
	
	
	public void setTemplate(Message message)throws Exception{
		//ShoppingMallUser user = SpringUtil.getBeanOfType(ShoppingMallUserManager.class).getCurrentUser();
		getChild("from").setText(message.getAuthor()).setAttribute("author", message.getAuthor());
		
		List<Merchant> users =MallUtil.getCurrentMall().getMerchants();
		List<String> dictionary = new ArrayList<String>();
		for(Merchant u : users){
			dictionary.add(u.getUsername());
		}
		getDescendentOfType(EXAutoComplete.class).setDictionary(dictionary);
		((EXInput)getChild("to")).setValue(message.getDestination());
		((EXInput)getChild("subject")).setValue(message.getTitle());
		((EXTextArea)getChild("message")).setValue(message.getSummary());
	}
	
	public void sendMessage(Container source){
		ShoppingMallUser user = MallUtil.getCurrentUser();
		Message message= user.createMessage("created on " + new Date().toString());//new Message();
		//message.setName("created on " + new Date().toString());
		String destination = ((EXInput)getChild("to")).getValue().toString();
		message.setDestination(destination);
		message.setAuthor(getChild("from").getAttribute("author"));
		message.setTitle(((EXInput)getChild("subject")).getValue().toString());
		message.setSummary(((EXTextArea)getChild("message")).getValue().toString());
		message.setOwner(Util.getRemoteUser());
		
		//user.sendMessage(message);
		message.save();
		getAncestorOfType(EXMessagePanel.class).showSentMessage();
	}
	
	public void cancel(Container source){
		getAncestorOfType(EXMessagePanel.class).showList();
	}
	public void send(Container source){
		sendMessage(source);
	}
	

	public void executeAction(Container source) {
		if(source.getName().equals("send")){
			sendMessage(source);
		}else if(source.getName().equals("cancel")){
			cancel(source);
		}
		
	}

}
