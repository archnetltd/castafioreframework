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

package org.castafiore.ui.ex.dynaform;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
/**
 * This class represents a form model. It is used with the {@link EXDynaForm} component. 
 * 
 * 
 * 
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public interface FormModel {
	
	
	/**
	 * Returns the number of input fields to render on the form
	 * @return
	 */
	public int size();
	
	/**
	 * returns the number of buttons to render on the form
	 * @return
	 */
	
	public int actionSize();
	
	
	/**
	 * returns the Field at the specified index
	 * @param index	- the index
	 * @param form	- the form on which the field is to be rendered
	 * @return
	 */
	public StatefullComponent getFieldAt(int index, DynaForm form);
	
	/**
	 * returns the label of the field at the specified index
	 * Here we prefer to make return EXContainer instead of a simple string in order to provide more flexibility
	 * Abstract implementation of this interface can easily be created to abstract the EXContainer in order to return simple string
	 * it is up to the user to do so.
	 * @param index	- The index
	 * @param form	- The form on which the field is to be rendered
	 * @return
	 */
	public String getLabelAt(int index, DynaForm form);
	
	
	/**
	 * returns the button at the specified index to be rendered on the form
	 * It is up to the user to add the required event. This providing greater flexibility
	 * Here as well, we prefer to return an EXContainer i.e. the button itself instead of a simple event.
	 * Abstract implementation of this interface can easily be created to abstract the EXContainer in order to return a simple event
	 * It is up to the user to do so.
	 * @param index	- The index
	 * @param form	- The form on which the button is to be rendered
	 * @return
	 */
	public Button getActionAt(int index, DynaForm form);
	


}
