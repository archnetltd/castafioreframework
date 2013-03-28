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
 package org.castafiore;

 /**
  * Thrown whenever a component is added to a layout container passing an invalid layout data
  * @author Kureem Rossaye
  *
  */
public class InvalidLayoutDataException extends RuntimeException {

	public InvalidLayoutDataException() {
		super();
	}

	public InvalidLayoutDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLayoutDataException(String message) {
		super(message);
	}

	public InvalidLayoutDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
