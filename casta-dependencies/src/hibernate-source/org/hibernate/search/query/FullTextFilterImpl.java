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
 // $Id: FullTextFilterImpl.java 15541 2008-11-10 20:14:05Z hardy.ferentschik $
package org.hibernate.search.query;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.search.FullTextFilter;

/**
 * @author Emmanuel Bernard
 */
public class FullTextFilterImpl implements FullTextFilter {
	private final Map<String, Object> parameters = new HashMap<String, Object>();
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public FullTextFilter setParameter(String name, Object value) {
		parameters.put( name, value );
		return this;
	}

	public Object getParameter(String name) {
		return parameters.get( name );
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}
}
