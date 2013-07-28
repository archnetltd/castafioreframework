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

import org.castafiore.security.User;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class UserProfileTabModel implements TabModel{
	
	private final static String[] LABELS = new String[]{"Account setting", "Address Info", "Permission settings"};
	
	private User user;
	
	

	public UserProfileTabModel(User user) {
		super();
		this.user = user;
	}

	public int getSelectedTab() {
		
		return 0;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			return new EXAccountSettingForm(user);
		}else if(index == 1){
			if(user == null || user.getUsername()==null){
				throw new UIException("Please save the user first");
			}
			
			return new EXProfileForm(user);
		}else if(index == 2){
			if(user == null || user.getUsername()==null)
				//return new EXPermissionTab("", null);
				throw new UIException("Please save the user first");
			else
				return new EXPermissionTab("", user.getUsername());
		}else if(index == 3){
			return new EXBusinessInfoForm();
		}else{
			return null;
		}
	}

	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return LABELS[index];
	}

	public int size() {
		
		return LABELS.length;
	}

}
