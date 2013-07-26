package org.castafiore.shoppingmall.cart;

import java.util.Date;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Message;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EXMerchantInfo extends EXXHTMLFragment implements Event{

	public EXMerchantInfo(String name) {
		super(name, "templates/EXMerchantInfo.xhtml");
		addChild(new EXContainer("username","label"));
		addChild(new EXContainer("addLine1","label"));
		
		addChild(new EXContainer("addLine2","label"));
		addChild(new EXContainer("addLine3","label"));
		addChild(new EXContainer("companyName","label").addClass("companyName"));
		addChild(new EXContainer("email","label").setStyle("font-weight", "bold").addClass("email"));
		addChild(new EXContainer("fax","label"));
		addChild(new EXContainer("phone","label"));
		addChild(new EXContainer("mobilePhone","label"));
		addChild(new EXContainer("businessRegistrationNumber","label"));
		addChild(new EXContainer("vatRegistrationNumber","label"));
		addChild(new EXContainer("logo","img").addClass("logo"));
		addChild(new EXContainer("website","a"));
		addChild(new EXContainer("summary","p"));	
		addChild(new EXContainer("nature","p"));
		addChild(new EXContainer("category","span"));
		addChild(new EXContainer("category_1","span"));	
		addChild(new EXContainer("category_2","span"));	
		addChild(new EXContainer("category_3","span"));	
		addChild(new EXContainer("category_4","span"));
		addChild(new EXContainer("contact","p"));
		addChild(new EXContainer("subscribe", "button").setText("Subscribe to merchant").addClass("ui-widget-header").addEvent(this, Event.CLICK));
		addChild(new EXContainer("sendMessage", "button").setText("Send Mail").addEvent(this, Event.CLICK).addClass("ui-widget-header"));
		addChild(new EXTextArea("message").setStyle("width", "285px").setStyle("height", "98px"));
		addChild(new EXInput("myEmail").setStyle("clear", "left").setStyle("float", "left").setStyle("width", "295px"));
		
	}
	
	public EXMerchantInfo setMerchant(Merchant merchant){
		
		setAttribute("username", merchant.getUsername());
		addLabel("username", merchant.getUsername());
		addLabel("addLine1", merchant.getAddressLine1());
		addLabel("addLine2", merchant.getAddressLine2());
		addLabel("addLine3", merchant.getCity());
		addLabel("companyName", merchant.getCompanyName());
		addLabel("email", merchant.getEmail());
		addLabel("mobilePhone", merchant.getMobilePhone());
		addLabel("fax", merchant.getFax());
		addLabel("phone", merchant.getPhone());
		addLabel("businessRegistrationNumber", merchant.getBusinessRegistrationNumber());
		addLabel("vatRegistrationNumber", merchant.getVatRegistrationNumber());
		getChild("logo").setStyle("width", "200px").setStyle("float", "left").setStyle("margin", "15px").setAttribute("src", merchant.getLogoUrl());
		getChild("website").setAttribute("href", merchant.getWebsite()).setAttribute("target", "_blank").setText(merchant.getWebsite());
		addLabel("summary", merchant.getSummary());
		addLabel("nature", merchant.getNature());
		addLabel("category", merchant.getCategory());
		addLabel("category_1", merchant.getCategory_1());
		addLabel("category_2", merchant.getCategory_2());
		addLabel("category_3", merchant.getCategory_3());
		addLabel("category_4", merchant.getCategory_4());
		addLabel("contact", SpringUtil.getSecurityService().loadUserByUsername(merchant.getUsername()).toString());
		
		
		if(merchant.getSubscription(Util.getRemoteUser()) != null){
			getChild("subscribe").setStyleClass("ui-state-active").setText("Unsubscribe from merchant");
		}else{
			getChild("subscribe").setStyleClass("ui-widget-header").setText("Subscribe to merchant");
		}
		
		return this;
	}
	
	private void addLabel(String name, String value){
		getChild(name).setText(value);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("subscribe")){
			if(container.getText().equalsIgnoreCase("Subscribe to merchant")){
				Merchant m = MallUtil.getMerchant(getAttribute("username"));
				m.subscribe(Util.getRemoteUser());
				container.setText("Unsubscribe from merchant");
				container.setStyleClass("ui-state-active");
			}else{
				Merchant m = MallUtil.getMerchant(getAttribute("username"));
				m.unSubscribe(Util.getRemoteUser());
				container.setText("Subscribe to merchant");
				container.setStyleClass("ui-widget-header");
			}
		}else{
			String msg = getDescendentOfType(EXTextArea.class).getValue().toString();
			String email = getDescendentOfType(EXInput.class).getValue().toString();
			if(StringUtil.isNotEmpty(email)){
				Merchant m = MallUtil.getMerchant(getAttribute("username"));
				String to = m.getEmail();
				try{
					JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
					MimeMessage message = sender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setSubject("Message from a user");
					helper.setFrom(email);
					helper.setTo(to);
					helper.setText(msg, true);
					sender.send(message);
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				getDescendentOfType(EXInput.class).addClass("ui-state-error");
				throw new UIException("Please enter a valid email address");
			}
			
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
