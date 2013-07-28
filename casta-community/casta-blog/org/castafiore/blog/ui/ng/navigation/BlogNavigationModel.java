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
 package org.castafiore.blog.ui.ng.navigation;

import org.castafiore.blog.ui.ng.ListModel;
import org.castafiore.blog.ui.ng.events.StaticEvents;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.Util;

public class BlogNavigationModel implements ListModel{
	
	public static String[]labels = new String[]{"Home", "New Post"};

	public Event getEventAt(int index) {
		if(index == 0){
			return StaticEvents.HOME_PAGE_EVENT;
		}
		if(index == 1){
			//should return event to show user info widget
			return null;
		}
		if(index == 2){
			//shopuld return signout event
			return StaticEvents.SIGN_OUT_EVENT;
		}
		return null;
	}

	public String getTextAt(int index) {
		if(index == 0)
			return "Home";
		else if(index == 1){
			
			User u = SpringUtil.getSecurityService().loadUserByUsername(Util.getRemoteUser());
			
			return "Welcome " + u.getFirstName() + " ," + u.getLastName(); 
		}else {
			return "Sign out";
		}
	}

	public int size() {
		String remoteUser = Util.getRemoteUser();
		if(remoteUser.equals("anonymous blogger")){
			return 1;
		}
		return 3;
	}

}
