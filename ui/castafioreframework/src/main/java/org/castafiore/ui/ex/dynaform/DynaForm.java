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
 package org.castafiore.ui.ex.dynaform;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.button.Button;

/**
 * 
 * Interface for handling a dynamic form.<br>
 * It is recommended to make components implement this interface to handle dynamic forms.<br>
 * 
 * 
 * @author Kureem Rossaye
 *
 */
public interface DynaForm extends Container{
	
	/**
	 * returns a map of fields configured in this Form
	 * @return
	 */
	public Map<String, StatefullComponent> getFieldsMap();
	
	/**
	 * The Form component with the specified name
	 * @param name
	 * @return
	 */
	public StatefullComponent getField(String name);
	
	/**
	 * Adds a field with the specified label
	 * @param label - The label of the field
	 * @param input - The field
	 * @return
	 */
	public DynaForm addField(String label, StatefullComponent input);
	
	/**
	 * Adds a button to the form
	 * @param button
	 * @return
	 */
	public DynaForm addButton(Button button);
	
	/**
	 * Return all fields in the form of a list
	 * @return
	 */
	public List<StatefullComponent> getFields();
	
	
	/**
	 * Sets the label for a specified field
	 * @param label
	 * @param c
	 */
	public void setLabelFor(String label, StatefullComponent c);
	
	

}
