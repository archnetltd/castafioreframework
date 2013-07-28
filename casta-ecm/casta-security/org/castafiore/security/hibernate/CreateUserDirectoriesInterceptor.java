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
 package org.castafiore.security.hibernate;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.castafiore.security.User;
import org.castafiore.security.UserProfile;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

/**
 * this interceptor is configured to be executed after the registerUser method SecurityService.
 * It has purpose to create the necessary user directory structure for a new user
 * e.g desktop, documents, etc etc
 * @author kureem
 *
 */
public class CreateUserDirectoriesInterceptor implements MethodInterceptor {
	
	private RepositoryService repositoryService;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		String methodName = invocation.getMethod().getName();
		if(methodName.equals("registerUser")){
			
			User param = (User)invocation.getArguments()[0];

			Object result = invocation.proceed();
			
			String username = param.getUsername();
			
			
			Directory users = repositoryService.getDirectory("/root/users", repositoryService.getSuperUser());
			Directory user = users.createFile(username, Directory.class);//new Directory();
			
			user.setOwner(username);
			
			
			Directory desktop = user.createFile("Desktop", Directory.class);//new Directory();
			
			desktop.setOwner(username);
			
			Directory documents = user.createFile("Documents", Directory.class);//new Directory();
			
			documents.setOwner(username);
			
			user.createFile("Contacts", Directory.class).makeOwner(username);
			
			user.createFile("Bin", Directory.class).makeOwner(username);
			
			
			
			Directory media = user.createFile("Media", Directory.class);
			
			media.makeOwner(username);
			
			media.createFile("Music", Directory.class).makeOwner(username);
			media.createFile("Video", Directory.class).makeOwner(username);
			media.createFile("Images", Directory.class).makeOwner(username);
			
			
			UserProfile profile = user.createFile("profile", UserProfile.class);
			profile.makeOwner(username);
			
			profile.createFile("profileInfo", Directory.class).makeOwner(username);
			profile.createFile("homeInfo", Directory.class).makeOwner(username);
			profile.createFile("businessInfo", Directory.class).makeOwner(username);
			
			
			//blog
			Directory applications = user.createFile("Applications", Directory.class);//new Directory();
			
			applications.makeOwner(username);
			
			
			applications.createFile("Blog", Directory.class).makeOwner(username);
			applications.createFile("Forum", Directory.class).makeOwner(username);
			applications.createFile("Designer", Directory.class).makeOwner(username);
			
			try{
				Class<? extends Directory> c = (Class)Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.shoppingmall.merchant.Merchant");
				Directory shoppingmall = applications.createFile("e-Shop", c);//new Directory();
	
				shoppingmall.makeOwner(username);
	
				shoppingmall.createFile("Favorite",  Directory.class).makeOwner(username);
				shoppingmall.createFile("Orders",  Directory.class).makeOwner(username);
				
				
				Directory contacts = shoppingmall.createFile("Contacts",  Directory.class);//new Directory();
				
				contacts.setOwner(username);
	
				contacts.createFile("Default",  Directory.class).makeOwner(username);
				contacts.createFile("Collaborators",  Directory.class).makeOwner(username);
				contacts.createFile("Friends and Family",  Directory.class).makeOwner(username);
				contacts.createFile("Business",  Directory.class).makeOwner(username);
				Directory produc = (Directory)shoppingmall.createFile("Products",  Directory.class).makeOwner(username);
	
				
				produc.createFile("draft",  Directory.class).makeOwner(username);
				produc.createFile("published",  Directory.class).makeOwner(username);
				produc.createFile("deleted",  Directory.class).makeOwner(username);
				
				
				
				Directory messages = shoppingmall.createFile("Messages",  Directory.class);//new Directory();
				
				messages.makeOwner(username);
				
				
				messages.createFile("Inbox",  Directory.class).makeOwner(username);
				messages.createFile("Sent",  Directory.class).makeOwner(username);
				messages.createFile("Draft",  Directory.class).makeOwner(username);
				messages.createFile("Bin",  Directory.class).makeOwner(username);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			users.save();
			
			return result;
			
			
		}
		
		return invocation.proceed();
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
	
	
	
	

}
