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
 package org.castafiore.designer.designable.datarepeater;

import org.castafiore.designer.designable.data.AbstractDataDesignableFactory;
import org.castafiore.ui.Container;

public class EXDataContainerDesignableFactory extends AbstractDataDesignableFactory {
	
	public EXDataContainerDesignableFactory() {
		super("EXDataContainerDesignableFactory");
		setText("Data container");
		
	}

	@Override
	public Container getInstance() {
		
		EXDataContainer dataContainer = new EXDataContainer("Data container");
		return dataContainer;
		
	}

	public String getUniqueId() {
		return "data:datacontainer";
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"templatelocation"};
	}


	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		// TODO Auto-generated method stub
		super.applyAttribute(c, attributeName, attributeValue);
		
		if(attributeName.equals("templatelocation")){
			EXDataContainer repeater = (EXDataContainer)c;
			repeater.setTemplateLocation(attributeValue);
		}
	}

}
