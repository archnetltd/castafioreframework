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

import org.castafiore.ui.engine.ClientProxy;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXCheckBox extends EXInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXCheckBox(String name) {
		this(name, false);
	}

	public EXCheckBox(String name, boolean checked) {
		super(name);
		setReadOnlyAttribute("type", "checkbox");
		if (checked) {
			super.setValue("checked");
			super.setAttribute("checked", "checked");
		} else
			super.setValue("");

	}

	public boolean isChecked() {
		return "checked".equalsIgnoreCase(getValue().toString());
	}

	public void setChecked(boolean checked) {
		if (checked) {
			super.setValue("checked");
		} else {
			super.setValue("");
		}
		if (!rendered() && getParent() != null) {
			getParent().setRendered(false);
		} else {
			setRendered(false);
		}

	}

	public void onReady(ClientProxy proxy) {
		if (isChecked()) {
			proxy.setAttribute("checked", "checked");
		}
	}

}
