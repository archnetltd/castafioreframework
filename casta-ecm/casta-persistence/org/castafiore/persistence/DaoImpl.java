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
 package org.castafiore.persistence;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class DaoImpl implements Dao {
	
private HibernateTemplate hibernateTemplate;

	private static ThreadLocal<Session> SESSION_THREAD = new ThreadLocal<Session>();
	
	private static ThreadLocal<Transaction> TRANSACTION_THREAD = new ThreadLocal<Transaction>();
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	public Session getReadOnlySession(){
		try{
			Session session = SESSION_THREAD.get();
			if(session == null || !session.isOpen()){
				session = getHibernateTemplate().getSessionFactory().openSession();
				session.setFlushMode(FlushMode.MANUAL);
				//session.setCacheMode(CacheMode.IGNORE);
				SESSION_THREAD.set(session);
			}
			return session;

		}catch(Exception e){
			//return HibernateUtil.getSession(getHibernateTemplate().getSessionFactory());
			throw new RuntimeException(e);
		}
	}
	
	public Session getSession(){
		try{
			Session session = SESSION_THREAD.get();
			if(session == null || !session.isOpen()){
				session = getHibernateTemplate().getSessionFactory().openSession();
				session.setFlushMode(FlushMode.MANUAL);
				//session.setCacheMode(CacheMode.IGNORE);
				SESSION_THREAD.set(session);
			}
			
			Transaction t = TRANSACTION_THREAD.get();
			if(t == null){
				t= session.beginTransaction();
				t.setTimeout(9000);
				TRANSACTION_THREAD.set(t);
				
			}else{
				if(!t.isActive()){
					t.begin();
				}
			}
			return session;

		}catch(Exception e){
			//return HibernateUtil.getSession(getHibernateTemplate().getSessionFactory());
			throw new RuntimeException(e);
		}
	}
	
	
	public void closeSession(){
		Session session = SESSION_THREAD.get();
		Transaction t = TRANSACTION_THREAD.get();
		if(session != null && session.isOpen()){
			try{
				session.flush();
				if(t!=null && t.isActive()){
					t.commit();
				}
				session.close();
				
			}catch(Exception e){
				try{
					session.clear();
					if(t != null)
						t.rollback();
					session.close();
				}catch(Exception ee){
					ee.printStackTrace();
				}
				//throw new RuntimeException(e);
			}
		}
		SESSION_THREAD.remove();
		TRANSACTION_THREAD.remove();
	}
	
	public void rollBack(){
		Session session = SESSION_THREAD.get();
		Transaction t = TRANSACTION_THREAD.get();
		if(session != null){
			try{
				
				if(t != null){
					t.rollback();
				}
				session.close();
			}catch(Exception e){
			}
		}
		SESSION_THREAD.remove();
		TRANSACTION_THREAD.remove();
	}

	@Override
	public int countRows(Criteria criteria) {
		return (Integer)criteria.setProjection(Projections.rowCount()).list().get(0);
	}

}
