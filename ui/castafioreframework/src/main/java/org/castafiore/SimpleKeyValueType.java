/*
 * Copyright (C) 2007-2013 Castafiore
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
 * Extends the {@link SimpleKeyValuePair} to add the type
 * @author arossaye
 *
 */
public class SimpleKeyValueType extends SimpleKeyValuePair implements
		KeyValueType {

	private Types type;

	/**
	 * Empty constructor
	 */
	public SimpleKeyValueType() {
		super();

	}

	/**
	 * Constructor using the necessary values
	 * @param key - The key
	 * @param value - The value
	 * @param type - The type
	 */
	public SimpleKeyValueType(String key, String value, Types type) {
		super(key, value);
		this.type = type;
	}

	/**
	 * Returns the type
	 */
	@Override
	public Types getType() {
		return type;
	}

	/**
	 * Sets the type
	 * @param type
	 */
	public void setType(Types type) {
		this.type = type;
	}

}
