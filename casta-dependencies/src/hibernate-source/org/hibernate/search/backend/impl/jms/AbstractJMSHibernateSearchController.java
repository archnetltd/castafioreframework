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
 //$Id: AbstractJMSHibernateSearchController.java 15354 2008-10-15 15:14:25Z hardy.ferentschik $
package org.hibernate.search.backend.impl.jms;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;

import org.hibernate.Session;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.util.ContextHelper;
import org.hibernate.search.util.LoggerFactory;

/**
 * Implement the Hibernate Search controller responsible for processing the
 * work send through JMS by the slave nodes.
 *
 * Note the subclass implementation has to implement javax.jms.MessageListener
 * //TODO Ask Bill why it is required
 *
 * @author Emmanuel Bernard
 */
public abstract class AbstractJMSHibernateSearchController implements MessageListener {
	private static final Logger log = LoggerFactory.make();

	/**
	 * Return the current or give a new session
	 * This session is not used per se, but is the link to access the Search configuration.
	 * <p>
	 * A typical EJB 3.0 usecase would be to get the session from the container (injected)
	 * eg in JBoss EJB 3.0
	 * <p>
	 * <code>
	 * &#64;PersistenceContext private Session session;<br>
	 * <br>
	 * protected Session getSession() {<br>
	 *  &nbsp; &nbsp;return session<br>
	 * }<br>
	 * </code>
	 * <p>
	 * eg in any container<br>
	 * <code>
	 * &#64;PersistenceContext private EntityManager entityManager;<br>
	 * <br>
	 * protected Session getSession() {<br>
	 *  &nbsp; &nbsp;return (Session) entityManager.getdelegate();<br>
	 * }<br>
	 * </code>
	 */
	protected abstract Session getSession();

	/**
	 * Ensure to clean the resources after use.
	 * If the session has been directly or indirectly injected, this method is empty
	 */
	protected abstract void cleanSessionIfNeeded(Session session);

	/**
	 * Process the Hibernate Search work queues received
	 */
	public void onMessage(Message message) {
		if ( !( message instanceof ObjectMessage ) ) {
			log.error( "Incorrect message type: {}", message.getClass() );
			return;
		}
		ObjectMessage objectMessage = (ObjectMessage) message;
		List<LuceneWork> queue;
		try {
			queue = (List<LuceneWork>) objectMessage.getObject();
		}
		catch (JMSException e) {
			log.error( "Unable to retrieve object from message: " + message.getClass(), e );
			return;
		}
		catch (ClassCastException e) {
			log.error( "Illegal object retrieved from message", e );
			return;
		}
		Runnable worker = getWorker( queue );
		worker.run();
	}

	private Runnable getWorker(List<LuceneWork> queue) {
		//FIXME casting sucks because we do not control what get session from
		Session session = getSession();
		Runnable processor = null;

		try {
			SearchFactoryImplementor factory = ContextHelper.getSearchFactory( session );
			processor = factory.getBackendQueueProcessorFactory().getProcessor( queue );
		}
		finally {
			cleanSessionIfNeeded(session);
		}
		return processor;
	}

	@PostConstruct
	public void initialize() {
		//init the source copy process
		//TODO actually this is probably wrong since this is now part of the DP
	}

	@PreDestroy
	public void shutdown() {
		//stop the source copy process
		//TODO actually this is probably wrong since this is now part of the DP
	}
}
