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
 //$Id: Workspace.java 15637 2008-12-02 14:28:28Z hardy.ferentschik $
package org.hibernate.search.backend;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;

import org.hibernate.annotations.common.AssertionFailure;
import org.hibernate.search.SearchException;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.engine.DocumentBuilderIndexedEntity;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.optimization.OptimizerStrategy;
import org.hibernate.search.util.LoggerFactory;

/**
 * Lucene workspace for a DirectoryProvider.<p/>
 * <ul>
 * <li>Before using {@link #getIndexWriter} or {@link #getIndexReader} the lock must be acquired,
 * and resources must be closed before releasing the lock.</li>
 * <li>One cannot get an IndexWriter when an IndexReader has been acquired and not closed, and vice-versa.</li>
 * <li>The recommended approach is to execute all the modifications on the <code>IndexReader</code>, and after that on
 * the <code>IndexWriter</code></li>.
 * </ul>
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Sanne Grinovero
 */
//TODO renaming to "DirectoryWorkspace" would be nice.
public class Workspace {

	private static final Logger log = LoggerFactory.make();
	private static final Analyzer SIMPLE_ANALYZER = new SimpleAnalyzer();
	
	// invariant state:

	private final SearchFactoryImplementor searchFactoryImplementor;
	private final DirectoryProvider directoryProvider;
	private final OptimizerStrategy optimizerStrategy;
	private final ReentrantLock lock;
	private final Set<Class<?>> entitiesInDirectory;
	private final LuceneIndexingParameters indexingParams;

	// variable state:
	
	/**
	 * Current open IndexReader, or null when closed. Guarded by synchronization.
	 */
	private IndexReader reader;
	
	/**
	 * Current open IndexWriter, or null when closed. Guarded by synchronization.
	 */
	private IndexWriter writer;
	
	/**
	 * Keeps a count of modification operations done on the index.
	 */
	private final AtomicLong operations = new AtomicLong( 0L );
	
	public Workspace(SearchFactoryImplementor searchFactoryImplementor, DirectoryProvider<?> provider) {
		this.searchFactoryImplementor = searchFactoryImplementor;
		this.directoryProvider = provider;
		this.optimizerStrategy = searchFactoryImplementor.getOptimizerStrategy( directoryProvider );
		this.entitiesInDirectory = searchFactoryImplementor.getClassesInDirectoryProvider( provider );
		this.indexingParams = searchFactoryImplementor.getIndexingParameters( directoryProvider );
		this.lock = searchFactoryImplementor.getDirectoryProviderLock( provider );
	}

	public <T> DocumentBuilderIndexedEntity<T> getDocumentBuilder(Class<T> entity) {
		return searchFactoryImplementor.getDocumentBuilderIndexedEntity( entity );
	}

	public Analyzer getAnalyzer(String name) {
		return searchFactoryImplementor.getAnalyzer( name );
	}

	/**
	 * If optimization has not been forced give a change to configured OptimizerStrategy
	 * to optimize the index.
	 * @throws AssertionFailure if the lock is not owned or if an IndexReader is open.
	 */
	public void optimizerPhase() {
		assertOwnLock();
		// used getAndSet(0) because Workspace is going to be reused by next transaction.
		optimizerStrategy.addTransaction( operations.getAndSet( 0L ) );
		optimizerStrategy.optimize( this );
	}
	
	/**
	 * Used by OptimizeLuceneWork after index optimization to flag that
	 * optimization has been forced.
	 * @see OptimizeLuceneWork
	 * @see SearchFactory#optimize()
	 * @see SearchFactory#optimize(Class)
	 */
	public void optimize() {
		assertOwnLock(); // the DP is not affected, but needs to ensure the optimizerStrategy is accesses in threadsafe way
		optimizerStrategy.optimizationForced();
	}

	/**
	 * Gets an IndexReader to alter the index, opening one if needed.
	 * The caller needs to own the lock relevant to this DirectoryProvider.
	 * @throws AssertionFailure if an IndexWriter is open or if the lock is not owned.
	 * @return a new IndexReader or one already open.
	 * @see #lock()
	 */
	public synchronized IndexReader getIndexReader() {
		assertOwnLock();
		// one cannot access a reader for update while a writer is in use
		if ( writer != null )
			throw new AssertionFailure( "Tries to read for update an index while a writer is in use." );
		if ( reader != null )
			return reader;
		Directory directory = directoryProvider.getDirectory();
		try {
			reader = IndexReader.open( directory, false );
			log.trace( "IndexReader opened" );
		}
		catch ( IOException e ) {
			reader = null;
			throw new SearchException( "Unable to open IndexReader on directory " + directory, e );
		}
		return reader;
	}

