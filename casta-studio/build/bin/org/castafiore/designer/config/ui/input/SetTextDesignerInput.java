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

import org.castafiore.designer.Studio;
import org.castafiore.designer.config.DesignerInput;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.ComponentUtil;

public class SetTextDesignerInput extends EXInput implements DesignerInput {

	public SetTextDesignerInput() {
		super("SetTextDesignerInput");
		
	}

	public void applyConfigOnContainer(Container c) {
		
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("text", getValue().toString());
		Studio.applyAttributes(c, m);
		//c.setText(getValue().toString());
		
	}

	public void initialise(Container c) {
		setValue(c.getText());
		
	}

	public void applyConfigOnContainer(String stringRepresentation, Container c) {
		setValue(stringRepresentation);
		applyConfigOnContainer(c);
		
	}

	public String getStringRepresentation() {
		return getValue().toString();
	}
	

	
	
	

}
