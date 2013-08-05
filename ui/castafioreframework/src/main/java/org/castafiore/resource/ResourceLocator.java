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

/**
 * Interface for resolving resources based on the specification.<br>
 * Implementations of this interface can optionally resize the resource to the specified width if possible
 * @author Kureem Rossaye
 *
 */
public interface ResourceLocator {
	
	/**
	 * Returns an implementation of {@link FileData} which is actually an abstraction of Binary data
	 * @param spec The specification of the Data typically in the form &lt;locatortype&gt;:&lt;path&gt;<br>
	 * e.g classpath:org/castafiore/resource/image.png 
	 * @param width The width to resize the resource if it is an image
	 * @return The Binary representation of the resource
	 * @throws Exception Thrown whenever resolving fails
	 */
	public FileData getResource(String spec, String width)throws Exception;

}
