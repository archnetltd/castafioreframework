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
 package org.castafiore.designer.config.ui.input;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.Studio;
import org.castafiore.designer.config.DesignerInput;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

/**
 * This class simply set a property into the specified container when calling applyConfigOnCopntainer.
 * 
 * To put it bluntly, if you want to configure an attribute e.g. src on a container, create an instance of this class, set its name src.
 * @author kureem
 *
 */
public class GenericAttributeDesignerInput extends EXInput implements DesignerInput {

	public GenericAttributeDesignerInput(String name) {
		super(name);
	}

	public void applyConfigOnContainer(Container c) {
		Map<String,String> attrs = new HashMap<String, String>();
		attrs.put(getName(), getValue().toString());
		//c.setAttribute(getName(), getValue().toString());
		Studio.applyAttributes(c, attrs);
	}

	public void initialise(Container c) {
		if(StringUtil.isNotEmpty(c.getAttribute(getName())))
		setValue(c.getAttribute(getName()));
		
	}

	public void applyConfigOnContainer(String stringRepresentation, Container c) {
		setValue(stringRepresentation);
		applyConfigOnContainer(c);
		
	}

	public String getStringRepresentation() {
		return getValue().toString();
	}

	

}
