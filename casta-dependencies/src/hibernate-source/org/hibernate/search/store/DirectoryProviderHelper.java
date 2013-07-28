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
 // $Id: DirectoryProviderHelper.java 15547 2008-11-11 12:57:47Z hardy.ferentschik $
package org.hibernate.search.store;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.search.SearchException;
import org.hibernate.search.util.FileHelper;
import org.hibernate.search.util.LoggerFactory;

/**
 * @author Emmanuel Bernard
 * @author Sanne Grinovero
 */
public class DirectoryProviderHelper {
	
	private static final Logger log = LoggerFactory.make();
	private static final String ROOTINDEX_PROP_NAME = "sourceBase";
	private static final String RELATIVEINDEX_PROP_NAME = "source";
	public static final String COPYBUFFERSIZE_PROP_NAME = "buffer_size_on_copy";

	/**
	 * Build a directory name out of a root and relative path, guessing the significant part
	 * and checking for the file availability
	 * @param directoryProviderName
	 * @param properties
	 * @param needWritePermissions when true the directory will be tested for read-write permissions. 
	 * @return The file representing the source directory
	 */
	public static File getSourceDirectory( String directoryProviderName, Properties properties, boolean needWritePermissions ) {
		String root = properties.getProperty( ROOTINDEX_PROP_NAME );
		String relative = properties.getProperty( RELATIVEINDEX_PROP_NAME );
		File sourceDirectory;
		if ( log.isTraceEnabled() ) {
			log.trace(
					"Guess source directory from {} {} and {} {}", new Object[] {
						ROOTINDEX_PROP_NAME,
						( root != null ? root : "<null>" ),
						RELATIVEINDEX_PROP_NAME,
					(relative != null ? relative : "<null>") }
			);
		}
		if ( relative == null ) relative = directoryProviderName;
		if ( StringHelper.isEmpty( root ) ) {
			log.debug( "No root directory, go with relative " + relative );
			sourceDirectory = new File( relative );
			if ( ! sourceDirectory.isDirectory() ) { // this also tests for existence
				throw new SearchException( "Unable to read source directory: " + relative );
			}
			//else keep source as it
		}
		else {
			File rootDir = new File( root );
			makeSanityCheckedDirectory( rootDir, directoryProviderName, needWritePermissions );
			sourceDirectory = new File( root, relative );
			makeSanityCheckedDirectory( sourceDirectory, directoryProviderName, needWritePermissions );
			log.debug( "Got directory from root + relative" );
		}
		return sourceDirectory;
	}
	
	/**
	 * Creates an FSDirectory in provided directory if not already existing.
	 * @param indexDir The directory where to write a new index
	 * @return the created FSDirectory
	 * @throws IOException
	 */
	public static FSDirectory createFSIndex(File indexDir) throws IOException {
		FSDirectory fsDirectory = FSDirectory.getDirectory( indexDir );
		if ( ! IndexReader.indexExists( fsDirectory ) ) {
			log.debug( "Initialize index: '{}'", indexDir.getAbsolutePath() );
			IndexWriter.MaxFieldLength fieldLength = new IndexWriter.MaxFieldLength( IndexWriter.DEFAULT_MAX_FIELD_LENGTH );
			IndexWriter iw = new IndexWriter( fsDirectory, new StandardAnalyzer(), true, fieldLength );
			iw.close();
		}
		return fsDirectory;
	}

	/**
	 * Verify the index directory exists and is writable,
	 * or creates it if not existing.
	 * @param annotatedIndexName The index name declared on the @Indexed annotation
	 * @param properties The properties may override the indexname.
	 * @param verifyIsWritable Verify the directory is writable
	 * @return the File representing the Index Directory
	 * @throws SearchException
	 */
	public static File getVerifiedIndexDir(String annotatedIndexName, Properties properties, boolean verifyIsWritable) {
		String indexBase = properties.getProperty( "indexBase", "." );
		String indexName = properties.getProperty( "indexName", annotatedIndexName );
		File baseIndexDir = new File( indexBase );
		makeSanityCheckedDirectory( baseIndexDir, indexName, verifyIsWritable );
		File indexDir = new File( baseIndexDir, indexName );
		makeSanityCheckedDirectory( indexDir, indexName, verifyIsWritable );
		return indexDir;
	}
	
	/**
	 * @param directory The directory to create or verify
	 * @param indexName To label exceptions
	 * @param verifyIsWritable Verify the directory is writable
	 * @throws SearchException
	 */
	private static void makeSanityCheckedDirectory(File directory, String indexName, boolean verifyIsWritable) {
		if ( ! directory.exists() ) {
			log.warn( "Index directory not found, creating: '" + directory.getAbsolutePath() + "'" );
			//if not existing, create the full path
			if ( ! directory.mkdirs() ) {
				throw new SearchException( "Unable to create index directory: "
						+ directory.getAbsolutePath() + " for index "
						+ indexName );
			}
		}
		else {
			// else check it is not a file
			if ( ! directory.isDirectory() ) {
				throw new SearchException( "Unable to initialize index: "
						+ indexName + ": "
						+ directory.getAbsolutePath() + " is a file." );
			}
		}
		// and ensure it's writable
		if ( verifyIsWritable && ( ! directory.canWrite() ) ) {
			throw new SearchException( "Cannot write into index directory: "
					+ directory.getAbsolutePath() + " for index "
					+ indexName );
		}
	}

	static long getRefreshPeriod(Properties properties, String directoryProviderName) {
		String refreshPeriod = properties.getProperty( "refresh", "3600" );
		long period;
		try {
			period = Long.parseLong( refreshPeriod );
		} catch (NumberFormatException nfe) {
			throw new SearchException( "Unable to initialize index: " + directoryProviderName +"; refresh period is not numeric.", nfe );
		}
		log.debug( "Refresh period: {} seconds", period );
		return period * 1000; //per second
	}

	/**
	 * Users may configure the number of MB to use as
	 * "chunk size" for large file copy operations performed
	 * by DirectoryProviders.
	 * @param directoryProviderName
	 * @param properties
	 * @return the number of Bytes to use as "chunk size" in file copy operations.
	 */
	public static long getCopyBufferSize(String directoryProviderName, Properties properties) {
		String value = properties.getProperty( COPYBUFFERSIZE_PROP_NAME );
		long size = FileHelper.DEFAULT_COPY_BUFFER_SIZE;
		if ( value != null ) {
			try {
				size = Long.parseLong( value ) * 1024 * 1024; //from MB to B.
			} catch (NumberFormatException nfe) {
				throw new SearchException( "Unable to initialize index " +
						directoryProviderName +"; "+ COPYBUFFERSIZE_PROP_NAME + " is not numeric.", nfe );
			}
			if ( size <= 0 ) {
				throw new SearchException( "Unable to initialize index " +
						directoryProviderName +"; "+ COPYBUFFERSIZE_PROP_NAME + " needs to be greater than zero.");
			}
		}
		return size;
	}
	
}
