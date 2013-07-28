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
 // $Id: FullTextEntityManagerImpl.java 15604 2008-11-21 13:58:05Z hardy.ferentschik $
package org.hibernate.search.jpa.impl;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.EntityTransaction;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.SearchException;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.Session;

/**
 * @author Emmanuel Bernard
 */
public class FullTextEntityManagerImpl implements FullTextEntityManager, Serializable {
	private final EntityManager em;
	private FullTextSession ftSession;

	public FullTextEntityManagerImpl(EntityManager em) {
		this.em = em;
	}

	private FullTextSession getFullTextSession() {
		if ( ftSession == null ) {
			Object delegate = em.getDelegate();
			if ( delegate == null ) {
				throw new SearchException(
						"Trying to use Hibernate Search without an Hibernate EntityManager (no delegate)"
				);
			}
			else if ( Session.class.isAssignableFrom( delegate.getClass() ) ) {
				ftSession = Search.getFullTextSession( ( Session ) delegate );
			}
			else if ( EntityManager.class.isAssignableFrom( delegate.getClass() ) ) {
				//Some app servers wrap the EM twice
				delegate = ( ( EntityManager ) delegate ).getDelegate();
				if ( delegate == null ) {
					throw new SearchException(
							"Trying to use Hibernate Search without an Hibernate EntityManager (no delegate)"
					);
				}
				else if ( Session.class.isAssignableFrom( delegate.getClass() ) ) {
					ftSession = Search.getFullTextSession( ( Session ) delegate );
				}
				else {
					throw new SearchException(
							"Trying to use Hibernate Search without an Hibernate EntityManager: " + delegate.getClass()
					);
				}
			}
			else {
				throw new SearchException(
						"Trying to use Hibernate Search without an Hibernate EntityManager: " + delegate.getClass()
				);
			}
		}
		return ftSession;
	}

	public FullTextQuery createFullTextQuery(org.apache.lucene.search.Query luceneQuery, Class... entities) {
		FullTextSession ftSession = getFullTextSession();
		return new FullTextQueryImpl( ftSession.createFullTextQuery( luceneQuery, entities ), ftSession );
	}

	public <T> void index(T entity) {
		getFullTextSession().index( entity );
	}

	public SearchFactory getSearchFactory() {
		return getFullTextSession().getSearchFactory();
	}

	public <T> void purge(Class<T> entityType, Serializable id) {
		getFullTextSession().purge( entityType, id );
	}

	public <T> void purgeAll(Class<T> entityType) {
		getFullTextSession().purgeAll( entityType );
	}

	public void flushToIndexes() {
		getFullTextSession().flushToIndexes();
	}

	public void persist(Object entity) {
		em.persist( entity );
	}

	public <T> T merge(T entity) {
		return em.merge( entity );
	}

	public void remove(Object entity) {
		em.remove( entity );
	}

	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return em.find( entityClass, primaryKey );
	}

	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		return em.getReference( entityClass, primaryKey );
	}

	public void flush() {
		em.flush();
	}

	public void setFlushMode(FlushModeType flushMode) {
		em.setFlushMode( flushMode );
	}

	public FlushModeType getFlushMode() {
		return em.getFlushMode();
	}

	public void lock(Object entity, LockModeType lockMode) {
		em.lock( entity, lockMode );
	}

	public void refresh(Object entity) {
		em.refresh( entity );
	}

	public void clear() {
		em.clear();
	}

	public boolean contains(Object entity) {
		return em.contains( entity );
	}

	public Query createQuery(String ejbqlString) {
		return em.createQuery( ejbqlString );
	}

	public Query createNamedQuery(String name) {
		return em.createNamedQuery( name );
	}

	public Query createNativeQuery(String sqlString) {
		return em.createNativeQuery( sqlString );
	}

	public Query createNativeQuery(String sqlString, Class resultClass) {
		return em.createNativeQuery( sqlString, resultClass );
	}

	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return em.createNativeQuery( sqlString, resultSetMapping );
	}

	public void joinTransaction() {
		em.joinTransaction();
	}

	public Object getDelegate() {
		return em.getDelegate();
	}

	public void close() {
		em.close();
	}

	public boolean isOpen() {
		return em.isOpen();
	}

	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}
}
