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

package org.castafiore.ui.engine;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */

public class SimpleKeyValuePair implements KeyValuePair,Comparable<SimpleKeyValuePair> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -550647585055640132L;

	private String key;
	
	private String value;
	
	public SimpleKeyValuePair()
	{
		
	}
	
	public SimpleKeyValuePair(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	@Override
	public String toString()
	{
		return value;
	}

	public int compareTo(SimpleKeyValuePair o) {
		return this.key.compareToIgnoreCase(o.getKey());
	}

}
