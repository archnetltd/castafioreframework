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

import org.castafiore.security.UserProfile;
import org.castafiore.security.api.UserProfileService;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Value;

public class UserProfileServiceImpl implements UserProfileService {
	
	private RepositoryService repositoryService;

	public UserProfile getUserProfile(String username) {
		
		return (UserProfile)repositoryService.getFile("/root/users/" + username + "/profile", username);
	}
	
	public Directory checkAndCreateCategory(String category, String username){
		UserProfile profile = getUserProfile(username);
		Directory fcategory = (Directory)profile.getFile(category);
		if(fcategory == null){
			Directory dir = profile.createFile(category, Directory.class);//new Directory();
			
			dir.makeOwner(username);
			
			profile.save();
			return profile;
		}
		return fcategory;
	}

	public String getUserProfileValue(String username, String infoCat,String infoKey) {
		
		try{
			return ((Value)repositoryService.getFile("/root/users/" + username + "/profile/" + infoCat + "/" +infoKey, username)).getString();
			
		}catch(FileNotFoundException nfe){
			return null;
		}
	}

	public void saveUserProfileValue(String username, String infoCat,
			String infoKey, String infoValue) {
		
			Directory dir = repositoryService.getDirectory("/root/users/" + username + "/profile", Util.getRemoteUser());
			//Value data = ((Value)repositoryService.getFile("/root/users/" + username + "/profile/" + infoCat + "/" +infoKey, username));
			
			Directory cat = (Directory)dir.getFile(infoCat);
			if(cat == null){
				cat = dir.createFile(infoCat, Directory.class);
			}
			
			Value data = (Value)cat.getFile(infoKey);
			if(data == null){
				data = cat.createFile(infoKey, Value.class);
			}
			data.setString(infoValue);
			dir.save();
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}


	public String[] getAvailableCategories(String username) {
		try{
			Directory dir = repositoryService.getDirectory("/root/users/" + username + "/profile", username);
		 	return dir.listFileNames();
		}catch(Exception e){
			return null;
		}
	}

	public String[] getAvailableKeys(String username, String infoCat) {
		try{
			Directory dir = repositoryService.getDirectory("/root/users/" + username + "/profile/" + infoCat, username);
		 	return dir.listFileNames();
		}catch(Exception e){
			return null;
		}
	}


	public Directory getUserDirectory(String username) {
		return repositoryService.getDirectory("/root/users/" + username, username);
	}
	
	

}
