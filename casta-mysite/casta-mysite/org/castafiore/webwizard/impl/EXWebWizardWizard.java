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
 package org.castafiore.webwizard.impl;

import java.util.List;

import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.sms.SMSService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.util.FreeCSSUtil;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.webwizard.Template;
import org.castafiore.webwizard.WizardForm;
import org.castafiore.webwizard.forms.impl.EXCompanyInfoWizardForm;
import org.castafiore.webwizard.forms.impl.EXFinalStepWizardForm;
import org.castafiore.webwizard.forms.impl.EXSelectModulesWizardForm;
import org.castafiore.webwizard.forms.impl.EXSelectTemplateWizardForm;
import org.castafiore.webwizard.forms.impl.EXTermAndAgreementWizardForm;
import org.castafiore.wfs.security.SecurityManager;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EXWebWizardWizard extends EXWizard {
	private User company = null;
	
	private List<String> selectedModules = null;
	
	private String selectedTemplate = null;
	
	boolean userCreated = false;
	
	private String portalPath = null;

	public EXWebWizardWizard(String name) {
		super(name);
		
	}


	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	public void next() {
		
//		WizardForm form = getCurrentWizardForm();
//		
//		
//		if(form.validate()){
//			if(form instanceof EXCompanyInfoWizardForm){
//				company = ((EXCompanyInfoWizardForm)form).getUser();
//			}else if(form instanceof EXSelectModulesWizardForm){
//				selectedModules = ((EXSelectModulesWizardForm)form).getSelectedModules();
//			}else if( form instanceof EXSelectTemplateWizardForm){
//				selectedTemplate = ((EXSelectTemplateWizardForm)form).getSelectedTemplate();
//			}else if( form instanceof EXTermAndAgreementWizardForm){
//				boolean hasAgreed = ((EXTermAndAgreementWizardForm)form).hasAgreed();
//				if(!userCreated && hasAgreed){
//					//create user
//					//create userprofile
//					//create folder templates in folder userprofile
//					//build portal from template
//					//generate portal into directory
//					userCreated = createCompany();
//					getDescendentOfType(EXFinalStepWizardForm.class).getDescendentByName("portalLink").setAttribute("href","portal.jsp?u=" + company.getUsername());
//				}
//			}
//		}
//		
//		
//		super.next();
	}
	
//	public boolean createCompany(){
//		
//		try{
//			//register the company as a user
//			SecurityService security = SpringUtil.getSecurityService();
//			security.registerUser(company);
//			
//			//add profiles to the user
//			UserProfileService upService = BaseSpringUtil.getBeanOfType(UserProfileService.class);
//			String tel = getDescendentOfType(EXCompanyInfoWizardForm.class).getField("tel").getValue().toString();
//			String mobile = getDescendentOfType(EXCompanyInfoWizardForm.class).getField("mobile").getValue().toString();
//			String street = getDescendentOfType(EXCompanyInfoWizardForm.class).getField("street").getValue().toString();
//			String city = getDescendentOfType(EXCompanyInfoWizardForm.class).getField("city").getValue().toString();
//			String companyName = getDescendentOfType(EXCompanyInfoWizardForm.class).getField("companyname").getValue().toString();
//			
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "tel", tel);
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "mobile", mobile);
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "street", street);
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "city", city);
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "companyName", companyName);
//			
//			//add the template used in profile of company
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "template", selectedTemplate);
//			
//			//add selected modules in profile of company
//			String selectedModules_ = this.selectedModules.toString().replace("[", "").replace("]", "");
//			upService.saveUserProfileValue(company.getUsername(), UserProfileService.INFO_CAT_PROFILE, "selectedmodules", selectedModules_);
//			
//			//create a group with company username.
//			security.saveOrUpdateGroup(company.getUsername(), "Group for company created using webwizard");
//			
//			//make company administrator of company
//			security.assignSecurity(company.getUsername(), "administrator", company.getUsername());
//			security.assignSecurity(company.getUsername(), "member","users");
//			
//			//make a copy of template into /root/users/<company>/portal-data/portal-template.ptl
//			Template t = new Template();
//			t.setName(selectedTemplate);
//			byte[] defn = t.getPortalDefinition(company.getUsername());
//			
//			BinaryFile bf = new BinaryFile();
//			bf.setName("portal-template.ptl");
//			bf.write(defn);
//			
//			RepositoryService repositoryService = SpringUtil.getRepositoryService();
//			
//			Directory userDirectory = upService.getUserDirectory(company.getUsername());
//			Directory portalData = (Directory)userDirectory.getChild("portal-data");
//			SecurityManager manager = SpringUtil.getSecurityManager();
//			
//			if(portalData == null){
//				portalData = new Directory();
//				portalData.setName("portal-data");
//				userDirectory.addChild(portalData.makeOwner(company.getUsername()));
//				//portalData.
//				manager.grantReadPermission(portalData, "*:" + company.getUsername());
//				manager.grantWritePermission(portalData, "administrator:" + company.getUsername());
//			}
//			
//			portalData.addChild(bf.makeOwner(company.getUsername()));
//			
//			Directory pages = new Directory();
//			pages.setName("pages");
//			bf.addChild(pages.makeOwner(company.getUsername()));
//			
//			repositoryService.update(userDirectory,  company.getUsername());
//			portalPath = bf.getAbsolutePath();
//			FreeCSSUtil.copyTemplates(company.getUsername(), selectedTemplate);
//			//create blog page
//			String defaultDescription =  IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("defaultdescription.properties"));
//			FreeCSSUtil.newBlogPage(portalPath, "blog", company.getUsername(), "Castafiore",defaultDescription);
//			FreeCSSUtil.newHomePage(portalPath, "home",company.getUsername(), "Castafiore",defaultDescription);
//			FreeCSSUtil.newCataloguePage(portalPath, "catalog", company.getUsername());
//			
//			
////			try{
////			
////				MailSender mailSender = BaseSpringUtil.getBeanOfType(MailSender.class);
////				SimpleMailMessage template = BaseSpringUtil.getBean("confirmation");
////				template.setTo(company.getEmail());
////				template.setText("Username :" + company.getUsername() + "\n<br>Password : " + company.getPassword());
////				mailSender.send(template);
////			}catch(Exception e){
////				e.printStackTrace();
////			}
//			
//			//SMSService smsService = SpringUtil.getBeanOfType(SMSService.class);
//			//smsService.sendMessage("Welcome to castafiore platform! Your company " + companyName + " has been created successfully. Your username : " + company.getUsername() + " Your password :castafiore2010" , mobile.replace("-", ""));
//			security.login(company.getUsername(), company.getPassword());
//			return true;
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//		
//	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		super.previous();
	}
	
	

}
