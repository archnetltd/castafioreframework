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

import org.castafiore.designer.designable.ConfigValue;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.designer.designable.data.AbstractDataDesignableFactory;
import org.castafiore.ui.Container;

public class EXDateRepeaterDesignableFactory extends AbstractDataDesignableFactory {

	public EXDateRepeaterDesignableFactory() {
		super("EXDateRepeaterDesignableFactory");
		setText("Data repeater");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Container getInstance() {
		
		EXDataRepeater repeater = new EXDataRepeater();
		return repeater;
		
	}

	public String getUniqueId() {
		return "data:datarepeater";
	}

	
	@Override
	@ConfigValues(configs={@ConfigValue(attribute="templatelocation",values="search:pages")})
	public String[] getRequiredAttributes() {
		return new String[]{"templatelocation", "injectionScript"};
	}

//	@Override
//	public void initialise(Container c, Map<String, String> attributes) {
//		// TODO Auto-generated method stub
//		super.initialise(c, attributes);
//		EXDataRepeater repeater = (EXDataRepeater)c;
//		repeater.setTemplateLocation(attributes.get("templatelocation"));
//	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		// TODO Auto-generated method stub
		super.applyAttribute(c, attributeName, attributeValue);
		
		if(attributeName.equals("templatelocation")){
			EXDataRepeater repeater = (EXDataRepeater)c;
			repeater.setTemplateLocation(attributeValue);
		}else if(attributeName.equals("injectionScript")){
			EXDataRepeater repeater = (EXDataRepeater)c;
			repeater.setInjectionScript(attributeValue);
		}
	}

	
	
	

}
