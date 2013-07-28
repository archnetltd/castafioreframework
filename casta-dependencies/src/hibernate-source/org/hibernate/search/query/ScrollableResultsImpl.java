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
 //$Id: ScrollableResultsImpl.java 15628 2008-11-29 14:06:12Z sannegrinovero $
package org.hibernate.search.query;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.lucene.search.IndexSearcher;
import org.slf4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.search.SearchException;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.engine.DocumentExtractor;
import org.hibernate.search.engine.EntityInfo;
import org.hibernate.search.engine.Loader;
import org.hibernate.search.util.LoggerFactory;
import org.hibernate.type.Type;

/**
 * Implements scrollable and paginated resultsets.
 * Contrary to Query#iterate() or Query#list(), this implementation is
 * exposed to returned null objects (if the index is out of date).
 * <p/>
 * <p/>
 * The following methods that change the value of 'current' will check
 * and set its value to either 'afterLast' or 'beforeFirst' depending
 * on direction. This is to prevent rogue values from setting it outside
 * the boundaries of the results.
 * <ul>
 * <li>next()</li>
 * <li>previous()</li>
 * <li>scroll(i)</li>
 * <li>last()</li>
 * <li>first()</li>
 * </ul>
 * 
 * @see org.hibernate.Query
 *
 * @author Emmanuel Bernard
 * @author John Griffin
 */
public class ScrollableResultsImpl implements ScrollableResults {
	private static final Logger log = LoggerFactory.make();
	private final SearchFactory searchFactory;
	private final IndexSearcher searcher;
	private final int first;
	private final int max;
	private final int fetchSize;
	private int current;
	private final EntityInfo[] entityInfos;
	private final Loader loader;
	private final DocumentExtractor documentExtractor;
	private final Map<EntityInfo, Object[]> resultContext;

	public ScrollableResultsImpl( IndexSearcher searcher, int first, int max, int fetchSize, DocumentExtractor extractor,
			Loader loader, SearchFactory searchFactory
	) {
		this.searchFactory = searchFactory;
		this.searcher = searcher;
		this.first = first;
		this.max = max;
		this.current = first;
		this.loader = loader;
		this.documentExtractor = extractor;
		int size = max - first + 1 > 0 ? max - first + 1 : 0;
		this.entityInfos = new EntityInfo[size];
		this.resultContext = new HashMap<EntityInfo, Object[]>( size );
		this.fetchSize = fetchSize;
	}

	// The 'cache' is a sliding window of size fetchSize that
	// moves back and forth over entityInfos as directed loading
	// values as necessary.
	private EntityInfo loadCache(int windowStart) {
		int windowStop;

		EntityInfo info = entityInfos[windowStart - first];
		if ( info != null ) {
			//data has already been loaded
			return info;
		}

		if ( windowStart + fetchSize > max ) {
			windowStop = max;
		}
		else {
			windowStop = windowStart + fetchSize - 1;
		}

		List<EntityInfo> entityInfosLoaded = new ArrayList<EntityInfo>( windowStop - windowStart + 1 );
		for (int x = windowStart; x <= windowStop; x++) {
			try {
				if ( entityInfos[x - first] == null ) {
					//FIXME should check that clazz match classes but this complicates a lot the firstResult/maxResult
					entityInfos[x - first] = documentExtractor.extract( x );
					entityInfosLoaded.add( entityInfos[x - first] );
				}
			}
			catch (IOException e) {
				throw new HibernateException( "Unable to read Lucene topDocs[" + x + "]", e );
			}

		}
		//preload efficiently first
		loader.load( entityInfosLoaded.toArray( new EntityInfo[entityInfosLoaded.size()] ) );
		//load one by one to inject null results if needed
		for (EntityInfo slidingInfo : entityInfosLoaded) {
			if ( !resultContext.containsKey( slidingInfo ) ) {
				Object loaded = loader.load( slidingInfo );
				if ( !loaded.getClass().isArray() ) loaded = new Object[] { loaded };
				resultContext.put( slidingInfo, (Object[]) loaded );
			}
		}
		return entityInfos[windowStart - first];
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean next() {
		//	Increases cursor pointer by one. If this places it >
		//	max + 1 (afterLast) then set it to afterLast and return
		//	false.
		if ( ++current > max ) {
			afterLast();
			return false;
		}
		return true;
	}

	public boolean previous() {
		//	Decreases cursor pointer by one. If this places it <
		//	first - 1 (beforeFirst) then set it to beforeFirst and
		//	return false.
		if ( --current < first ) {
			beforeFirst();
			return false;
		}
		return true;
	}

	public boolean scroll(int i) {
		//  Since we have to take into account that we can scroll any
		//  amount positive or negative, we perform the same tests that
		//  we performed in next() and previous().
		current = current + i;
		if ( current > max ) {
			afterLast();
			return false;
		}
		else if ( current < first ) {
			beforeFirst();
			return false;
		}
		else {
			return true;
		}
	}

	public boolean last() {
		current = max;
		if ( current < first ) {
			beforeFirst();
			return false;
		}
		return max >= first;
	}

	public boolean first() {
		current = first;
		if ( current > max ) {
			afterLast();
			return false;
		}
		return max >= first;
	}

	public void beforeFirst() {
		current = first - 1;
	}

	public void afterLast() {
		current = max + 1;
	}

	public boolean isFirst() {
		return current == first;
	}

	public boolean isLast() {
		return current == max;
	}

	public void close() {
		try {
			searchFactory.getReaderProvider().closeReader( searcher.getIndexReader() );
		}
		catch (SearchException e) {
			log.warn( "Unable to properly close searcher in ScrollableResults", e );
		}
	}

	public Object[] get() throws HibernateException {
		// don't throw an exception here just
		// return 'null' this is similar to the
		// RowSet spec in JDBC. It returns false
		// (or 0 I can't remember) but we can't
		// do that since we have to make up for
		// an Object[]. J.G
		if ( current < first || current > max ) return null;
		loadCache( current );
		return resultContext.get( entityInfos[current - first] );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Object get(int i) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Type getType(int i) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Integer getInteger(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Long getLong(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Float getFloat(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Boolean getBoolean(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Double getDouble(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Short getShort(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Byte getByte(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Character getCharacter(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public byte[] getBinary(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public String getText(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Blob getBlob(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Clob getClob(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public String getString(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public BigDecimal getBigDecimal(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public BigInteger getBigInteger(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Date getDate(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Locale getLocale(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public Calendar getCalendar(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	/**
	 * This method is not supported on Lucene based queries
	 * @throws UnsupportedOperationException always thrown
	 */
	public TimeZone getTimeZone(int col) {
		throw new UnsupportedOperationException( "Lucene does not work on columns" );
	}

	public int getRowNumber() {
		if ( max < first ) return -1;
		return current - first;
	}

	public boolean setRowNumber(int rowNumber) {
		if ( rowNumber >= 0 ) {
			current = first + rowNumber;
		}
		else {
			current = max + rowNumber + 1; //max row start at -1
		}
		return current >= first && current <= max;
	}
}
