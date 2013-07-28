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
 package org.castafiore.blog.designer;

import org.castafiore.designer.designable.datarepeater.EXDataContainerDesignableFactory;
import org.castafiore.ui.Container;

public class EXPostContainerDesignableFactory extends EXDataContainerDesignableFactory {

	public EXPostContainerDesignableFactory() {
		super();
		setName("EXPostContainerDesignableFactory");
		setText("Blog container");
	}

	@Override
	public Container getInstance() {
		
		EXPostContainer repeater = new EXPostContainer("blog container");
		return repeater;
		
	}

	public String getUniqueId() {
		return "blog:postcontainer";
	}
	
	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"templatelocation", "commentstemplate"};
	}



	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		
		super.applyAttribute(c, attributeName, attributeValue);
		
		if(attributeName.equals("templatelocation")){
			EXPostContainer repeater = (EXPostContainer)c;
			repeater.setTemplateLocation(attributeValue);
		}else if(attributeName.equals("commentstemplate")){
			EXPostContainer repeater = (EXPostContainer)c;
			repeater.getChild("comments").setRendered(false);
		}
	}
	
	@Override
	public String getCategory() {
		return "Community";
	}
	
}
