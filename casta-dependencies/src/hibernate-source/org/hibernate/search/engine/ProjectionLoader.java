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
 //$Id: ProjectionLoader.java 15592 2008-11-19 14:17:48Z hardy.ferentschik $
package org.hibernate.search.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

/**
 * Implementation of the <code>Loader</code> interface used for loading entities which are projected via
 * {@link org.hibernate.search.ProjectionConstants#THIS}.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public class ProjectionLoader implements Loader {
	private SearchFactoryImplementor searchFactoryImplementor;
	private Session session;
	private Loader objectLoader;
	private Boolean projectThis;
	private ResultTransformer transformer;
	private String[] aliases;
	private Set<Class<?>> entityTypes;

	public void init(Session session, SearchFactoryImplementor searchFactoryImplementor) {
		this.session = session;
		this.searchFactoryImplementor = searchFactoryImplementor;
	}

	public void init(Session session, SearchFactoryImplementor searchFactoryImplementor, ResultTransformer transformer, String[] aliases) {
		init( session, searchFactoryImplementor );
		this.transformer = transformer;
		this.aliases = aliases;
	}

	public void setEntityTypes(Set<Class<?>> entityTypes) {
		this.entityTypes = entityTypes;
	}

	public Object load(EntityInfo entityInfo) {
		initThisProjectionFlag( entityInfo );
		if ( projectThis ) {
			for ( int index : entityInfo.indexesOfThis ) {
				entityInfo.projection[index] = objectLoader.load( entityInfo );
			}
		}
		if ( transformer != null ) {
			return transformer.transformTuple( entityInfo.projection, aliases );
		}
		else {
			return entityInfo.projection;
		}
	}

	private void initThisProjectionFlag(EntityInfo entityInfo) {
		if ( projectThis == null ) {
			projectThis = entityInfo.indexesOfThis.size() != 0;
			if ( projectThis ) {
				MultiClassesQueryLoader loader = new MultiClassesQueryLoader();
				loader.init( session, searchFactoryImplementor );
				loader.setEntityTypes( entityTypes );
				objectLoader = loader;
			}
		}
	}

	public List load(EntityInfo... entityInfos) {
		List results = new ArrayList( entityInfos.length );
		if ( entityInfos.length == 0 ) {
			return results;
		}

		initThisProjectionFlag( entityInfos[0] );
		if ( projectThis ) {
			objectLoader.load( entityInfos ); // load by batch
			for ( EntityInfo entityInfo : entityInfos ) {
				for ( int index : entityInfo.indexesOfThis ) {
					// set one by one to avoid loosing null objects (skipped in the objectLoader.load( EntityInfo[] ))
					entityInfo.projection[index] = objectLoader.load( entityInfo );
				}
			}
		}
		for ( EntityInfo entityInfo : entityInfos ) {
			if ( transformer != null ) {
				results.add( transformer.transformTuple( entityInfo.projection, aliases ) );
			}
			else {
				results.add( entityInfo.projection );
			}
		}

		return results;
	}
}
