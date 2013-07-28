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
 package org.castafiore.sms;

import java.util.List;

import org.castafiore.persistence.Dao;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.wfs.Util;
import org.hibernate.criterion.Restrictions;


public class SendSMSShedularImpl implements SendSMSSchedular {
	
	private UserProfileService userProfileService;
	
	private SecurityService securityService;
	
	private SMSService SMSService;
	
	private Dao dao;
	

	public void doSendSMS(SMSInstance instance)throws Exception {
		//Session session = dao.getSession();
		
		//List<SMSInstance> instances = session.createCriteria(SMSInstance.class).add(Restrictions.eq("statue", STATUS_NEW)).list();
		
			String message = instance.getMessage();
			List<SMSRecipient> recipients = instance.getRecipients();
			for(SMSRecipient recipient : recipients){
				String permission = recipient.getPermission();
				
				if(permission.contains(":")){
					//groups
					
					List<User> users = securityService.getUsers(permission, Util.getLoggedOrganization());
					for(User u : users){
						doSendSMS(u.getUsername(), instance.getMessage());
					}
				}else{
					String response = doSendSMS(permission,instance.getMessage());
					instance.setResponse(response);
				}
			}
	}
	protected String doSendSMS(String username, String message){
		try{
			String mobile = userProfileService.getUserProfileValue(username, UserProfileService.INFO_CAT_BUSINESS, "mobile");
			SMSService.sendMessage(message, mobile);
			return "success";
		}catch(Exception e){
			return "error:" + e.getMessage();
		}
	}

	public List<SMSInstance> getSMS(int status) {
		return dao.getReadOnlySession().createCriteria(SMSInstance.class).add(Restrictions.eq("status", status)).list();
	}
	public UserProfileService getUserProfileService() {
		return userProfileService;
	}
	public void setUserProfileService(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}
	public SecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public SMSService getSMSService() {
		return SMSService;
	}
	public void setSMSService(SMSService sMSService) {
		SMSService = sMSService;
	}
	public Dao getDao() {
		return dao;
	}
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	

}
