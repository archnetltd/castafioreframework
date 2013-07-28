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
 //$Id: LuceneWork.java 15224 2008-09-29 15:35:13Z sannegrinovero $
package org.hibernate.search.backend;

import java.io.Serializable;

import org.apache.lucene.document.Document;

/**
 * Represent a Serializable Lucene unit work
 *
 * WARNING: This class aims to be serializable and passed in an asynchronous way across VMs
 *          any non backward compatible serialization change should be done with great care
 *          and publically announced. Specifically, new versions of Hibernate Search should be
 *          able to handle changes produced by older versions of Hibernate Search if reasonably possible.
 *          That is why each subclass susceptible to be pass along have a magic serialization number.
 *          NOTE: we are relying on Lucene's Document to play nice unfortunately
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Sanne Grinovero
 */
public abstract class LuceneWork implements Serializable {

	private final Document document;
	private final Class entityClass;
	private final Serializable id;
	
	/**
	 * Flag indicating if this lucene work has to be indexed in batch mode.
	 */
	private final boolean batch;
	private final String idInString;

	public LuceneWork(Serializable id, String idInString, Class entity) {
		this( id, idInString, entity, null );
	}

	public LuceneWork(Serializable id, String idInString, Class entity, Document document) {
		this( id, idInString, entity, document, false );
	}

	public LuceneWork(Serializable id, String idInString, Class entity, Document document, boolean batch) {
		this.id = id;
		this.idInString = idInString;
		this.entityClass = entity;
		this.document = document;
		this.batch = batch;
	}

	public boolean isBatch() {
		return batch;
	}

	public Document getDocument() {
		return document;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public Serializable getId() {
		return id;
	}

	public String getIdInString() {
		return idInString;
	}
	
	public abstract <T> T getWorkDelegate(WorkVisitor<T> visitor);

}
