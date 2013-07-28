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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.castafiore.profile.Profiler;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.DelectedFileException;
import org.castafiore.wfs.InsufficientPriviledgeException;
import org.castafiore.wfs.security.SecurityManager;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.session.SessionImpl;
import org.castafiore.wfs.types.File;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SecurityInterceptor implements MethodInterceptor, ApplicationContextAware {
	
	private static List<String> readMethods = new ArrayList<String>();
	
	private ApplicationContext applicationContext;
	
	static{
		readMethods.add("getDrives");
		readMethods.add("getFile");
		readMethods.add("executeQuery");
		readMethods.add("getFilesOfType");
		readMethods.add("getFileById");
		readMethods.add("getDirectory");
		//readMethods.add("fullTextSearch");
		
	}

	
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//MethodInvocation invocation
		
		
		
		
		Method method = invocation.getMethod();
		
		String methodName = method.getName();
		
		if(readMethods.contains(methodName))
		{
			Object result = invocation.proceed();
			
			
			String username = (String)invocation.getArguments()[invocation.getArguments().length-1];
			System.out.println("Applying read check on method : " + methodName + " against user : " + username);
			//System.out.println("check read permission against user: " + username);
			if(result instanceof List)
			{
				List<File> filteredResult = new ArrayList<File>();
				for(Object o : (List)result)
				{
					if(o instanceof File){
						try
						{
							File file = (File)o;
							getSecurityManager().checkRead(file, username);
							if(file.getStatus() != File.STATE_DELETED){
								filteredResult.add((File)o);
								//((File)o).setSession(new SessionImpl(username,getRepositoryService()));
							}
						}
						catch(InsufficientPriviledgeException in){
							
						}
					}
				}
				
				return filteredResult;
			}
			else if(result instanceof File)
			{
				System.out.println("Applying read check on method : " + methodName + " against user : " + username);
				getSecurityManager().checkRead((File)result, username);
				
				File file = (File)result;
				if(file.getStatus() == File.STATE_DELETED){
					throw new DelectedFileException(file, username);
				}
				//((File)result).setSession(new SessionImpl(username, getRepositoryService()));
				
				return result;
			}
			else
			{
				
				return result;
			}
		}
		else if(methodName.equals("delete") || methodName.equals("update")){
			
			File file = (File)invocation.getArguments()[0];
			String username = (String)invocation.getArguments()[1];
			System.out.println("Applying write check on method : " + methodName + " against user : " + username);
			getSecurityManager().checkWrite(file, username);
			
			return invocation.proceed();
			//return file;
		}
		else if(methodName.equals("saveIn")){
			String username = (String)invocation.getArguments()[2];
			
			String path = invocation.getArguments()[0].toString();
			//if path is empty, the we are adding a file in root
			if(StringUtil.isNotEmpty(path)){
				File file = (File)getRepositoryService().getDirectory(invocation.getArguments()[0].toString(), username);
			
			 
				if(file.getAbsolutePath() != null){
					System.out.println("Applying write check on method : " + methodName + " against user : " + username);
					getSecurityManager().checkWrite(file, username);
				}
			}
			
			return invocation.proceed();
		}
		else
		{
			//System.out.println("Applying read check on method : " + methodName + " against user : " + username);
			
			return invocation.proceed();
		}
		//Profiler.stop("checkPermission.....");
		//return result;
	}

	public SecurityManager getSecurityManager(){
		return (SecurityManager)applicationContext.getBean("securityManager");
	}
	
	public RepositoryService getRepositoryService(){
		return (RepositoryService)applicationContext.getBean("repositoryService");
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}



	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}



	



	

}
