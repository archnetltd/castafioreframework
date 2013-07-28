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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface Dao {	
	
	public void setSessionFactory(SessionFactory sessionFactory);

	public HibernateTemplate getHibernateTemplate();

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) ;
	
	public Session getSession();
	
	public void closeSession();
	
	public void rollBack();
	
	public Session getReadOnlySession();
	
	
	public int countRows(Criteria criteria);
}
