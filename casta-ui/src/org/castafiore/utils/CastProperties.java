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

package org.castafiore.utils;

import java.util.Properties;
/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public class CastProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getProperty(String key, String defaultValue) {
		
		String value = super.getProperty(key);
		
		if(value == null)
		{
			return defaultValue;
		}
		else if(value.startsWith("$"))
		{
			return getProperty(value.substring(1));
		}
		else
		{
			return value;
		}
	}

	@Override
	public String getProperty(String key) {
		return getProperty(key, null);
	}
	
	
	
	
	

}
