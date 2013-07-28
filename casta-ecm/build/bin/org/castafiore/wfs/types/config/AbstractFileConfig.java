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
 package org.castafiore.wfs.types.config;

import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.session.SessionImpl;
import org.castafiore.wfs.types.File;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract  class AbstractFileConfig implements ApplicationContextAware{

	
	private String name="";
	
	private String parentDir="";
	

	
	private String editPermissions;
	
	private String readPermissions;
	
	private String owner = null;
	
	private RepositoryService repositoryService;
	
	private ApplicationContext applicationContext;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentDir() {
		return parentDir;
	}

	public void setParentDir(String parentDir) {
		this.parentDir = parentDir;
	}
	
	

	

	public String getEditPermissions() {
		return editPermissions;
	}

	public void setEditPermissions(String editPermissions) {
		this.editPermissions = editPermissions;
	}

	public String getReadPermissions() {
		return readPermissions;
	}

	public void setReadPermissions(String readPermissions) {
		this.readPermissions = readPermissions;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public abstract File getNewInstance();
	
	public abstract void fillInstance(File empty);
	
	public void save()
	{
		File f = getNewInstance();
		//f.setSession(new SessionImpl(repositoryService.getSuperUser(), repositoryService));
		if(owner != null){
			f.setOwner(owner);
		}
		else
		{
			f.setOwner(repositoryService.getSuperUser());
		}
	
		
		f.setEditPermissions(editPermissions);
		f.setReadPermissions(readPermissions);
		fillInstance(f);

		try
		{
				//System.out.println()
			//repositoryService.update(f, repositoryService.getSuperUser());
			f.save();
			
			f.refresh();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}
	
}
