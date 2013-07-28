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
 // $Id: ProjectionConstants.java 15606 2008-11-24 11:39:52Z hardy.ferentschik $
package org.hibernate.search;

/**
 * Defined projection constants.
 *
 * @author Emmanuel Bernard
 */
public interface ProjectionConstants {
	/**
	 * Represents the Hibernate entity returned in a search.
	 */
	public String THIS = "__HSearch_This";

	/**
	 * The Lucene document returned by a search.
	 */
	public String DOCUMENT = "__HSearch_Document";

	/**
	 * The legacy document's score from a search.
	 */
	public String SCORE = "__HSearch_Score";

	/**
	 * The boost value of the Lucene document.
	 *
	 * @deprecated always return 1
	 */
	public String BOOST = "__HSearch_Boost";

	/**
	 * Object id property
	 */
	public String ID = "__HSearch_id";

	/**
	 * Lucene Document id
	 * Experimental: If you use this feature, please speak up in the forum
	 * <p/>
	 * Expert: Lucene document id can change overtime between 2 different IndexReader opening.
	 */
	public String DOCUMENT_ID = "__HSearch_DocumentId";
	
	/**
	 * Lucene {@link org.apache.lucene.search.Explanation} object describing the score computation for
	 * the matching object/document
	 * This feature is relatively expensive, do not use unless you return a limited
	 * amount of objects (using pagination)
	 * To retrieve explanation of a single result, consider retrieving {@link #DOCUMENT_ID}
	 * and using fullTextQuery.explain(int)
	 */
	public String EXPLANATION = "__HSearch_Explanation";

	/**
	 * Represents the Hibernate entity class returned in a search. In contrast to the other constants this constant
	 * represents an actual field value of the underlying Lucene document and hence can directly be used in queries.
	 */
	public String OBJECT_CLASS = "_hibernate_class";
}
