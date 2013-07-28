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
 package org.castafiore.designer.events;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.designer.config.ui.EXConfigPanel;
import org.castafiore.designer.designable.ConfigValue;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.springframework.aop.framework.Advised;

public class ShowConfigPanel implements Event {

	public void ClientAction(ClientProxy container) {
		container.getAncestorOfType(EXDesigner.class).mask();
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXConfigPanel panel = container.getAncestorOfType(EXDesigner.class).getDescendentOfType(EXConfigPanel.class);
		Container c = container.getAncestorOfType(EXConfigItem.class).getContainer();
		
		if(panel == null){
			panel = new EXConfigPanel();
			container.getAncestorOfType(EXDesigner.class).addPopup(panel);
		}
		panel.setDisplay(true);
		String uniqueId = c.getAttribute("des-id");
		Map<String, ConfigForm> advancedConfigs = null;
		String[] req = new String[]{};
		Map<String, String[]> map   = new HashMap<String, String[]>();
		if(StringUtil.isNotEmpty(uniqueId)){
			
			//DesignableFactory des =  container.getAncestorOfType(EXDesigner.class).getDescendentOfType(EXDesignableFactoryToolBar.class).getDesignable(uniqueId);
			DesignableFactory des = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(uniqueId);
		
			//advancedConfigs = des.getAdvancedConfigs();
			if(des == null)
				throw new UIException("the designable factory " + uniqueId + " is probably not  configured");
			req = des.getRequiredAttributes();
			if(req == null){
				req = new String[]{};
			}
			
				Advised advised = (Advised) des;
				Class<?> cls = advised.getTargetSource().getTargetClass();

				//Class<?> cls = des.getClass().getSuperclass();
				try{
			ConfigValues values =cls.getMethod("getRequiredAttributes", new Class[]{}).getAnnotation(ConfigValues.class);
			
			
			panel.setContainer(c, req, values);
				}catch(Exception e){
					panel.setContainer(c, req, null);
				}
			
			
		}
		
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}

}
