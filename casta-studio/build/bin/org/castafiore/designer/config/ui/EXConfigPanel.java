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

import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXConfigPanel extends EXPanel implements PopupContainer{

	public EXConfigPanel() {
		super("configPanel");
		setDraggable(true);
		setShowCloseButton(true);
		setWidth(Dimension.parse("700px"));
		addChild(new EXOverlayPopupPlaceHolder("pc"));
		addClass("ui-form");
		//getDescendentByName("closeButton").getEvents().clear();
		//getDescendentByName("closeButton").addEvent(HIDE_EVENT, Event.CLICK);
		getBodyContainer().setStyle("float", "left").setStyle("width", "677px");
	}
	
	
	public void setContainer(final Container c, final String[] advancedConfigs, final ConfigValues vals){
		EXConfigTabPanel tabs = getDescendentOfType(EXConfigTabPanel.class);
		
		if(tabs == null){
			tabs =new EXConfigTabPanel(new ConfigPanelTabModel(c, advancedConfigs, vals));
			tabs.setStyle("border", "none").setStyle("width", "100%").setStyle("height", "450px");
			setBody(tabs);
		}else{
			ComponentUtil.iterateOverDescendentsOfType(tabs, ConfigPanel.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container container) {
					((ConfigPanel)container).setContainer(c, advancedConfigs, vals);
					
				}
			});
			//tabs.setContainer(c, advancedConfigs, pValues);
		}
		
		setTitle("configure " + c.getName());
		setDisplay(true);
		//super.addPopup(popup)
	}
	
	public void addPopup(Container container){
		getChild("pc").addChild(container);
		//getChild("pc").setDisplay(true);
	}
	
	
	//piu

}
