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

package org.castafiore.ui;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.ex.EXApplication;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */

/**
 * Simple implementation of Application registry
 * 
 * Uses properties file mechanism
 */
public class SimpleApplicationRegistry implements ApplicationRegistry {
	
	private Properties simpleProperties = null;
	
	private static SimpleApplicationRegistry registry = null;

	private SimpleApplicationRegistry ()
	{
		if(simpleProperties == null)
		{
			this.simpleProperties = org.castafiore.utils.Properties.getProperties("application-registry");
		}
	}
	
	
	
	
	




	public Application getApplication(HttpServletRequest request,
			HttpServletResponse response) {
		try
		{
			String applicationId = request.getParameter("casta_applicationid");
			String className = simpleProperties.getProperty(applicationId);
			Class c = Thread.currentThread().getContextClassLoader().loadClass(className);
			return (Application)c.newInstance();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
