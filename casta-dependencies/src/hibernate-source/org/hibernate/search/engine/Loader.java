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
 //$Id: Loader.java 15553 2008-11-12 15:01:44Z hardy.ferentschik $
package org.hibernate.search.engine;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.search.engine.EntityInfo;

/**
 * Interface defining a set of operations in order to load entities which matched a query. Depending on the type of
 * indexed entities and the type of query different strategies can be used.
 *
 *
 * @author Emmanuel Bernard
 */
public interface Loader {
	void init(Session session, SearchFactoryImplementor searchFactoryImplementor);

	Object load(EntityInfo entityInfo);

	List load(EntityInfo... entityInfos);
}
