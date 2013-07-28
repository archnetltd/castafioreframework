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
 package org.castafiore.security.api;

import java.io.Serializable;

import org.castafiore.security.UserProfile;
import org.castafiore.wfs.types.Directory;


/**
 * 
 * @author Kureem Rossaye
 * This class deals with userprofile
 *
 */
public interface UserProfileService extends Serializable {
	
	public final static String INFO_CAT_PROFILE = "profileInfo";
	
	public final static String INFO_CAT_HOME = "homeInfo";
	
	public final static String INFO_CAT_BUSINESS = "businessInfo";
	
	/**
	 * returns the user profile for the specified username
	 * @param username
	 * @return
	 */
	public UserProfile getUserProfile(String username);
	
	/**
	 * returns the value of the specified info from the specified category for the specified user
	 * @param username - the username
	 * @param infoCat - default categories are (profile, homeInfo, businessInfo)
	 * @param infoKey
	 * @return
	 */
	public String getUserProfileValue(String username, String infoCat, String infoKey);

	/**
	 * Saves the value for a userprofile
	 * @param username
	 * @param infoCat
	 * @param infoKey
	 * @param infoValue
	 */
	public void saveUserProfileValue(String username, String infoCat, String infoKey, String infoValue);
	
	/**
	 * Returns the available categories in the profile for the specified username
	 * @param username
	 * @return
	 */
	public String[] getAvailableCategories(String username);
	
	
	/**
	 * returns the available Keys for the UserProfile of the specified username with the specified category
	 * @param username
	 * @param infoCat
	 * @return
	 */
	public String[] getAvailableKeys(String username, String infoCat);
	
	
	public Directory getUserDirectory(String username);

}
