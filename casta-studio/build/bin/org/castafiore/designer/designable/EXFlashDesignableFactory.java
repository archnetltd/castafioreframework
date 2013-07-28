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
 package org.castafiore.designer.designable;

import java.util.Map;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXImage;
import org.castafiore.utils.ResourceUtil;

public class EXFlashDesignableFactory extends AbstractDesignableFactory{

	public EXFlashDesignableFactory() {
		super("EXFlashDesignableFactory");
		setText("Flash");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCategory() {
		return "primitive";
	}
	
	@Override
	public Container getInstance() {
		
		
		EXContainer flash = new EXContainer("plugin", "embed");
		flash.setAttribute("width", "100%");
		flash.setAttribute("height", "100%");
		flash.setAttribute("type", "application/x-shockwave-flash");
		return flash;
	}
	
	public String getUniqueId() {
		return "core:flash";
	}
	
	public String[] getRequiredAttributes(){
		return new String[]{"src", "width", "height"};
	}

	
	
	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		c.setAttribute(attributeValue, attributeValue);
	}

	public String getInfoAttribute(String key) {
		return "Creates a flash ";
	}

	public String[] getKeys() {
		return new String[]{"description"};
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}
	
	

}
