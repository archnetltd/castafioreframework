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
 package org.castafiore.designer.config.ui;

import java.util.Map;

import org.castafiore.designer.config.DesignerInput;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.tabbedpane.EXTabPanel;

public class EXConfigTabPanel extends EXTabPanel {

	public EXConfigTabPanel(ConfigPanelTabModel model) {
		super("EXConfigTabPanel");
		setModel(model);
		setHeight(Dimension.parse("265px"));
	}
	
	
	public void setContainer(Container c, String[] advancedConfigs, ConfigValues vals){
		setModel(new ConfigPanelTabModel(c, advancedConfigs, vals));
	}

}
