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
 // $Id: FullTextQuery.java 14940 2008-07-17 00:15:47Z epbernard $
package org.hibernate.search.jpa;

import javax.persistence.Query;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Explanation;
import org.hibernate.Criteria;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.search.ProjectionConstants;
import org.hibernate.search.FullTextFilter;

/**
 * The base interface for lucene powered searches.
 * This extends the JPA Query interface
 *
 * @author Hardy Ferentschik
 * @author Emmanuel Bernard
 */
//TODO return FullTextQuery rather than Query in useful chain methods
public interface FullTextQuery extends Query, ProjectionConstants {

	/**
	 * Allows to let lucene sort the results. This is useful when you have
	 * additional sort requirements on top of the default lucene ranking.
	 * Without lucene sorting you would have to retrieve the full result set and
	 * order the hibernate objects.
	 *
	 * @param sort The lucene sort object.
	 * @return this for method chaining
	 */
	FullTextQuery setSort(Sort sort);

	/**
	 * Allows to use lucene filters.
	 * Semi-deprecated? a preferred way is to use the @FullTextFilterDef approach
	 *
	 * @param filter The lucene filter.
	 * @return this for method chaining
	 */
	FullTextQuery setFilter(Filter filter);

	/**
	 * Returns the number of hits for this search
	 *
	 * Caution:
	 * The number of results might be slightly different from
	 * <code>list().size()</code> because list() if the index is
	 * not in sync with the database at the time of query.
	 */
	int getResultSize();

	/**
	 * Defines the Database Query used to load the Lucene results.
	 * Useful to load a given object graph by refining the fetch modes
	 *
	 * No projection (criteria.setProjection() ) allowed, the root entity must be the only returned type
	 * No where restriction can be defined either.
	 *
	 */
	FullTextQuery setCriteriaQuery(Criteria criteria);

	/**
	 * Defines the Lucene field names projected and returned in a query result
	 * Each field is converted back to it's object representation, an Object[] being returned for each "row"
	 * (similar to an HQL or a Criteria API projection).
	 *
	 * A projectable field must be stored in the Lucene index and use a {@link org.hibernate.search.bridge.TwoWayFieldBridge}
	 * Unless notified in their JavaDoc, all built-in bridges are two-way. All @DocumentId fields are projectable by design.
	 *
	 * If the projected field is not a projectable field, null is returned in the object[]
	 *
	 */
	FullTextQuery setProjection(String... fields);

	/**
	 * Enable a given filter by its name. Returns a FullTextFilter object that allows filter parameter injection
	 */
	FullTextFilter enableFullTextFilter(String name);

	/**
	 * Disable a given filter by its name
	 */
	void disableFullTextFilter(String name);

	/**
	 *
	 * defines a result transformer used during projection
	 *
	 */
	FullTextQuery setResultTransformer(ResultTransformer transformer);

	/**
	 * Return the Lucene {@link org.apache.lucene.search.Explanation}
	 * object describing the score computation for the matching object/document
	 * in the current query
	 *
	 * @param documentId Lucene Document id to be explain. This is NOT the object id
	 * @return Lucene Explanation
	 */
	Explanation explain(int documentId);
}
