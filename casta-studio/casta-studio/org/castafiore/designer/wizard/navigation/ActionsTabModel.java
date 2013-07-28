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
 package org.castafiore.designer.wizard.navigation;

import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class ActionsTabModel implements TabModel {

	private String[] titles = new String[] { "Uri", "Page reference", "Macro" };

	public int getSelectedTab() {

		return 0;
	}

	public Container getTabContentAt(TabPanel pane, int index) {
		if (index == 0) {
			return new UriTab();
		} else if (index == 1) {
			return new PageReferenceTab();
		} else {
			return new MacroTab("macro");
		}
	}

	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return titles[index];
	}

	public int size() {
		return titles.length;
	}

}
