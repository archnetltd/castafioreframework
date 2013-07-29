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
 package org.castafiore.ui.engine.context;

import org.castafiore.ui.Application;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * 
 * @author Kureem Rossaye
 * This class is the scope implementation for a castafiore application
 * The scope name is "casta-app".<br>
 * If a bean is labeled with scope casta-app in a spring application context, each different application will have a different instance of this bean.<br>
 * and each Castafiore application will have a single instance of the bean<br>
 * 
 *
 */
public class CastafioreApplicationScope implements Scope {

	/**
	 * loads the 
	 */
	public Object get(String name, ObjectFactory<?> objectFactory) {
		// loads the application set in thread Local using CastafioreApplicationContextFilter
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		if(app != null)
		{
			//finds out if there already exist an instance of the bean in the application configContext
			Object scopedObject = app.getConfigContext().get(name);
			//if no bean created, we store the bean the applications context
			if(scopedObject == null)
			{
				scopedObject =  objectFactory.getObject();
				app.getConfigContext().put(name, scopedObject);
			}
			// we return the same instance of the bean.
			return scopedObject;
			
		}
		return null;
		
	}

	public String getConversationId() {
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		if(app != null)
		{
			return app.getName();
		}
		return null;
	}

	public void registerDestructionCallback(String name, Runnable callback) {
		
	}

	public Object remove(String name) {
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		if(app != null)
		{
			Object scopedObject = app.getConfigContext().get(name);
			if(scopedObject != null)
			{
				return app.getConfigContext().remove(name);
			}
			
			
		}
		return null;
	}



	public Object resolveContextualObject(String arg0) {
		return null;
	}

}
