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

import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.layout.EXXYLayoutContainer;

public class EXLinkDesignableFactory extends AbstractDesignableFactory implements IBeanInfo {

	public EXLinkDesignableFactory() {
		super("EXLinkDesignable");
		setText("Link");
	}

	@Override
	public String getCategory() {
		return "primitive";
	}

	@Override
	public Container getInstance() {
		EXXYLayoutContainer container = new EXDroppableXYLayoutContainer("Link", "a");
		container.setText("Link");
		
		return container;
	}

	public String getUniqueId() {
		return "core:link";
	}
	
	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {

		if(attributeName.equalsIgnoreCase("text")){
			c.setText(attributeValue);
		}else if(attributeName.equals("href")){
			c.setAttribute("href", attributeValue);
		}
	}

	public String[] getRequiredAttributes(){
		return new String[]{"href", "text"};
	}

	public String getInfoAttribute(String key) {
		return "Creates a simple link";
	}

	public String[] getKeys() {
		return new String[]{"description"};
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}
}
