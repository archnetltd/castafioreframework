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
 // $Id: OptimizeLuceneWork.java 15224 2008-09-29 15:35:13Z sannegrinovero $
package org.hibernate.search.backend;

import java.io.Serializable;

/**
 * A unit of work triggering an optimize operation.
 * This work does not propagate to a cluster: it should be filtered before being sent to
 * the network.
 *
 * @author Andrew Hahn
 * @author Emmanuel Bernard
 */
public class OptimizeLuceneWork extends LuceneWork implements Serializable {
	
	public OptimizeLuceneWork(Class entity) {
		super( null, null, entity );
	}

	@Override
	public <T> T getWorkDelegate(final WorkVisitor<T> visitor) {
		return visitor.getDelegate( this );
	}
	
}
