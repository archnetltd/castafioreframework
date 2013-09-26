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
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.Var;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXCheckBox extends AbstractFormComponent<Boolean> implements Focusable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXCheckBox(String name) {
		this(name, false);
	}

	public EXCheckBox(String name, boolean checked) {
		super(name, "input");
		setReadOnlyAttribute("type", "checkbox");
		setValue(checked);

	}

	

	public void onReady(ClientProxy proxy) {
		if (getValue()) {
			proxy.addMethod("prop", "checked", true);
		}
		proxy.change(proxy.clone().setAttribute("value", new Var("$(this).prop('checked')")));
	}

	
	
	@Override
	public void setValue(Boolean value) {
		setRendered(false);
		super.setValue(value);
	}

	@Override
	public String serialize(Boolean value) {
		return value.toString();
	}

	@Override
	public Boolean deserialize(String s) {
		return Boolean.parseBoolean(s);
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
