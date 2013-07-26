package org.castafiore.shoppingmall.message.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Message;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EXNewMessagePopup extends EXXHTMLFragment implements Event{

	public EXNewMessagePopup(String to) {
		super("EXNewMessage", "templates/EXNewMessage.xhtml");
		setDraggable(true);
		setAttribute("to", to);
		setStyle("z-index", "3000");
		addChild(new EXContainer("from", "div"))
		.addChild(new EXAutoComplete("to", to).addClass("span-12"))
		.addChild(new EXInput("subject", "").addClass("span-11").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter a subject"))
		
		
		
		
		.addChild(new EXContainer("sendMessage", "button").addClass("ui-widget-header").addClass("ui-corner-all").setText("Send Message").setAttribute("validate", "true").addEvent(this, Event.CLICK))
		.addChild(new EXContainer("cancel", "button").addClass("ui-widget-header").addClass("ui-corner-all").setText("Cancel").addEvent(this, Event.CLICK));
		
		Container message = new EXContainer("message", "div");
		
		message.addChild(new EXRichTextArea("message").setStyle("width", "600px").setAttribute("validation-method", "empty").setAttribute("error-message", "Please enter a message").setStyle("width", "538px").setStyle("height", "200px"));
		addChild(message);
		setStyle("width", "950px");
		addChild(new EXContainer("fieldset", "fieldset").setStyle("color", "orange")
				.setStyle("text-align", "center").setStyle("background", "none repeat scroll 0pt 0pt rgb(195, 217, 255)")
				.setStyle("border-bottom", "solid 1px silver").setText("Saved to draft").setStyle("display", "none"));
		
//		addEvent(new Event() {
//			
//			@Override
//			public void Success(ClientProxy container, Map<String, String> request)
//					throws UIException {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public boolean ServerAction(Container container, Map<String, String> request)
//					throws UIException {
//				container.getAncestorOfType(EXNewMessagePopup.class).saveToDraft();
//				return true;
//			}
//			
//			@Override
//			public void ClientAction(ClientProxy container) {
//				container.setTimeout(container.clone().makeServerRequest(this), 20000);
//				
//			}
//		}, Event.READY);
	}
	
	
	
	
	public void saveToDraft(){
		ShoppingMallUser user = MallUtil.getCurrentUser();
		Message message= null;
		if(StringUtil.isNotEmpty(getAttribute("path"))){
			message = (Message)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			String destination = ((EXInput)getChild("to")).getValue().toString();
			message.setDestination(destination);
			message.setAuthor(getChild("from").getAttribute("author"));
			message.setOwner(Util.getRemoteUser());
			
		}else{
			message = user.createMessage("created on " + new Date().toString());//new Message();
		}
		String title = ((EXInput)getChild("subject")).getValue().toString();
		if(!StringUtil.isNotEmpty(title)){
			title= "Draft";
		}
		message.setTitle(title);
		message.setSummary(getDescendentOfType(EXRichTextArea.class).getValue().toString());
		message.setStatus(Article.STATE_DRAFT);
		message.save();
		setAttribute("path", message.getAbsolutePath());
		getChild("fieldset").setStyle("display", "block");
	}
	
	public void initForSendMail(String fromemail, String toemail, String subject){
		
		((EXInput)getChild("to")).setValue(toemail);
		getChild("from").setText(fromemail);
		((EXInput)getChild("subject")).setValue(subject);
		getChild("sendMessage").setText("Send email");
		
	}
	
	//public void init(boolean)
	
	public void init(){
		ShoppingMallUser user = MallUtil.getCurrentUser();
		getChild("from").setText(user.getUser().toString()).setAttribute("author", user.getUser().getUsername());
		if(getRoot().getDescendentOfType(EXSearchEngineApplication.class) != null){
			List<Merchant> users =MallUtil.getCurrentMall().getMerchants();
			List<String> dictionary = new ArrayList<String>();
			for(Merchant u : users){
			
				dictionary.add(u.getUsername());
			}
			getDescendentOfType(EXAutoComplete.class).setDictionary(dictionary);
		}
		((EXInput)getChild("to")).setValue(getAttribute("to"));
		((EXInput)getChild("subject")).setValue("");
		getDescendentOfType(EXRichTextArea.class).setValue("");
	}
	
	public void init(Message message){
		ShoppingMallUser user = MallUtil.getCurrentUser();
		getChild("from").setText(user.getUser().toString()).setAttribute("author", user.getUser().getUsername());
		if(getRoot().getDescendentOfType(EXSearchEngineApplication.class) != null){
			List<Merchant> users =MallUtil.getCurrentMall().getMerchants();
			List<String> dictionary = new ArrayList<String>();
			for(Merchant u : users){
			
				dictionary.add(u.getUsername());
			}
			getDescendentOfType(EXAutoComplete.class).setDictionary(dictionary);
		}
		((EXInput)getChild("to")).setValue(message.getDestination());
		((EXInput)getChild("subject")).setValue(message.getTitle());
		getDescendentOfType(EXRichTextArea.class).setValue(message.getSummary());
		setAttribute("path", message.getAbsolutePath());
	}
	
	public void sendMessage(){
		ShoppingMallUser user = MallUtil.getCurrentUser();
		Message message= null;
		if(StringUtil.isNotEmpty(getAttribute("path"))){
			message = (Message)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			String destination = ((EXInput)getChild("to")).getValue().toString();
			message.setDestination(destination);
			message.setAuthor(getChild("from").getAttribute("author"));
			message.setOwner(Util.getRemoteUser());
			
		}else{
			message = user.createMessage("created on " + new Date().toString());//new Message();
		}
		message.setStatus(Article.STATE_PUBLISHED);
		message.setTitle(((EXInput)getChild("subject")).getValue().toString());
		message.setSummary(getDescendentOfType(EXRichTextArea.class).getValue().toString());
		message.save();
		remove();
	}
	public void sendEMail(){
		try{
			
			
			
			String to = ((EXInput)getChild("to")).getValue().toString();
			String from = getChild("from").getText();
			
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(((EXInput)getChild("subject")).getValue().toString());
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(getDescendentOfType(EXRichTextArea.class).getValue().toString(), true);
			sender.send(message);
			remove();
		}catch(Exception e){
			throw new UIException(e);
			
		}
	}
	public void sendMail(){
		try{
			
			User userFrom = SpringUtil.getSecurityService().loadUserByUsername(getChild("from").getAttribute("author"));
			String from = userFrom.getEmail();
			
			//User userto = SpringUtil.getSecurityService().loadUserByUsername(((EXInput)getChild("to")).getValue().toString());
			String to = ((EXInput)getChild("to")).getValue().toString();
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(((EXInput)getChild("subject")).getValue().toString());
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(getDescendentOfType(EXRichTextArea.class).getValue().toString(), true);
			sender.send(message);
			remove();
		}catch(Exception e){
			throw new UIException(e);
			
		}
	}


	public void executeAction(Container source) {
		if(source.getText().equalsIgnoreCase("Send message")){
			sendMessage();
			sendMail();
		}else if(source.getText().equalsIgnoreCase("Send mail")){
			sendEMail();
			
		}else if(source.getText().equalsIgnoreCase("Send email")){
			sendEMail();
			
		}else{
			this.remove();
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXNewMessagePopup.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("savetodraft")){
			saveToDraft();
			return true;
		}
		executeAction(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(!request.containsKey("savetodraft")){
			container.getAncestorOfType(EXNewMessagePopup.class).fadeOut(100);
		}
		
	}
}
