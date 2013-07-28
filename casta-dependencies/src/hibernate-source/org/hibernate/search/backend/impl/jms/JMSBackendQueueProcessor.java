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
 //$Id: JMSBackendQueueProcessor.java 15368 2008-10-22 10:12:17Z hardy.ferentschik $
package org.hibernate.search.backend.impl.jms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import org.slf4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.OptimizeLuceneWork;
import org.hibernate.search.util.LoggerFactory;

/**
 * @author Emmanuel Bernard
 */
public class JMSBackendQueueProcessor implements Runnable {
	private static final Logger log = LoggerFactory.make();

	private List<LuceneWork> queue;
	private JMSBackendQueueProcessorFactory factory;

	public JMSBackendQueueProcessor(List<LuceneWork> queue,
									JMSBackendQueueProcessorFactory jmsBackendQueueProcessorFactory) {
		this.queue = queue;
		this.factory = jmsBackendQueueProcessorFactory;
	}

	public void run() {
		List<LuceneWork> filteredQueue = new ArrayList<LuceneWork>(queue);
		for (LuceneWork work : queue) {
			if ( work instanceof OptimizeLuceneWork ) {
				//we don't want optimization to be propagated
				filteredQueue.remove( work );
			}
		}
		if ( filteredQueue.size() == 0) return;
		factory.prepareJMSTools();
		QueueConnection cnn = null;
		QueueSender sender;
		QueueSession session;
		try {
			cnn = factory.getJMSFactory().createQueueConnection();
			//TODO make transacted parameterized
			session = cnn.createQueueSession( false, QueueSession.AUTO_ACKNOWLEDGE );

			ObjectMessage message = session.createObjectMessage();
			message.setObject( (Serializable) filteredQueue );

			sender = session.createSender( factory.getJmsQueue() );
			sender.send( message );

			session.close();
		}
		catch (JMSException e) {
			throw new HibernateException( "Unable to send Search work to JMS queue: " + factory.getJmsQueueName(), e );
		}
		finally {
			try {
				if (cnn != null)
					cnn.close();
				}
			catch ( JMSException e ) {
				log.warn( "Unable to close JMS connection for " + factory.getJmsQueueName(), e );
			}
		}
	}
}
