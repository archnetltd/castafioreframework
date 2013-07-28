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

import org.castafiore.blog.ui.ng.events.SignInEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.dynaform.FormModel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;

public class SignInFormModel implements FormModel{

	public int actionSize() {
		return 1;
	}

	public EXButton getActionAt(int index, DynaForm form) {
		EXButton button = new EXButton("signIn", "Sign In");
		button.addEvent(new SignInEvent(), Event.CLICK);
		return button;
	}

	public StatefullComponent getFieldAt(int index, DynaForm form) {
		if(index == 0){
			EXInput username = new EXInput("username");
			return username;
		}else{
			EXInput password = new EXPassword("password");
			return password;
		}
	}

	public String getLabelAt(int index, DynaForm form) {
		if(index == 0){
			return ("Username :");
		}else{
			return ("Password :");
		}
	}

	public int size() {
		return 2;
	}

}
