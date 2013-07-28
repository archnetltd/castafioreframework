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
 //$Id: FSDirectoryProvider.java 15636 2008-12-02 14:21:37Z hardy.ferentschik $
package org.hibernate.search.store;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;

import org.hibernate.search.SearchException;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.util.LoggerFactory;

/**
 * Use a Lucene {@link FSDirectory}. The base directory is represented by the property <i>hibernate.search.default.indexBase</i>
 * or <i>hibernate.search.&lt;index&gt;.indexBase</i>. The former defines the default base directory for all indexes whereas the
 * latter allows to override the base directory on a per index basis.<i> &lt;index&gt;</i> has to be replaced with the fully qualified
 * classname of the indexed class or the value of the <i>index</i> property of the <code>@Indexed</code> annotation.
 * <p>
 * The actual index files are then created in <i>&lt;indexBase&gt;/&lt;index name&gt;</i>, <i>&lt;index name&gt;</i> is
 * per default the name of the indexed entity, or the value of the <i>index</i> property of the <code>@Indexed</code> or can be specified
 * as property in the configuration file using <i>hibernate.search.&lt;index&gt;.indexName</i>.
 * </p>
 *
 * @author Emmanuel Bernard
 * @author Sylvain Vieujot
 * @author Sanne Grinovero
 */
public class FSDirectoryProvider implements DirectoryProvider<FSDirectory> {

	private static final Logger log = LoggerFactory.make();
	
	private FSDirectory directory;
	private String indexName;

	public void initialize(String directoryProviderName, Properties properties, SearchFactoryImplementor searchFactoryImplementor) {
		// on "manual" indexing skip read-write check on index directory
		boolean manual = searchFactoryImplementor.getIndexingStrategy().equals( "manual" );
		File indexDir = DirectoryProviderHelper.getVerifiedIndexDir( directoryProviderName, properties, ! manual );
		try {
			indexName = indexDir.getCanonicalPath();
			//this is cheap so it's not done in start()
			directory = DirectoryProviderHelper.createFSIndex( indexDir );
		}
		catch (IOException e) {
			throw new SearchException( "Unable to initialize index: " + directoryProviderName, e );
		}
	}

	public void start() {
		//all the process is done in initialize
	}

	public void stop() {
		try {
			directory.close();
		}
		catch (Exception e) {
			log.error( "Unable to properly close Lucene directory {}" + directory.getFile(), e );
		}
	}

	public FSDirectory getDirectory() {
		return directory;
	}

	@Override
	public boolean equals(Object obj) {
		// this code is actually broken since the value change after initialize call
		// but from a practical POV this is fine since we only call this method
		// after initialize call
		if ( obj == this ) return true;
		if ( obj == null || !( obj instanceof FSDirectoryProvider ) ) return false;
		return indexName.equals( ( (FSDirectoryProvider) obj ).indexName );
	}

	@Override
	public int hashCode() {
		// this code is actually broken since the value change after initialize call
		// but from a practical POV this is fine since we only call this method
		// after initialize call
		int hash = 11;
		return 37 * hash + indexName.hashCode();
	}
}
