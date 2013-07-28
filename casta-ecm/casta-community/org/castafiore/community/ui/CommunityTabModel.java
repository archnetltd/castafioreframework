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
 package org.castafiore.community.ui;

import org.castafiore.community.ui.groups.EXGroupsTab;
import org.castafiore.community.ui.membership.EXMemberShipsTab;
import org.castafiore.community.ui.timetable.EXTimeTableTab;
import org.castafiore.security.logs.EXLogsTab;
import org.castafiore.security.sessions.EXLoggedSessionsTab;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.ComponentUtil;

public class CommunityTabModel implements TabModel {

	private final static String[] LABELS = new String[]{"Users", "Groups", "Membership", "Time table", "Logs", "Logged Sessions"};
	
	public int getSelectedTab() {
	
		return 0;
	}

	public Container getTabContentAt(TabPanel pane, int index) {
		try{
			if(index == 0){
				return new EXUsersTab("usersList");
			}else if(index == 1){
				return new EXGroupsTab("groups");
			}else if(index == 2){
				return new EXMemberShipsTab("memberships");
			}else if(index == 3){
				return new EXTimeTableTab("timetable");
			}else if(index == 4){
				return new EXLogsTab("logs");
			}else if(index == 5){
				return new EXLoggedSessionsTab("sessions");
			}
			return ComponentUtil.getContainer("", "h1", "Under construction", null);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return LABELS[index];
	}
	public int size() {
		return LABELS.length;
	}

}
