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

import java.util.List;

import org.castafiore.community.ui.CommunityEvents;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

public class EXProfileForm extends EXDynaformPanel{

	public EXProfileForm(User user) {
		super("EXProfileForm", "Address");
		addField("Address Line 1 :", new EXInput("line1"));
		addField("Address Line 2 :", new EXInput("line2"));
		addField("City :", new EXInput("city"));
		addField("Postal Code :", new EXInput("postalCode"));
		addField("Country :", new EXInput("country"));
		//setAttribute("infoCategory", UserProfileService.INFO_CAT_PROFILE);
		
//		DefaultDataModel gender = new DefaultDataModel().addItem("Male").addItem("Female");
//		
//		
//		addField("Gender :", new EXSelect("gender", gender));
//		addField("Employer :", new EXInput("employer"));
//		addField("Department :", new EXInput("department"));
//		addField("Job title :", new EXInput("jobTitle"));
//		
//		DefaultDataModel language = new DefaultDataModel().addItem("English").addItem("French");
//		addField("Language :", new EXSelect("language", language));
		EXButton saveButton = new EXButton("Save", "Save");
		saveButton.setAttribute("method", "save");
		saveButton.addEvent(CommunityEvents.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		addButton(saveButton);
		addButton(new EXButton("Cancel", "Cancel"));
		setShowCloseButton(false);
		setShowHeader(false);
		
		setDraggable(false);
		setWidth(Dimension.parse("100%"));
		setUser(user);
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "270px");
	}
	
	protected void setUser(User user){
		if(user.getUsername() != null){
			setAttribute("username",user.getUsername());
			user = SpringUtil.getSecurityService().hydrate(user);
			
			Address a = user.getDefaultAddress();
			if(a != null){
				getField("line1").setValue(a.getLine1());
				getField("line2").setValue(a.getLine1());
				getField("city").setValue(a.getCity());
				getField("postalCode").setValue(a.getPostalCode());
				getField("country").setValue(a.getCountry());
			}
			
		}
	}
	
	public void save()throws Exception{
		String username = getAttribute("username");
		
		if(StringUtil.isNotEmpty(username)){
			User u = BaseSpringUtil.getBeanOfType(SecurityService.class).loadUserByUsername(username);
			Address a = u.getDefaultAddress();
			if(a == null){
				a = new Address();
				u.addAddress(a);
			}
			a.setCity(getField("city").getValue().toString());
			a.setLine1(getField("line1").getValue().toString());
			a.setLine2(getField("line2").getValue().toString());
			a.setCountry(getField("country").getValue().toString());
			a.setPostalCode(getField("postalCode").getValue().toString());
			a.setDefaultAddress(true);
			BaseSpringUtil.getBeanOfType(SecurityService.class).saveOrUpdateUser(u);
			((UserProfileTabModel)getAncestorOfType(EXTabPanel.class).getModel()).setUser(u);
		}else{
			throw new UIException("Please save the user first before saving its address");
		}
		
		
//		UserProfileService service = BaseSpringUtil.getBeanOfType(UserProfileService.class);
//		List<StatefullComponent> fields = super.getFields();
//		String category = getAttribute("infoCategory");
//		for(StatefullComponent field : fields){
//			String value = field.getValue().toString();
//			String key = field.getName();
//			service.saveUserProfileValue(username, category, key, value);
//		}
		
	}
	
	
	
	
	
	
	
	

}
