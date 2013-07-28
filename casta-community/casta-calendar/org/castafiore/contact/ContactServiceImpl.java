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
 package org.castafiore.contact;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class ContactServiceImpl implements ContactService {

	private RepositoryService repositoryService; 
	
	private String rootContact = "/root/users/";
	
	private SecurityService securityService;
	
//	public void addContact(String username, Contact contact) throws Exception {
//		
//		repositoryService.saveIn(rootContact + username + "/contact", contact, Util.getRemoteUser());
//	}

	public void deleteContact(int contactId) {
//		File f = repositoryService.getFileById(contactId, Util.getRemoteUser());
//		if(f != null)
//		{
//			repositoryService.delete(f, Util.getRemoteUser());
//		}
	}

	public User getAccociatedUser(Contact contact) throws Exception {
		String username = contact.getUsername();
		
		//repositoryService.getFile("/system/organization/users/" + username, Util.getRemoteUser());
		
		return securityService.loadUserByUsername(username);
		
	}

	public List<Contact> getContacts(String username) throws Exception {
		Directory dir = repositoryService.getDirectory(this.rootContact + username + "/contact", Util.getRemoteUser());
		
		List<Contact> result = new ArrayList<Contact>();
		 FileIterator iter = dir.getFiles();
		 for(int i = 0; i < iter.count(); i ++)
		 {
			 result.add((Contact)iter.get(i));
		 }
		 
		 return result;
		
		
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public String getRootContact() {
		return rootContact;
	}

	public void setRootContact(String rootContact) {
		this.rootContact = rootContact;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	

}
