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
 //$Id: EntityInfo.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.engine;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Emmanuel Bernard
 */
//TODO Move to egine?
public class EntityInfo {
	
	public final Class clazz;
	public final Serializable id;
	public final Object[] projection;
	public final List<Integer> indexesOfThis = new LinkedList<Integer>();
	
	public EntityInfo(Class clazz, Serializable id, Object[] projection) {
		this.clazz = clazz;
		this.id = id;
		this.projection = projection;
	}
	
}
