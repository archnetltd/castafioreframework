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
 package org.castafiore.wfs.security;

import org.castafiore.profile.Profiler;
import org.castafiore.security.api.SecurityService;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.InsufficientPriviledgeException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class SecurityManagerImpl  implements SecurityManager {
	
	private SecurityService securityService;
	


	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	
	
	private String getRecursivePermission(File file, int type){
		String perm = null;
		if(type == 0){
			perm = file.getReadPermissions();
		}else{
			perm = file.getEditPermissions();
		}
		
		  
		if(perm == null){
			File parent = file.getParent();
			if(parent == null){
				return null;
			}
			else {
				return getRecursivePermission(file.getParent(), type);
			}
		}else{
			return perm;
		}
	}

	public void checkRead(File file, String remoteUser) throws InsufficientPriviledgeException {
	
		
		
		
		
		String readPermission = getRecursivePermission(file, 0);
		
		//while( file.getReadPermissions();
		//RepositoryService service = SpringUtil.getRepositoryService();
		if(remoteUser == null)
		{
			
			throw new InsufficientPriviledgeException(file, "should be logged in to access the file " + file.getAbsolutePath(), remoteUser);
		}
		//if(remoteUser.equals(service.getSuperUser()))
			//return;
		
		
		
		if("*".equals(readPermission))
		{
			
			return;
		}
		
		if(file.getOwner() != null && file.getOwner().equals(remoteUser))
		{
			
			return;
		}
		if(!StringUtil.isNotEmpty(readPermission))
		{
			
			throw new InsufficientPriviledgeException(file, "read", remoteUser);
		}
		//String remoteUser  = Util.getRemoteUser();
		
		String[] accessPermissions = StringUtil.split(readPermission, ";");
		try
		{
			boolean hasPermission = securityService.isUserAllowed(accessPermissions, remoteUser);
			if(! hasPermission)
			{
				
				throw new InsufficientPriviledgeException(file, "read", remoteUser);
			}
		}
		catch(InsufficientPriviledgeException ise){
			throw ise;
		}
		catch(Exception e)
		{
			
			throw new RuntimeException("was unable to check if user " + remoteUser + " has read permission on file " + file.getName() + " due to some unknown exception ", e);
		}
		
		//securityService.isUserAllowed(accessPermission, username)
		
	}

	public void checkWrite(File file, String remoteUser) throws InsufficientPriviledgeException {
		//String editPermission = file.getEditPermissions();
		
		String editPermission = getRecursivePermission(file, 1);
		
		//RepositoryService service = SpringUtil.getRepositoryService();
		
		//if(remoteUser.equals(service.getSuperUser()))
			//return;
		
		if(file != null){
			System.out.println("Check write on :" + file.getAbsolutePath() + " user : " + remoteUser);
		}
		
		
		if("*".equals(editPermission))
		{
			return;
		}
		
		if(file.getOwner() != null && file.getOwner().equals(remoteUser))
		{
			return;
		}
		if(!StringUtil.isNotEmpty(editPermission))
		{
			throw new InsufficientPriviledgeException(file, "edit", remoteUser);
		}
		//String remoteUser  = Util.getRemoteUser();
		
		String[] accessPermissions = StringUtil.split(editPermission, ";");
		try
		{
			boolean hasPermission = securityService.isUserAllowed(accessPermissions, remoteUser);
			if(! hasPermission)
			{
				throw new InsufficientPriviledgeException(file, "write", remoteUser);
			}
		}
		catch(InsufficientPriviledgeException ipe){
			throw ipe;
		}
		catch(Exception e)
		{
			throw new RuntimeException("was unable to check if user " + remoteUser + " has write permission on file " + file.getName() + " due to some unknown exception ", e);
		}
		
	}

	public void grantReadPermission(File file, 
			String permissionSpec) {
		grantPermission(file, "read", permissionSpec);
		
	}
	
	public void grantWritePermission(File file, 
			String permissionSpec) {
		grantPermission(file, "write", permissionSpec);
		
	}

	public void grantPermission(File file, String permissionType,
			String permissionSpec) {
		String permissions = "";
		
		if(permissionSpec.equals("*"))
		{
			//trying to grand full permission
			if(permissionType.equalsIgnoreCase("read"))
			{
				file.setReadPermissions("*");
			}
			else
			{
				file.setEditPermissions("*");
			}
			return;
		}
		
		String[] specs = StringUtil.split(permissionSpec,":");
		
		
		
		String group = specs[1];
		
		if(permissionType.equalsIgnoreCase("read"))
		{
			permissions = file.getReadPermissions();
		}
		else
		{
			permissions = file.getEditPermissions();
		}
		if(permissions == null)
		{
			permissions = "";
		}
		if(permissions.equalsIgnoreCase("*"))
		{
			//already has full permisssion, we don't do anything
			return;
		}
		String[] asPermissions = StringUtil.split(permissions, ";");
		for(String permission : asPermissions)
		{
			if(permissionSpec.equalsIgnoreCase(permission))
			{
				// already has a permission configured according to the specification spec
				return;
			}
			String[] parts = StringUtil.split(permission, ":");
			if(parts[1].equalsIgnoreCase(group) && parts[0].equalsIgnoreCase("*"))
			{
				//we already have something like "*:group"
				return;
			}
		}
		if(permissions.length() == 0)
		{
			permissions = permissionSpec;
		}
		else
		{
			permissions = permissions + ";" + permissionSpec;
		}
		
		if(permissionType.equalsIgnoreCase("read"))
		{
			file.setReadPermissions(permissions);
		}
		else
		{
			file.setEditPermissions(permissions);
		}
		
	}

	public String[] getPermissions(File file, String type) {
		
		String permissions ="";
		if(type.equals("read"))
		{
			permissions = getRecursivePermission(file, 0);
		}
		else
		{
			permissions = getRecursivePermission(file, 1);
		}
		
		if(StringUtil.isNotEmpty(permissions))
		{
			return StringUtil.split(permissions, ";");
		}
		return null;
	}

	public String[] getReadPermissions(File file) {
		return getPermissions(file, "read");
	}

	public String[] getWritePermissions(File file) {
		return getPermissions(file, "write");
	}

	public void checkRead(File file) throws InsufficientPriviledgeException {
		checkRead(file, Util.getRemoteUser());
		
	}

	public void checkWrite(File file) throws InsufficientPriviledgeException {
		checkWrite(file, Util.getRemoteUser());
		
	}

	
	

}
