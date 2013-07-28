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
 // $Id: ObjectLoaderHelper.java 15605 2008-11-23 10:48:59Z hardy.ferentschik $
package org.hibernate.search.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.util.LoggerFactory;

/**
 * @author Emmanuel Bernard
 */
public class ObjectLoaderHelper {

	private static final int MAX_IN_CLAUSE = 500;
	private static final Logger log = LoggerFactory.make();

	public static Object load(EntityInfo entityInfo, Session session) {
		//be sure to get an initialized object but save from ONFE and ENFE
		Object maybeProxy = session.load( entityInfo.clazz, entityInfo.id );
		try {
			Hibernate.initialize( maybeProxy );
		}
		catch (RuntimeException e) {
			if ( LoaderHelper.isObjectNotFoundException( e ) ) {
				log.debug( "Object found in Search index but not in database: {} with id {}",
						entityInfo.clazz, entityInfo.id );
				maybeProxy = null;
			}
			else {
				throw e;
			}
		}
		return maybeProxy;
	}

	public static void initializeObjects(EntityInfo[] entityInfos, Criteria criteria, Class<?> entityType,
										 SearchFactoryImplementor searchFactoryImplementor) {
		final int maxResults = entityInfos.length;
		if ( maxResults == 0 ) return;

		DocumentBuilderIndexedEntity<?> builder = searchFactoryImplementor.getDocumentBuilderIndexedEntity( entityType );
		String idName = builder.getIdentifierName();
		int loop = maxResults / MAX_IN_CLAUSE;
		boolean exact = maxResults % MAX_IN_CLAUSE == 0;
		if ( !exact ) loop++;
		Disjunction disjunction = Restrictions.disjunction();
		for (int index = 0; index < loop; index++) {
			int max = index * MAX_IN_CLAUSE + MAX_IN_CLAUSE <= maxResults ?
					index * MAX_IN_CLAUSE + MAX_IN_CLAUSE :
					maxResults;
			List<Serializable> ids = new ArrayList<Serializable>( max - index * MAX_IN_CLAUSE );
			for (int entityInfoIndex = index * MAX_IN_CLAUSE; entityInfoIndex < max; entityInfoIndex++) {
				ids.add( entityInfos[entityInfoIndex].id );
			}
			disjunction.add( Restrictions.in( idName, ids ) );
		}
		criteria.add( disjunction );
		criteria.list(); //load all objects
	}


	public static List returnAlreadyLoadedObjectsInCorrectOrder(EntityInfo[] entityInfos, Session session) {
		//mandatory to keep the same ordering
		List result = new ArrayList( entityInfos.length );
		for (EntityInfo entityInfo : entityInfos) {
			Object element = session.load( entityInfo.clazz, entityInfo.id );
			if ( Hibernate.isInitialized( element ) ) {
				//all existing elements should have been loaded by the query,
				//the other ones are missing ones
				result.add( element );
			}
			else {
				if ( log.isDebugEnabled() ) {
					log.debug( "Object found in Search index but not in database: {} with {}",
						entityInfo.clazz, entityInfo.id );
				}
			}
		}
		return result;
	}
}