	/**
	 * Closes a previously opened IndexReader.
	 * @throws SearchException on IOException during Lucene close operation.
	 * @throws AssertionFailure if the lock is not owned or if there is no IndexReader to close.
	 * @see #getIndexReader()
	 */
	public synchronized void closeIndexReader() {
		assertOwnLock();
		IndexReader toClose = reader;
		reader = null;
		if ( toClose != null ) {
			try {
				toClose.close();
				log.trace( "IndexReader closed" );
			}
			catch ( IOException e ) {
				throw new SearchException( "Exception while closing IndexReader", e );
			}
		}
		else {
			throw new AssertionFailure( "No IndexReader open to close." );
		}
	}
	
	/**
	 * Gets the IndexWriter, opening one if needed.
	 * @param batchmode when true the indexWriter settings for batch mode will be applied.
	 * Ignored if IndexWriter is open already.
	 * @throws AssertionFailure if an IndexReader is open or the lock is not owned.
	 * @throws SearchException on a IOException during index opening.
	 * @return a new IndexWriter or one already open.
	 */
	public synchronized IndexWriter getIndexWriter(boolean batchmode) {
		assertOwnLock();
		// one has to close a reader for update before a writer is accessed
		if ( reader != null )
			throw new AssertionFailure( "Tries to open an IndexWriter while an IndexReader is open in update mode." );
		if ( writer != null )
			return writer;
		try {
			// don't care about the Analyzer as it will be selected during usage of IndexWriter.
			IndexWriter.MaxFieldLength fieldLength = new IndexWriter.MaxFieldLength( IndexWriter.DEFAULT_MAX_FIELD_LENGTH );
			writer = new IndexWriter( directoryProvider.getDirectory(), SIMPLE_ANALYZER, false, fieldLength ); // has been created at init time
			indexingParams.applyToWriter( writer, batchmode );
			log.trace( "IndexWriter opened" );
		}
		catch ( IOException e ) {
			writer = null;
			throw new SearchException( "Unable to open IndexWriter", e );
		}
		return writer;
	}

	/**
	 * Commits changes to a previously opened index writer.
	 *
	 * @throws SearchException on IOException during Lucene close operation.
	 * @throws AssertionFailure if there is no IndexWriter to close, or if the lock is not owned.
	 */
	public synchronized void commitIndexWriter() {
		assertOwnLock();
		if ( writer != null ) {
			try {
				writer.commit();
				log.trace( "Index changes commited." );
			}
			catch ( IOException e ) {
				throw new SearchException( "Exception while commiting index changes", e );
			}
		}
		else {
			throw new AssertionFailure( "No open IndexWriter to commit changes." );
		}
	}

	/**
	 * Closes a previously opened IndexWriter.
	 * @throws SearchException on IOException during Lucene close operation.
	 * @throws AssertionFailure if there is no IndexWriter to close, or if the lock is not owned.
	 */
	public synchronized void closeIndexWriter() {
		assertOwnLock();
		IndexWriter toClose = writer;
		writer = null;
		if ( toClose != null ) {
			try {
				toClose.close();
				log.trace( "IndexWriter closed" );
			}
			catch ( IOException e ) {
				throw new SearchException( "Exception while closing IndexWriter", e );
			}
		}
		else {
			throw new AssertionFailure( "No open IndexWriter to close" );
		}
	}

	/**
	 * Increment the counter of modification operations done on the index.
	 * Used (currently only) by the OptimizerStrategy.
	 * @param modCount the increment to add to the counter.
	 */
	public void incrementModificationCounter(int modCount) {
		operations.addAndGet( modCount );
	}

	/**
	 * @return The unmodifiable set of entity types being indexed
	 * in the underlying Lucene Directory backing this Workspace.
	 */
	public Set<Class<?>> getEntitiesInDirectory() {
		return entitiesInDirectory;
	}
	
	/**
	 * Acquires a lock on the DirectoryProvider backing this Workspace;
	 * this is required to use getIndexWriter(boolean), closeIndexWriter(),
	 * getIndexReader(), closeIndexReader().
	 * @see #getIndexWriter(boolean)
	 * @see #closeIndexWriter()
	 * @see #getIndexReader()
	 * @see #closeIndexReader()
	 */
	public void lock() {
		lock.lock();
	}

	/**
	 * Releases the lock obtained by calling lock()
	 * @throws AssertionFailure when unlocking without having closed IndexWriter or IndexReader.
	 * @see #lock()
	 */
	public synchronized void unlock() {
		try {
			if ( this.reader != null ) {
				throw new AssertionFailure( "Unlocking Workspace without having closed the IndexReader" );
			}
			if ( this.writer != null ) {
				throw new AssertionFailure( "Unlocking Workspace without having closed the IndexWriter" );
			}
		}
		finally {
			lock.unlock();
		}
	}

	private void assertOwnLock() {
		if ( ! lock.isHeldByCurrentThread() )
			throw new AssertionFailure( "Not owning DirectoryProvider Lock" );
	}

}
