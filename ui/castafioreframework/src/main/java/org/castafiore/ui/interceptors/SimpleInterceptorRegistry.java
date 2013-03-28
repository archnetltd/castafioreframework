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

package org.castafiore.ui.interceptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.castafiore.ui.Container;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class SimpleInterceptorRegistry implements InterceptorRegistry{

	private Properties simpleProperties = null;
	
	private Map<Class, Interceptor> registry = new HashMap<Class, Interceptor>();
	
	private static InterceptorRegistry instance =null;
	public static InterceptorRegistry getInstance()
	{
		try
		{
			if(instance == null)
			{
				instance = new SimpleInterceptorRegistry();
			}
			return instance;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private SimpleInterceptorRegistry() throws Exception
	{
		if(simpleProperties == null)
		{
			this.simpleProperties = org.castafiore.utils.Properties.getProperties("interceptor-registry");
			
			
			Iterator<Object> iterKeys = simpleProperties.keySet().iterator();
			
			while(iterKeys.hasNext())
			{
				String interfaceName = iterKeys.next().toString();
				
				Class c = Thread.currentThread().getContextClassLoader().loadClass(interfaceName);
				
				
				String interceptorName = simpleProperties.getProperty(interfaceName);
				
				Class cInterceptor = Thread.currentThread().getContextClassLoader().loadClass(interceptorName);
				
				Interceptor interceptor= (Interceptor)cInterceptor.newInstance();
				
				registry.put(c, interceptor);
				
				
				
			}
			
		}
	}
	
	
	
	public Interceptor[] getInterceptors(Container container) {
		
		List<Interceptor> interceptors = new ArrayList<Interceptor>();
		Iterator<Class> interfaces = registry.keySet().iterator();
		
		while(interfaces.hasNext())
		{
			Class c = interfaces.next();
			
			if(c.isAssignableFrom(container.getClass()))
			{
				interceptors.add(registry.get(c));
			}
		}
		
		return interceptors.toArray(new Interceptor[interceptors.size()]);
		
		
		
		
	}
	
	

}
