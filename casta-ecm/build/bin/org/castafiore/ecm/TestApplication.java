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
 package org.castafiore.ecm;

import org.castafiore.ecm.ui.mac.ecm.ListDirectoryMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ShowFileOptionsEventHandler;
import org.castafiore.security.api.SecurityService;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.mac.EXMacFinder;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.utils.BaseSpringUtil;

public class TestApplication extends EXApplication {

	public TestApplication() {
		super("ecmtest");
		try{
			BaseSpringUtil.getBeanOfType(SecurityService.class).login("system", "admin");
			EXMacFinder finder = new EXMacFinder("");
			EXMacFinderColumn column = new EXMacFinderColumn("");
			ListDirectoryMacFinderViewModel model = new ListDirectoryMacFinderViewModel("/root/users", null);
			column.setViewModel(model);
			OpenColumnEventHandler handler = new ShowFileOptionsEventHandler(null);
			column.setOpenColumnEventHandler(handler);
			finder.addColumn(column);
			
			addChild(finder);
			//addChild(new EXQueryBuilder(""));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
