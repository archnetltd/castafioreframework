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
 package org.castafiore.blog.ui.ng.form;

import org.castafiore.blog.ui.ng.events.RegisterUserEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.dynaform.FormModel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class RegisterFormModel implements FormModel {
	
	private final static String[] labels = new String[]{"Username :", "Password :", "Email :", "First name :", "Last name :"};
	
	private final static String[] names = new String[]{"username", "password", "email", "firstName", "lastName"};

	public int actionSize() {
		return 1;
	}

	public EXButton getActionAt(int index, DynaForm form) {
		EXButton register = new EXButton("register", "Register");
		register.addEvent(new RegisterUserEvent(), Event.CLICK);
		return register;
	}

	public StatefullComponent getFieldAt(int index, DynaForm form) {
		return new EXInput(names[index]);
	}

	public String getLabelAt(int index, DynaForm form) {
		
		return (labels[index]);
		
	}

	public int size() {
		return labels.length;
	}

}
