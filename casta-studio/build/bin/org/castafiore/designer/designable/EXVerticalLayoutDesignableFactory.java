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
import org.castafiore.designer.layout.EXDroppableVerticalLayoutContainer;
import org.castafiore.ui.Container;

public class EXVerticalLayoutDesignableFactory extends AbstractDesignableFactory implements IBeanInfo {

	public EXVerticalLayoutDesignableFactory() {
		super("EXColumnLayoutDesignable");
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		EXDroppableVerticalLayoutContainer l = new EXDroppableVerticalLayoutContainer();
		l.setName("Vertical Layout");
		return l;
	}

	public String getUniqueId() {
		return "core:verticallayout";
	}

	public String getInfoAttribute(String key) {
		return "Creates a vertical layout";
	}

	public String[] getKeys() {
		return new String[]{"description"};
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		
	}

	@Override
	public String[] getRequiredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
