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
 // $Id: UriBridge.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.bridge.builtin;

import java.net.URI;
import java.net.URISyntaxException;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.search.SearchException;
import org.hibernate.search.bridge.TwoWayStringBridge;

/**
 * Bridge for <code>URI</code>
 *
 * @author Emmanuel Bernard
 */
public class UriBridge implements TwoWayStringBridge {
	public Object stringToObject(String stringValue) {
		if ( StringHelper.isEmpty( stringValue ) ) {
			return null;
		}
		else {
			try {
				return new URI( stringValue );
			}
			catch (URISyntaxException e) {
				throw new SearchException( "Unable to build URI: " + stringValue, e );
			}
		}
	}

	public String objectToString(Object object) {
		return object == null ?
				null :
				object.toString();
	}
}