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
import org.castafiore.designer.layout.EXDroppableJerichoContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.EXTemplateComponent;

public class EXJericoContainerDesignableFactory extends AbstractDesignableFactory {

	public EXJericoContainerDesignableFactory() {
		super("EXJericoContainerDesignableFactory");
		setText("Jericho containter");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		return new EXDroppableJerichoContainer();
	}

	public String getUniqueId() {
		return "core:jerico";
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"selector", "elemIndex", "template"};
	}

	@Override
	public void applyAttribute(Container c, String attributeName,String attributeValue) {
		if(attributeName.equals("template")){
			((EXTemplateComponent)c).setTemplateLocation(attributeValue);
		}
		setRendered(false);
	}

}
