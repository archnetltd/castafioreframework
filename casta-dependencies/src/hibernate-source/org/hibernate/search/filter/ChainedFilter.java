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
 // $Id: ChainedFilter.java 15627 2008-11-29 13:10:14Z sannegrinovero $
package org.hibernate.search.filter;

import java.util.BitSet;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.index.IndexReader;
import org.hibernate.annotations.common.AssertionFailure;

/**
 * <p>A Filter capable of chaining other filters, so that it's
 * possible to apply several filters on a Query.</p>
 * <p>The resulting filter will only enable result Documents
 * if no filter removed it.</p>
 * 
 * @author Emmanuel Bernard
 * @author Sanne Grinovero
 */
public class ChainedFilter extends Filter {
	
	private static final long serialVersionUID = -6153052295766531920L;
	
	private final List<Filter> chainedFilters = new ArrayList<Filter>();

	public void addFilter(Filter filter) {
		this.chainedFilters.add( filter );
	}

	public BitSet bits(IndexReader reader) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		int size = chainedFilters.size();
		if ( size == 0 ) {
			throw new AssertionFailure( "Chainedfilter has no filters to chain for" );
		}
		else if ( size == 1 ) {
			return chainedFilters.get( 0 ).getDocIdSet( reader );
		}
		else {
			List<DocIdSet> subSets = new ArrayList<DocIdSet>( size );
			for ( Filter f : chainedFilters ) {
				subSets.add( f.getDocIdSet( reader ) );
			}
			subSets = FilterOptimizationHelper.mergeByBitAnds( subSets );
			if ( subSets.size() == 1 ) {
				return subSets.get( 0 );
			}
			return new AndDocIdSet( subSets, reader.maxDoc() );
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder( "ChainedFilter [" );
		for (Filter filter : chainedFilters) {
			sb.append( "\n  " ).append( filter.toString() );
		}
		return sb.append("\n]" ).toString();
	}
}
