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
 package org.castafiore.ui.ex.i18n.edit;

import java.util.Locale;

public interface MessageStore {
	
	/**
	 * overrides and update the key with the message for the specified locale
	 * @param key
	 * @param message
	 * @param locale
	 */
	public void store(String key, String message,Locale locale);
	
	/**
	 * returns the message.
	 * Null if message not found
	 * @param key
	 * @param message
	 * @return
	 */
	public String getMessage(String key, Locale locale);
	
	
	/**
	 * checks if code is present
	 * @param code
	 * @param locale
	 * @return
	 */
	public boolean hasCode(String code, Locale locale);
	
	/**
	 * removes the specified code from cache
	 * @param code
	 */
	public void clearCache(String code);
	
	/**
	 * clear cache completely
	 */
	public void clearCache();
	
	

}
