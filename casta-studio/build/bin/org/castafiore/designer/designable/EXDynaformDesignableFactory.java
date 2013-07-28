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
import org.castafiore.designer.layout.EXDroppableDynaForm;
import org.castafiore.ui.Container;

public class EXDynaformDesignableFactory extends AbstractDesignableFactory {

	public EXDynaformDesignableFactory() {
		super("EXDynaformDesignable");
		setText("Dynamic form");
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		return new EXDroppableDynaForm();
	}

	public String getUniqueId() {
		return "core:dynaform";
	}
	
	public String[] getRequiredAttributes(){
		return new String[]{"title"};
	}
	public void applyAttribute(Container c, String attributeName, String attributeValue){
		if(attributeName.equals("title")){
			((EXDroppableDynaForm)c).setTitle(attributeValue);
		}
	}
	
}
