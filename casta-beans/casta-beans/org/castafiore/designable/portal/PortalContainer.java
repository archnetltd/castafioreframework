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
 package org.castafiore.designable.portal;

import java.util.List;
import java.util.Map;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.dataenvironment.Datasource;

/**
 * 
 * @author Kureem Rossaye
 *
 */
public interface PortalContainer extends DesignableLayoutContainer {
	
	/**
	 * returns the absolute path where this portal is stored
	 * @return
	 */
	public String getDefinitionPath();
	
	/**
	 * Sets the definition path of this portal container
	 * @param path
	 */
	public void setDefinitionPath(String path);
	
	public Map<String,String> getSession();
	
	public PortalContainer addDatasource(Datasource d);
	
	public List<Datasource> getDatasources();

}
