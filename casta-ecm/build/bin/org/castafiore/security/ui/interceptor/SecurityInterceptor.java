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
package org.castafiore.security.ui.interceptor;

import org.castafiore.security.api.SecurityService;
import org.castafiore.security.ui.HideComponentSecurityInterceptor;
import org.castafiore.security.ui.SecurityAble;
import org.castafiore.security.ui.SecurityInterceptorHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.interceptors.Interceptor;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Nov 20, 2008
 */
public class SecurityInterceptor implements Interceptor {
	
	

	public Interceptor next() {
		return null;
	}

	public boolean onRender(Container container) {
		if(container instanceof SecurityAble)
		{
			try
			{
				
				SecurityAble sa = (SecurityAble)container;
				SecurityInterceptorHandler interceptor = sa.getInterceptor();
				if(interceptor == null){
					interceptor = HideComponentSecurityInterceptor.INSTANCE;
				}
				String accessPermission = sa.getAccessPermission();
				String remoteUser = sa.getRemoteUser();
				if(accessPermission == null)
					accessPermission = "";
				
				if("*".equalsIgnoreCase(accessPermission))
				{
					interceptor.doInterceptOnUserAllowed(sa);
				}
				else
				{
					SecurityService service = SpringUtil.getBean("securityService");
					if( service.isUserAllowed(accessPermission, remoteUser))
					{
						interceptor.doInterceptOnUserAllowed(sa);
					}
					else
					{
						interceptor.doInterceptOnUserNotAllowed(sa);
					}
					
				}
			}
			catch(Exception e)
			{
				throw new RuntimeException("error occured while trying to perform security check",e);
			}
			
		}
		else
		{
			throw new IllegalStateException("a container that is not an instance of " + SecurityAble.class.getName() + " is undergoing security check");
		}
		return true;
	}



}
