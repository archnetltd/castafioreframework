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
 //$Id: QueryLoader.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.engine;

import java.util.List;
import java.util.Collections;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.common.AssertionFailure;

/**
 * @author Emmanuel Bernard
 */
public class QueryLoader implements Loader {

	private Session session;
	private Class entityType;
	private SearchFactoryImplementor searchFactoryImplementor;
	private Criteria criteria;
	private boolean isExplicitCriteria;

	public void init(Session session, SearchFactoryImplementor searchFactoryImplementor) {
		this.session = session;
		this.searchFactoryImplementor = searchFactoryImplementor;
	}

	public void setEntityType(Class entityType) {
		this.entityType = entityType;
	}

	public Object load(EntityInfo entityInfo) {
		//if explicit criteria, make sure to use it to load the objects
		if ( isExplicitCriteria ) load( new EntityInfo[] { entityInfo } );
		return ObjectLoaderHelper.load( entityInfo, session );
	}

	public List load(EntityInfo... entityInfos) {
		if ( entityInfos.length == 0 ) return Collections.EMPTY_LIST;
		if ( entityType == null ) throw new AssertionFailure( "EntityType not defined" );
		if ( criteria == null ) criteria = session.createCriteria( entityType );

		ObjectLoaderHelper.initializeObjects( entityInfos, criteria, entityType, searchFactoryImplementor );
		return ObjectLoaderHelper.returnAlreadyLoadedObjectsInCorrectOrder( entityInfos, session );
	}

	public void setCriteria(Criteria criteria) {
		isExplicitCriteria = criteria != null;
		this.criteria = criteria;
	}
}
