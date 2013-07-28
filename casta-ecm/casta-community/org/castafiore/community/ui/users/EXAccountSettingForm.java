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
 package org.castafiore.community.ui.users;

import org.castafiore.community.ui.CommunityEvents;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXAccountSettingForm extends EXDynaformPanel{

	public EXAccountSettingForm(User user) {
		super("EXAccountSettingForm", "Account setting");
		addField("Username :", new EXInput("username"));
		addField("Password :", new EXPassword("password"));
		addField("Confirm password :", new EXPassword("confirmPassword"));
		addField("First name :", new EXInput("firstName"));
		addField("Last name :", new EXInput("lastName"));
		addField("Email address :", new EXInput("emailAddress"));
		addField("Phone :", new EXInput("phone"));
		addField("Mobile :", new EXInput("mobile"));
		addField("Fax :", new EXInput("fax"));
		addField("POS :", new EXInput("pointOfSale"));
		
		EXButton saveButton = new EXButton("Save", "Save");
		saveButton.setAttribute("method", "save");
		saveButton.addEvent(CommunityEvents.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		addButton(saveButton);
		addButton(new EXButton("Cancel", "Cancel"));
		setShowCloseButton(false);
		setShowHeader(false);
		
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "270px");
		
		setDraggable(false);
		setWidth(Dimension.parse("100%"));
		setUser(user);
		
	}
	
	public void setUser(User user){
		getField("username").setValue(user.getUsername());
		getField("password").setValue(user.getPassword());
		getField("firstName").setValue(user.getFirstName());
		getField("lastName").setValue(user.getLastName());
		getField("emailAddress").setValue(user.getEmail());
		getField("phone").setValue(user.getPhone());
		getField("mobile").setValue(user.getMobile());
		getField("fax").setValue(user.getFax());
		getField("pointOfSale").setValue(user.getPointOfSale());
		if(StringUtil.isNotEmpty(user.getUsername())){
			((EXInput)getField("username")).setEnabled(false);
			setAttribute("new", "false");
		}else{
			setAttribute("new", "true");
		}
		
	}
	
	public void save()throws Exception{
		User u = null;
		String username = getField("username").getValue().toString();
		if(getAttribute("new").equals("false")){
			u = BaseSpringUtil.getBeanOfType(SecurityService.class).loadUserByUsername(username);
		}else{
			u = new User();
			u.setUsername(username);
		}
		u.setPassword(getField("password").getValue().toString());
		u.setAccountNonExpired(true);
		u.setAccountNonLocked(true);
		u.setCredentialsNonExpired(true);
		u.setEmail(getField("emailAddress").getValue().toString());
		u.setEnabled(true);
		u.setFirstName(getField("firstName").getValue().toString());
		u.setLastName(getField("lastName").getValue().toString());
		u.setPhone(getField("phone").getValue().toString());
		u.setMobile(getField("mobile").getValue().toString());
		u.setFax(getField("fax").getValue().toString());
		u.setPointOfSale(getField("pointOfSale").getValue().toString());
		u.setOrganization(Util.getLoggedOrganization());
		u.setMerchant(true);
		if(getAttribute("new").equals("false")){
			SpringUtil.getSecurityService().saveOrUpdateUser(u);
		}else{
			BaseSpringUtil.getBeanOfType(SecurityService.class).registerUser(u);
			setAttribute("new", "false");
		}
		((UserProfileTabModel)getAncestorOfType(EXTabPanel.class).getModel()).setUser(u);
		//getAncestorOfType(EXTabPanel.class).getDescendentOfType(EXProfileForm.class).setUser(u);
		//getAncestorOfType(EXTabPanel.class).getDescendentOfType(EXPermissionTab.class).setUser(u.getUsername());
	}

}
