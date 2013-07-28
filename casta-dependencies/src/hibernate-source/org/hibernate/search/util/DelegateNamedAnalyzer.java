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
 // $Id: DelegateNamedAnalyzer.java 14954 2008-07-17 20:43:10Z sannegrinovero $
package org.hibernate.search.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * delegate to a named analyzer
 * delegated Analyzers are lazily configured
 *
 * @author Emmanuel Bernard
 */
public class DelegateNamedAnalyzer extends Analyzer {
	private String name;
	private Analyzer delegate;

	public DelegateNamedAnalyzer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDelegate(Analyzer delegate) {
		this.delegate = delegate;
		this.name = null; //unique init
	}

	public TokenStream tokenStream(String fieldName, Reader reader) {
		return delegate.tokenStream( fieldName, reader );
	}

	@Override
	public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
		return delegate.reusableTokenStream( fieldName, reader );
	}

	@Override
	public int getPositionIncrementGap(String fieldName) {
		return delegate.getPositionIncrementGap( fieldName );
	}
}
