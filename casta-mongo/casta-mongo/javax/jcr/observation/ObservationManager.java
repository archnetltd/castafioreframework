/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.observation;

import javax.jcr.RepositoryException;

/**
 * The ObservationManager object.
 * <p>
 * Acquired via <code>{@link javax.jcr.Workspace#getObservationManager()}</code>.
 * Allows for the registration and deregistration of event listeners.
 */
public interface ObservationManager {

    /**
     * Adds an event listener that listens for the specified
     * <code>eventTypes</code> (a combination of one or more event types encoded
     * as a bit mask value).
     * <p>
     * The set of events will be further filtered by the access rights of the
     * current <code>Session</code> as well as the restrictions specified by the
     * parameters of this method. These restrictions are stated in terms of
     * characteristics of the <i>associated parent node</i> of the event.
     * <p>
     * The associated parent node of an event is the parent node of the item at
     * (or formerly at) the path returned by {@link Event#getPath}. The
     * following restrictions are available: <ul> <li> <code>absPath</code>,
     * <code>isDeep</code>: Only events whose associated parent node is at
     * <code>absPath</code> (or within its subgraph, if <code>isDeep</code> is
     * <code>true</code>) will be received. It is permissible to register a
     * listener for a path where no node currently exists. </li> <li>
     * <code>uuid</code>: Only events whose associated parent node has one of
     * the identifiers in this list will be received. If his parameter is
     * <code>null</code> then no identifier-related restriction is placed on
     * events received. Note that specifying an empty array instead of
     * <code>null</code> would result in no nodes being listened to. The term
     * "UUID" is used for compatibility with JCR 1.0. </li> <li>
     * <code>nodeTypeName</code>: Only events whose associated parent node has
     * one of the node types (or a subtype of one of the node types) in this
     * list will be received. If his parameter is <code>null</code> then no node
     * type-related restriction is placed on events received. Note that
     * specifying an empty array instead of <code>null</code> would result in no
     * nodes types being listened to. </li> </ul> The restrictions are "ANDed"
     * together. In other words, for a particular node to be "listened to" it
     * must meet all the restrictions.
     * <p>
     * Additionally, if <code>noLocal</code> is <code>true</code>, then events
     * generated by the session through which the listener was registered are
     * ignored. Otherwise, they are not ignored.
     * <p>
     * The filters of an already-registered <code>EventListener</code> can be
     * changed at runtime by re-registering the same <code>EventListener</code>
     * object (i.e. the same actual Java object) with a new set of filter
     * arguments. The implementation must ensure that no events are lost during
     * the changeover.
     * <p>
     * In addition to the filters placed on a listener above, the scope of
     * observation support, in terms of which subgraphs are observable, may also
     * be subject to implementation-specific restrictions. For example, in some
     * repositories observation of changes in the <code>jcr:system</code>
     * subgraph may not be supported
     *
     * @param listener     an {@link EventListener} object.
     * @param eventTypes   A combination of one or more event type constants
     *                     encoded as a bitmask.
     * @param absPath      an absolute path.
     * @param isDeep       a <code>boolean</code>.
     * @param uuid         array of identifiers.
     * @param nodeTypeName array of node type names.
     * @param noLocal      a <code>boolean</code>.
     * @throws RepositoryException If an error occurs.
     */
    public void addEventListener(EventListener listener, int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal)
            throws RepositoryException;

    /**
     * Deregisters an event listener.
     * <p>
     * A listener may be deregistered while it is being executed. The
     * deregistration method will block until the listener has completed
     * executing. An exception to this rule is a listener which deregisters
     * itself from within the <code>onEvent</code> method. In this case, the
     * deregistration method returns immediately, but deregistration will
     * effectively be delayed until the listener completes.
     *
     * @param listener The listener to deregister.
     * @throws RepositoryException If an error occurs.
     */
    public void removeEventListener(EventListener listener) throws RepositoryException;

    /**
     * Returns all event listeners that have been registered through this
     * session. If no listeners have been registered, an empty iterator is
     * returned.
     *
     * @return an <code>EventListenerIterator</code>.
     * @throws RepositoryException If an error occurs
     */
    public EventListenerIterator getRegisteredEventListeners() throws RepositoryException;

    /**
     * Sets the user data information that will be returned by {@link
     * Event#getUserData}.
     *
     * @param userData the user data
     * @throws RepositoryException if an error occurs
     */
    public void setUserData(String userData) throws RepositoryException;

    /**
     * Retrieves the event journal for this workspace. If journaled observation
     * is not supported for this workspace, <code>null</code> is returned.
     * Events recorded in the <code>EventJournal</code> instance will be
     * filtered according to the current session's access rights as well as any
     * additional restrictions specified through implemention-specific
     * configuration.
     *
     * @return an <code>EventJournal</code> or <code>null</code>.
     * @throws RepositoryException if an error occurs
     */
    public EventJournal getEventJournal() throws RepositoryException;

    /**
     * Retrieves the event journal for this workspace.
     * <p>
     * If journaled observation
     * is not supported for this workspace, <code>null</code> is returned.
     * <p>
     * Events returned in the <code>EventJournal</code> instance will be
     * filtered according to the parameters of this method,
     * the current session's access restrictions as well as any
     * additional restrictions specified through implemention-specific
     * configuration.
     * <p>
     * The parameters of this method filter the event set in the same way as
     * they do in {@link #addEventListener}.
     *
     * @param eventTypes   A combination of one or more event type constants encoded as a bitmask.
     * @param absPath      an absolute path.
     * @param isDeep       a <code>boolean</code>.
     * @param uuid         array of identifiers.
     * @param nodeTypeName array of node type names.
     *
     * @return an <code>EventJournal</code> or <code>null</code>.
     * @throws RepositoryException If an error occurs.
     */
    public EventJournal getEventJournal(int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName)
            throws RepositoryException;
}