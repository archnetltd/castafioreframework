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

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.ui.Container;

public class EXXYLayoutDesignableFactory extends AbstractDesignableFactory {

	public EXXYLayoutDesignableFactory() {
		super("EXXYLayoutDesignable");
		setText("XY layout");
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		EXDroppableXYLayoutContainer lc = new EXDroppableXYLayoutContainer();
		setAttribute("text", "");
		return lc;
	}

	
	
	public void applyAttribute(Container c, String attributeName, String attributeValue){
		
		
	}
	
	public String getUniqueId() {
		return "core:xylayout";
	}

	@Override
	@ConfigValues(configs={
			@ConfigValue(attribute="LayoutMode",values={"Natural", "Absolute"})
	})
	public String[] getRequiredAttributes() {
		return new String[]{"LayoutMode"};
	}



}
