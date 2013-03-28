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
import org.castafiore.ui.ex.form.button.Button;

public interface DynaForm extends Container{
	
	public Map<String, StatefullComponent> getFieldsMap();
	
	public StatefullComponent getField(String name);
	
	public DynaForm addField(String label, StatefullComponent input);
	
	public DynaForm addButton(Button button);
	
	public List<StatefullComponent> getFields();
	
	
	public void setLabelFor(String label, StatefullComponent c);
	
	

}
