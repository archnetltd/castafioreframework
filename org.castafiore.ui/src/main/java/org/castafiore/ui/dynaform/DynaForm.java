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
package org.castafiore.ui.dynaform;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.button.Button;

/**
 * 
 * Interface for handling a dynamic form.<br>
 * It is recommended to make components implement this interface to handle
 * dynamic forms.<br>
 * 
 * 
 * @author Kureem Rossaye
 * 
 */
public interface DynaForm extends Container {

	/**
	 * 
	 * @return returns a map of fields configured in this Form
	 */
	public Map<String, FormComponent<?>> getFieldsMap();

	/**
	 * 
	 * @param name
	 *            The name of the field to search
	 * @return The Form component with the specified name
	 */
	public FormComponent<?> getField(String name);

	/**
	 * Adds a field with the specified label
	 * 
	 * @param label
	 *            - The label of the field
	 * @param input
	 *            - The field
	 * @return
	 */
	public DynaForm addField(String label, FormComponent<?> input);

	/**
	 * Adds a button to the form
	 * 
	 * @param button
	 *            The button to add
	 * @return This current form
	 */
	public DynaForm addButton(Button button);

	/**
	 * 
	 * @return All fields in the form of a list
	 */
	public List<FormComponent<?>> getFields();

	/**
	 * Sets the label for a specified field
	 * 
	 * @param label
	 *            The label of the field
	 * @param c
	 *            The form component to set label for
	 */
	public void setLabelFor(String label, FormComponent<?> c);

}
