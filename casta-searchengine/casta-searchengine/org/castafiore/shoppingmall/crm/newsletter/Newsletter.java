package org.castafiore.shoppingmall.crm.newsletter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.persistence.Entity;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Entity
public class Newsletter extends Article{
	
	public Newsletter(){
		super();
	}
	
	public transient final static int TYPE_MAIL = 0;
	
	public transient final static int TYPE_SMS = 1;
	
	
	public final static int STATE_SCHEDULED = 40;
	
	public final static int STATE_SENT = 41;
	
	
	public final static int STATE_CANCELLED = 42;
	
	private Calendar scheduledTime;
	
	private int newsletterType = TYPE_MAIL;

	public Calendar getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Calendar scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public int getNewsletterType() {
		return newsletterType;
	}

	public void setNewsletterType(int newsletterType) {
		this.newsletterType = newsletterType;
	}
	
	public String getNewsletterTypeLabel(){
		if(newsletterType == TYPE_SMS){
			return "SMS";
		}else{
			return "Mail";
		}
	}
	
	
	public void addGroup(String ov){
		Value val = createFile(ov.toString(), Value.class);
		val.setString(ov.toString());
	}
	
	public List<String> getGroups(){
		List<String> result = new ArrayList<String>();
		for(Value v : getFiles(Value.class).toList()){
			result.add(v.getString());
		}
		return result;
		
	}
	
	
	public List<MerchantSubscription> getUsersToSend(){
		//List<String> groups = getGroups();
		Merchant merchant = getAncestorOfType(Merchant.class);
		
		List<MerchantSubscription> users = merchant.getSubscribers("Default");
		return users;
	}
	public void send(){
		if(newsletterType == TYPE_MAIL){
			String content = getSummary();
			String subject = getTitle();
			List<MerchantSubscription> users = getUsersToSend();
			User uFrom = SpringUtil.getSecurityService().loadUserByUsername(MallUtil.getCurrentMerchant().getUsername());
			try{
				JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
				MimeMessage message = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setSubject(subject);
				helper.setFrom(uFrom.getEmail());
				helper.setText(content, true);
				for(int i=0; i< users.size();i++){
					try{
						helper.setTo(users.get(i).getEmail());
					
						sender.send(message);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			setStatus(STATE_SENT);
		}
	}
	
	public String getNewsletterStatusLabel(){
		if(getStatus() == STATE_SCHEDULED){
			return "Scheduled";
		}else if(getStatus() == STATE_SENT){
			return "Sent";
		}else if(getStatus() == STATE_CANCELLED){
			return "Cancelled";
		}else{
			return "New";
		}
	}
	
	
	

}
