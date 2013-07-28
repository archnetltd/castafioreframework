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
 package org.castafiore.designer.portal;

import org.castafiore.designer.designable.EXXYLayoutDesignableFactory;
import org.castafiore.designer.layout.EXDroppablePrimitiveTagLayoutContainer;
import org.castafiore.ui.Container;

public class EXPageContainerDesignableFactory extends EXXYLayoutDesignableFactory {
	
	public EXPageContainerDesignableFactory() {
		super();
		setName("EXPageContainerDesignableFactory");
		setText("Page Container");
	}
	
	
	
	
	@Override
	public String getCategory() {
		return "Portal";
	}

	@Override
	public Container getInstance() {
		EXDroppablePrimitiveTagLayoutContainer lc = new EXDesignablePageContainer();
		//lc.setStyle("min-width", "500px");
		//lc.setStyle("min-height", "300px");
		//lc.setStyle("background-color", "steelBlue");
		setAttribute("text", "");
		return lc;
	}

	public String getUniqueId() {
		
		return "portal:pagecontainer";
	}

}
