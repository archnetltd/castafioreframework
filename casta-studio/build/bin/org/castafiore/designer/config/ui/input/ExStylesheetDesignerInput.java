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

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.castafiore.designer.config.DesignerInput;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.utils.StringUtil;

public class ExStylesheetDesignerInput extends EXTextArea implements DesignerInput {

	public ExStylesheetDesignerInput(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void applyConfigOnContainer(Container c) {
		String sheets =getValue().toString();
		
		String[] asSheets = StringUtil.split(sheets.trim(), ",");
		if(asSheets != null && asSheets.length > 0){
			for(String sheet : asSheets){
				if(StringUtils.isNotEmpty(sheet)){
					if(c.getResources() == null || !c.getResources().contains(sheet)){
						c.addStyleSheet(sheet);
						if(c.rendered()){
							c.setRendered(false);
						}
					}
					
				}
			}
		}
		
	}

	public void initialise(Container c) {
		Set<String> res = c.getResources();
		StringBuilder b = new StringBuilder();
		if(res != null){
			for(String s : res){
				b.append(s).append(",");
			}
		}
		
		if(b.length() > 0){
			setValue(b.toString().substring(0, b.length()-1));
		}
		
	}

	public void applyConfigOnContainer(String stringRepresentation, Container c) {
		setValue(stringRepresentation);
		applyConfigOnContainer(c);
		
	}

	public String getStringRepresentation() {
		return getValue().toString();
	}

}
