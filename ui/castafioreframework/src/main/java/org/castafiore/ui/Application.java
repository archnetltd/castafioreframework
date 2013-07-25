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

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.ex.EXApplication;

/**
 * This interface defines an Application. The provided implementation is {@link EXApplication}. All applications created in Castafiore should extend {@link EXApplication}.
 * 
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public interface Application extends Container  {
	
	/**
	 * returns the config context. This context is basically used to create user informations.
	 * 
	 * It is by no means created in web.xml
	 * @return
	 */
	public Map<String, Object> getConfigContext();
	
	/**
	 * returns the logged user
	 * This is simply a convenient method that returns the attribute remoteuser from the config context
	 * It is up to the user to find a way to set the atttribe remoteuser into the config context in his implementation of this interface
	 * @return The logged user
	 */
	public String getLoggedUser();
	
	/**
	 * returns the locale of the application
	 * This is simply a convenient method that returns the attribute "locale" from the config context
	 * It is up to the user to set the attribute locale of type {@link Locale} into the config context in his own implementation of this interface 
	 * @return
	 */
	public Locale getLocale();
	
	/**
	 * returns the ServletContextName
	 * This method does not need initialisation. It is set automatically into the engine.
	 * @return
	 */
	public String getContextPath();
	
	/**
	 * returns the server name
	 * @return
	 */
	public String getServerName();
	
	/**
	 * returns the server port
	 * @return
	 */
	public String getServerPort();
	
	/**
	 * returns buffered resources
	 * @return
	 */
	public Set<String> getBufferedResources();
	
	/**
	 * sets a cookie into the web application
	 * it will overwrite existing one
	 * @param name
	 * @param value
	 */
	public void setCookie(String name, String value);
	
	
	/**
	 * returns a cookie
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getCookie(String name, String defaultValue);
	
	/**
	 * 
	 * @return The session id of the session
	 */
	public String getSessionId();
	
	/**
	 * 
	 * @return The remote address of the web application
	 */
	public String getRemoteAddress();

}
