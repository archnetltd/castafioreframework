/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.query;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

/**
 * A <code>QueryResult</code> object. Returned by {@link
 * javax.jcr.query.Query#execute()}.
 */
public interface QueryResult {

    /**
     * Returns an array of all the column names in the table view of this result
     * set.
     *
     * @return a <code>String</code> array holding the column names.
     * @throws RepositoryException if an error occurs.
     */
    public String[] getColumnNames() throws RepositoryException;

    /**
     * Returns an iterator over the <code>Row</code>s of the result table. The
     * rows are returned according to the ordering specified in the query.
     *
     * @return a <code>RowIterator</code>
     * @throws RepositoryException if this call is the second time either
     *                             <code>getRows</code> or </code>getNodes</code> has been called on the
     *                             same <code>QueryResult</code> object or if another error occurs.
     */
    public RowIterator getRows() throws RepositoryException;

    /**
     * Returns an iterator over all nodes that match the query. The nodes are
     * returned according to the ordering specified in the query.
     *
     * @return a <code>NodeIterator</code>
     * @throws RepositoryException if the query contains more than one selector,
     *                             if this call is the second time either <code>getRows</code> or
     *                             </code>getNodes</code> has been called on the same
     *                             <code>QueryResult</code> object or if another error occurs.
     */
    public NodeIterator getNodes() throws RepositoryException;

    /**
     * Returns an array of all the selector names that were used in the query
     * that created this result. If the query did not have a selector name then
     * an empty array is returned.
     *
     * @return a <code>String</code> array holding the selector names.
     * @throws RepositoryException if an error occurs.
     */
    public String[] getSelectorNames() throws RepositoryException;
}