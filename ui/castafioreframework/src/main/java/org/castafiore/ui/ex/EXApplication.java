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

package org.castafiore.ui.ex;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.Application;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */


/**
 * Implementation of Application.
 * Most simple implementation
 */
public abstract  class EXApplication extends EXContainer implements Application {

	private Map<String, Object> configs = new LinkedHashMap<String, Object>(0);
	
	private Set<String> resources = new LinkedHashSet<String>(0);
	
	private Set<String> bufferedResources = new LinkedHashSet<String>(0);
	
	private Map<String, String> cookies = new LinkedHashMap<String, String>(0);
	
	private Map<String, String> newCookies = new LinkedHashMap<String, String>(0);
	
	public EXApplication(String name) {
		super(name, "div");
	}

	public Map<String, Object> getConfigContext() {
		return configs;
	}

	public Locale getLocale() {
		if(configs.containsKey("locale"))
		{
			return (Locale)configs.get("locale");
		}
		return Locale.ENGLISH;
	}

	public String getLoggedUser() {
		if(configs.containsKey("remoteuser"))
		{
			return configs.get("remoteuser").toString();
		}
		else
		{
			return "anonymous";
		}
	}
	
	public String getContextPath()
	{
		return getConfigContext().get("contextPath").toString();
	}
	
	public String getSessionId(){
		return getConfigContext().get("sessionid").toString();
	}
	
	public String getRemoteAddress(){
		return getConfigContext().get("remoteAddress").toString();
	}
	
	public void addResource(String url)
	{
		this.bufferedResources.add(url);
		this.resources.add(url);
	}
	
	public Set<String> getBufferedResources()
	{
		return this.bufferedResources;
	}
	
	public void mergeResources(Set<String> resources)
	{
		this.bufferedResources.addAll(resources);
		this.resources.addAll(resources);
	}
	
	public void flush(int secretKey)
	{
		this.bufferedResources.clear();
		super.flush(secretKey);
	}
	
	public String getServerName(){
		return getConfigContext().get("serverName").toString();
	}
	
	
	public String getServerPort(){
		return getConfigContext().get("serverPort").toString();
	}

	public String getCookie(String name, String defaultValue) {
		if(cookies.containsKey(name)){
			return cookies.get(name);
		}else{
			return defaultValue;
		}
	}

	public void setCookie(String name, String value) {
		cookies.put(name, value);
		newCookies.put(name, value);
		
	}
	
	
	public Map<String, String> getNewCookies(){
		return newCookies;
	}
	
	
	

}
