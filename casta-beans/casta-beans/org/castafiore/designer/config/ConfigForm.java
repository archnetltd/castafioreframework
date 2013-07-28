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

import org.castafiore.designable.DesignableFactory;
import org.castafiore.ui.Container;
/**
 * Implement this interface, and configure it inside your {@link DesignableFactory}
 * @author Kureem Rossaye
 *
 */
public interface ConfigForm extends Container{
	
	
	
	/**
	 * this method should be used to set the container to be configured, and at the same
	 * time configure this config form according to the current state of the container
	 * In another word, this method should be used to initialise the config form
	 * @param container
	 */
	public void setContainer(Container container);
	
	
	/**
	 * applies the current config form state on its container
	 * does nothing if container is not set yet
	 */
	public void applyConfigs();
	
	
	
	

}
