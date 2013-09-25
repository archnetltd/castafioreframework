/*
 * Copyright (C) 2007-2008 Castafiore
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
 * Simple implementation of {@link KeyValuePair}
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */ 
public class SimpleKeyValuePair implements KeyValuePair,
		Comparable<SimpleKeyValuePair> {

	private static final long serialVersionUID = -550647585055640132L;

	/**
	 * The key
	 */
	private String key;

	/**
	 * The value
	 */
	private String value;

	/**
	 * Empty constructor
	 */
	public SimpleKeyValuePair() {

	}

	/**
	 * Constructor passing key and value
	 * 
	 * @param key
	 *            - The key
	 * @param value
	 *            - The value
	 */
	public SimpleKeyValuePair(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * Returns the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key
	 * 
	 * @param key
	 *            - The key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Returns the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value
	 * 
	 * @param value
	 *            - The value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * String representation of this {@link KeyValuePair}<br>
	 * It returns the value
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * Equality is calculated exclusively on the key
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleKeyValuePair) {
			return ((SimpleKeyValuePair) obj).getKey().equals(key);
		}
		return super.equals(obj);
	}

	/**
	 * Comparison is made exclusively using the key
	 */
	public int compareTo(SimpleKeyValuePair o) {
		return this.key.compareToIgnoreCase(o.getKey());
	}
}
