/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.ex.form;

import org.castafiore.ui.AbstractFormComponent;
import org.castafiore.ui.dynaform.Focusable;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public class EXInput extends AbstractFormComponent<String> implements Focusable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXInput(String name, String value) {
		super(name, "input");
		setValue(value);
		setReadOnlyAttribute("type", "text");
	}
	
	public EXInput setPlaceHolder(String placeHolder){
		setAttribute("placeholder", placeHolder);
		return this;
	}
	
	public String getPlaceHolder(){
		return getAttribute("placeholder");
	}

	public EXInput(String name) {
		super(name, "input");
		setReadOnlyAttribute("type", "text");
	}

	

	public void setEnabled(boolean enabled) {
		super.setAttribute("disabled", Boolean.toString((!enabled)));
	}

	public boolean isEnabled() {
		try {
			return !Boolean.parseBoolean(super.getAttribute("disabled"));
		} catch (Exception e) {

		}

		return true;
	}

	@Override
	public String serialize(String value) {
		return value;
	}

	@Override
	public String deserialize(String s) {
		return s;
	}

	@Override
	public int getTabIndex() {
		try{
		return Integer.parseInt(getAttribute("tabindex"));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public void setAccessKey(char key) {
		
		setAttribute("accesskey", new String(new char[]{key}));
	}

	@Override
	public void setFocus(boolean focused) {
		setAttribute("hasfocus", focused + "");
	}

	@Override
	public void setTabIndex(int index) {
		setAttribute("tabindex", index + "");
		
	}

}
