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
 * Interface for handling binary data
 * 
 * @author arossaye
 * 
 */
public interface FileData {

	/**
	 * 
	 * @return The name of the resource
	 */
	public String getName();

	/**
	 * Sets the name of the resource
	 * 
	 * @param name
	 *            The name of the resource
	 */
	public void setName(String name);

	/**
	 * 
	 * @return Mimetype of the resource
	 */
	public String getMimeType();

	/**
	 * Provides an {@link InputStream} pointing to the resource
	 * 
	 * @return The {@link InputStream} of this resource
	 * @throws Exception
	 */
	public InputStream getInputStream() throws Exception;

	/**
	 * Overwrite the underlying resource with the specified stream
	 * 
	 * @param in
	 *            The {@link InputStream} to write from
	 * @throws Exception
	 *             Whenever there is any exception
	 */
	public void write(InputStream in) throws Exception;

	/**
	 * Sets an accessible url for the resource
	 * 
	 * @param url
	 *            The url accessible via castafiore framework
	 */
	public void setUrl(String url);

}