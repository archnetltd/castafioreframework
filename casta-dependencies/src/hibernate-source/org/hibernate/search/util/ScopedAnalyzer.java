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
 // $Id: ScopedAnalyzer.java 15637 2008-12-02 14:28:28Z hardy.ferentschik $
package org.hibernate.search.util;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * A <code>ScopedAnalyzer</code> is a wrapper class containing all analyzers for a given class.
 * <code>ScopedAnalyzer</code> behaves similar to <code>PerFieldAnalyzerWrapper</code> by delegating requests for
 * <code>TokenStream</code>s to the underlying <code>Analyzer</code> depending on the requested field name.
 * 
 * @author Emmanuel Bernard
 */
public class ScopedAnalyzer extends Analyzer {
	private Analyzer globalAnalyzer;
	private Map<String, Analyzer> scopedAnalyzers = new HashMap<String, Analyzer>();

	public ScopedAnalyzer() {
	}

	private ScopedAnalyzer( Analyzer globalAnalyzer, Map<String, Analyzer> scopedAnalyzers) {
		this.globalAnalyzer = globalAnalyzer;
		for ( Map.Entry<String, Analyzer> entry : scopedAnalyzers.entrySet() ) {
			addScopedAnalyzer( entry.getKey(), entry.getValue() );
		}
	}

	public void setGlobalAnalyzer( Analyzer globalAnalyzer ) {
		this.globalAnalyzer = globalAnalyzer;
	}

	public void addScopedAnalyzer( String scope, Analyzer scopedAnalyzer ) {
		scopedAnalyzers.put(scope, scopedAnalyzer);
	}

	public TokenStream tokenStream( String fieldName, Reader reader ) {
		return getAnalyzer(fieldName).tokenStream(fieldName, reader);
	}

	public int getPositionIncrementGap( String fieldName ) {
		return getAnalyzer(fieldName).getPositionIncrementGap(fieldName);
	}

	private Analyzer getAnalyzer( String fieldName ) {
		Analyzer analyzer = scopedAnalyzers.get(fieldName);
		if ( analyzer == null ) {
			analyzer = globalAnalyzer;
		}
		return analyzer;
	}

	public ScopedAnalyzer clone() {
		ScopedAnalyzer clone = new ScopedAnalyzer( globalAnalyzer, scopedAnalyzers );
		return clone;
	}
}
