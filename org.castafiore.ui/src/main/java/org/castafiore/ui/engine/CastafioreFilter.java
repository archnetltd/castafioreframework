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
package org.castafiore.ui.engine;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface acts as a Filter to be used exclusively in Castafiore
 * framework.
 * 
 * It is configured inside a spring application context.
 * 
 * The order in which Filters are executed are not granteed. But depends on how
 * the spring application context serves the implementations of this interface
 * 
 * @author kureem
 * 
 */
public interface CastafioreFilter extends Serializable {

	/**
	 * executed upon begining of request before application is loaded
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void doStart(HttpServletRequest request) throws Exception;

	/**
	 * executed at the end of the request. After event is executed
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void doEnd(HttpServletRequest request) throws Exception;

	/**
	 * executed whenever any exception is caught
	 * 
	 * @param request
	 * @param e
	 */
	public void onException(HttpServletRequest request, Exception e);

}
