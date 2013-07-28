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
 //$Id: RAMDirectoryProvider.java 15547 2008-11-11 12:57:47Z hardy.ferentschik $
package org.hibernate.search.store;

import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.RAMDirectory;
import org.hibernate.HibernateException;
import org.hibernate.search.engine.SearchFactoryImplementor;

/**
 * Use a Lucene RAMDirectory
 *
 * @author Emmanuel Bernard
 * @author Sylvain Vieujot
 */
public class RAMDirectoryProvider implements DirectoryProvider<RAMDirectory> {

	private RAMDirectory directory;
	private String indexName;

	public void initialize(String directoryProviderName, Properties properties, SearchFactoryImplementor searchFactoryImplementor) {
		indexName = directoryProviderName;
	}

	public void start() {
		directory = new RAMDirectory();
		try {
			IndexWriter.MaxFieldLength fieldLength = new IndexWriter.MaxFieldLength( IndexWriter.DEFAULT_MAX_FIELD_LENGTH );
			IndexWriter iw = new IndexWriter( directory, new StandardAnalyzer(), true, fieldLength );
			iw.close();
		}
		catch (IOException e) {
			throw new HibernateException( "Unable to initialize index: " + indexName, e );
		}
	}


	public RAMDirectory getDirectory() {
		return directory;
	}

	public void stop() {}

	@Override
	public boolean equals(Object obj) {
		// this code is actually broken since the value change after initialize call
		// but from a practical POV this is fine since we only call this method
		// after initialize call
		if ( obj == this ) return true;
		if ( obj == null || !( obj instanceof RAMDirectoryProvider ) ) return false;
		return indexName.equals( ( (RAMDirectoryProvider) obj ).indexName );
	}

	@Override
	public int hashCode() {
		// this code is actually broken since the value change after initialize call
		// but from a practical POV this is fine since we only call this method
		// after initialize call
		int hash = 7;
		return 29 * hash + indexName.hashCode();
	}

}
