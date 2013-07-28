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
 //$Id: EnumBridge.java 11625 2007-06-04 16:21:54Z epbernard $
package org.hibernate.search.bridge.builtin;

import org.hibernate.search.bridge.TwoWayStringBridge;
import org.hibernate.annotations.common.util.StringHelper;


/**
 * Map an Enum field
 *
 * @author Sylvain Vieujot
 */
public class EnumBridge implements TwoWayStringBridge {

	private Class<? extends Enum> clazz = null;

    /**
     * @param clazz the class of the enum.
     */
    public EnumBridge(Class<? extends Enum> clazz) {
        this.clazz = clazz;
    }

	public Enum<? extends Enum> stringToObject(String stringValue) {
		if ( StringHelper.isEmpty( stringValue ) ) return null;
		return Enum.valueOf( clazz, stringValue );
	}

	public String objectToString(Object object) {
		Enum e = (Enum) object;
		return e != null ? e.name() : null;
	}
}

