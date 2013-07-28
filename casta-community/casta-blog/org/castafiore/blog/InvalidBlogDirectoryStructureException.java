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
 package org.castafiore.blog;

/**
 * This exception is thrown whenever there is a invalid configuration inside the blog directory structure
 * @author Kuree Rossaye
 *
 */
public class InvalidBlogDirectoryStructureException extends RuntimeException {

	public InvalidBlogDirectoryStructureException() {
		super();
		
	}

	public InvalidBlogDirectoryStructureException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InvalidBlogDirectoryStructureException(String message) {
		super(message);
		
	}

	public InvalidBlogDirectoryStructureException(Throwable cause) {
		super(cause);
		
	}

	
}
