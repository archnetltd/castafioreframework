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
 package org.castafiore.designer.service;

import org.castafiore.beans.info.BeanInfoService;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.InvalidDesignableException;
import org.castafiore.designer.macro.EventMacro;
import org.castafiore.designer.renderer.ListRenderer;

public class DesignerServiceImpl implements DesignableService{

	private BeanInfoService beanInfoService;
	
	public BeanInfoService getBeanInfoService() {
		return beanInfoService;
	} 

	public void setBeanInfoService(BeanInfoService beanInfoService) {
		this.beanInfoService = beanInfoService;
	}

	public DesignableFactory getDesignable(String uniqueDesignableId) {
		
		
		DesignableFactory result = beanInfoService.getBeanOfType(DesignableFactory.class, uniqueDesignableId);
		
		if(result == null)
			throw new InvalidDesignableException("The unique designableId " + uniqueDesignableId + " cannot be found. Probably it was not configured");
		else
			return result;
		
	}

	public EventMacro getMacro(String macroUniqueId) {
		
		
		EventMacro result = beanInfoService.getBeanOfType(EventMacro.class, macroUniqueId);
		
		if(result == null)
			throw new InvalidDesignableException("The unique macroId " + macroUniqueId + " cannot be found. Probably it was not configured");
		else
			return result;
	}

	public ListRenderer<?> getListRenderer(String uniqueId) {
		
		ListRenderer result = beanInfoService.getBeanOfType(ListRenderer.class, uniqueId);
		
		if(result == null)
			throw new InvalidDesignableException("The unique renderer id " + uniqueId + " cannot be found. Probably it was not configured");
		else
			return result;
		
	}

}
