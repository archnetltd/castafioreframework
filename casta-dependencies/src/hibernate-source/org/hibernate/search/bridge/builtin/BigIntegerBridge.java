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
 //$Id: BigIntegerBridge.java 15574 2008-11-17 11:23:26Z hardy.ferentschik $
package org.hibernate.search.bridge.builtin;

import java.math.BigInteger;

import org.hibernate.util.StringHelper;

/**
 * Map a <code>BigInteger</code> element.
 *
 * @author Emmanuel Bernard
 */
public class BigIntegerBridge extends NumberBridge {
	public Object stringToObject(String stringValue) {
		if ( StringHelper.isEmpty( stringValue ) ) {
			return null;
		}
		return new BigInteger( stringValue );
	}
}
