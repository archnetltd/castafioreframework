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
 package org.castafiore.resource;

import java.io.InputStream;

/**
 * Extends the {@link BinaryFileData} to be able to handle binary files in the classpath<br>
 * @author Kureem Rossaye
 *
 */
public class ClasspathFileData extends BinaryFileData {

	private String location;
	
	/**
	 * @see FileData#getInputStream()
	 */
	public InputStream getInputStream() throws Exception {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(getLocation());
	}


	/**
	 * Returns the location of the Resource
	 * @return The location of the resource
	 */
	public String getLocation() {
		if(location.startsWith("/"))
			location = location.substring(1);
		return location;
	}


	/**
	 * Sets the location of the resource. Typically for this implementation, it is a classpath 
	 * @param location The location of the resource
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	

	

}
