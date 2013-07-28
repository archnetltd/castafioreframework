package com.eliensons.reminders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class ReminderUtil {
	
	public static void sendReminderForEffectiveDate(){
		//send elie and sons
		//send client
		//2 templates
		
		Calendar from = Calendar.getInstance();
		from.add(Calendar.MONTH, 6);
		from.set(Calendar.HOUR, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		
		
		Calendar to = Calendar.getInstance();
		
		//QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.between("dateOfTransaction", from.getTime(), hi))
	}
	
	public static void cancel(Container source){
		final Order order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		BillingInformation bi= order.getBillingInformation();
		bi.setOtherProperty("dateCancelled", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		bi.save();
	}
	
	
	public static void teminateOnDeath(Container source){
		final Order order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		BillingInformation bi= order.getBillingInformation();
		final String email = bi.getEmail();
		
		EXDynaformPanel panel = new EXDynaformPanel("deceased", "Termination on Death");
		panel.setStyle("z-index","4000");
		source.getAncestorOfType(PopupContainer.class).addPopup(panel);
		
		panel.addField("Name of person deceased :", new EXInput("nameofperson"));
		panel.addButton(new EXButton("Send", "Send Mail"));
		panel.addButton(new EXButton("cancel", "Cancel"));
		panel.getDescendentByName("Send").addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				
				String person = container.getAncestorOfType(EXDynaformPanel.class).getField("nameofperson").getValue().toString();
				
				sendMail(person, email);
				
				container.getAncestorOfType(EXDynaformPanel.class).remove();
				
				order.setStatus(21);
				BillingInformation b = order.getBillingInformation();
				b.setOtherProperty("dateTerminated", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				order.save();
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.mask(container.getAncestorOfType(EXDynaformPanel.class)).makeServerRequest(this);
				
			}
		}, Event.CLICK);
	}
	
	public static void partlyTerminate(Container source){
		final Order order = (Order)SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		BillingInformation bi= order.getBillingInformation();
		final String email = bi.getEmail();
		
		EXDynaformPanel panel = new EXDynaformPanel("deceased", "Partly terminate on Death");
		panel.setStyle("z-index","4000");
		source.getAncestorOfType(PopupContainer.class).addPopup(panel);
		Map<String,String> props = order.getBillingInformation().getOtherProperties();
		
		List<String> dict = new ArrayList<String>();
		for(int i =1; i <=6;i++){
			//"c1surname","c1name"
			String key1 = "c" + i + "surname";
			String key2 = "c" + i + "name";
			
			if(StringUtil.isNotEmpty(props.get(key1)) || StringUtil.isNotEmpty(props.get(key2))){
				String val = (StringUtil.isNotEmpty(props.get(key1))? props.get(key1) + " ":"") + (StringUtil.isNotEmpty(props.get(key2))? props.get(key2):"");
				dict.add(val);
			}
		}
		
		panel.addField("Name of person deceased :", new EXAutoComplete("nameofperson", "", dict));
		panel.addButton(new EXButton("Send", "Send Mail"));
		panel.addButton(new EXButton("cancel", "Cancel"));
		panel.getDescendentByName("Send").addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				
				String person = container.getAncestorOfType(EXDynaformPanel.class).getField("nameofperson").getValue().toString();
				
				sendMail(person, email);
				
				container.getAncestorOfType(EXDynaformPanel.class).remove();
				
				order.setStatus(21);
				BillingInformation b = order.getBillingInformation();
				b.setOtherProperty("datePartlyTerminated", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				order.save();
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.mask(container.getAncestorOfType(EXDynaformPanel.class)).makeServerRequest(this);
				
			}
		}, Event.CLICK);
	}
	
	private static void sendMail(String person, String email){
		String content = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/eliensons/reminders/terminate.xhtml"));
		
		content = content.replace("${person}", person);
		
		
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("Sincere sympath from Elie and Sons");
			helper.setFrom(MallUtil.getCurrentMerchant().getEmail());
			helper.setTo(email);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
