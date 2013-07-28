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
 package org.castafiore.catalogue.designables;

import org.castafiore.designer.designable.datarepeater.EXDataContainerDesignableFactory;
import org.castafiore.ui.Container;

public class EXProductContainerDesignableFactory extends EXDataContainerDesignableFactory {

	public EXProductContainerDesignableFactory() {
		super();
		setName("EXProductContainerDesignableFactory");
		setText("Product container");
	}

	@Override
	public Container getInstance() {
		
		EXProductContainer repeater = new EXProductContainer("product container");
		return repeater;
		
	}

	public String getUniqueId() {
		return "ecm:productcontainer";
	}
	
	@Override
	public String getCategory() {
		return "ECM";
	}

	
}
