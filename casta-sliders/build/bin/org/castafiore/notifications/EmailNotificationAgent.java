package org.castafiore.notifications;

import org.castafiore.security.User;
import org.castafiore.wfs.types.Message;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailNotificationAgent extends AbstractNotificationAgent {
	
	private JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getName() {
		return "Email";
	}

	public void sendNotification(Message message, User from, User to) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setSubject(message.getTitle());
		mail.setText(message.getSummary());

		mail.setFrom(from.getEmail());
		mail.setTo(to.getEmail());
		mailSender.send(mail);
		
	}
}
