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
 package org.castafiore.wfs.aop;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.castafiore.security.User;

public class UserPermissionSpecInterceptor implements MethodInterceptor {
	
	private Map<String, String[]> specs = Collections.synchronizedMap(new WeakHashMap<String, String[]>());
	
	private Map<String, Boolean> isUserAllowedCache = Collections.synchronizedMap(new WeakHashMap<String, Boolean>());

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		if(invocation.getMethod().getName().equals("isUserAllowed")){
			if(invocation.getArguments()[0] instanceof String[]){
				String[] permSpec = (String[])invocation.getArguments()[0];
				String username = (String)invocation.getArguments()[1];
				
				StringBuilder b = new StringBuilder();
				for(String s : permSpec){
					b.append(s).append(";");
				}
				b.append(username);
				if(isUserAllowedCache.containsKey(b.toString())){
					return isUserAllowedCache.get(b.toString());
				}else{
					Boolean result = (Boolean)invocation.proceed();
					isUserAllowedCache.put(b.toString(), result);
					return result;
				}
			}		
		}
		
		if(invocation.getMethod().getName().equals("getPermissionSpec")){
			String username = (String)invocation.getArguments()[0];
			if(specs.containsKey(username)){
				System.out.println("permission spec for user:" + username + " obtained from cache");
				return specs.get(username);
			}
			else{
				String[] spec = (String[])invocation.proceed();
				
				specs.put(username, spec);
				return spec;
			}
		}
		else if (invocation.getMethod().getName().equals("assignSecurity")){
			String username = null;
			if(invocation.getArguments()[0] instanceof String){
				username  = (String)invocation.getArguments()[0];
			}
			else{
				User u = (User)invocation.getArguments()[0];
				username = u.getUsername();
			}
			specs.remove(username);

			return invocation.proceed();			
		}
		
		else if (invocation.getMethod().getName().equals("unAssignSecurity")){
			String username = null;
			if(invocation.getArguments()[0] instanceof String){
				username  = (String)invocation.getArguments()[0];
			}
			specs.remove(username);

			return invocation.proceed();			
		}
		return invocation.proceed();
	}
}
