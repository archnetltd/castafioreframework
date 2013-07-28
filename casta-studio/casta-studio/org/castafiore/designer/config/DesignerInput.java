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
 package org.castafiore.designer.config;

import org.castafiore.designer.config.ui.EXPropertiesConfigForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;

/**
 * This interface is used exclusively in the {@link EXPropertiesConfigForm} to provide a generic way to plug<br>
 * custom config options. See method EXPropertiesConfigForm.setConfigs(..)
 * 
 * User should write the necessary logic to apply the configuration input in this {@link StatefullComponent} to the specified container.
 * The container will be the component currently being configured
 * @author Kureem Rossaye
 *
 */
public interface DesignerInput extends StatefullComponent{
	
	/**
	 * Applies the configuration on the container currently being configured in the EXPropertiesConfigForm
	 * @param c
	 */
	public void applyConfigOnContainer(Container c);
	
	
	/**
	 * apply the string representation to the specified container
	 * @param stringRepresentation
	 * @param c
	 */
	public void applyConfigOnContainer(String stringRepresentation, Container c);
	
	
	/**
	 * initialises this {@link DesignerInput} with the current state of the specified container
	 * @param c
	 */
	public void initialise(Container c);
	
	
	/**
	 * returns the string representation of this {@link DesignerInput}
	 * @return
	 */
	public String getStringRepresentation();
	
	

	
	

}
