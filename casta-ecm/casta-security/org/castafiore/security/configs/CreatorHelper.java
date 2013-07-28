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
 package org.castafiore.security.configs;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.Starter;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CreatorHelper implements ApplicationContextAware{
	private ApplicationContext appContext;
	
	private Dao dao;
	
	
	public Dao getDao() {
		return dao;
	}


	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public void init(){
		
		Session session = dao.getSession();
		Map<String, AbstractCreator> beans = appContext.getBeansOfType(AbstractCreator.class);
		Iterator<String>  iters = beans.keySet().iterator();
		while(iters.hasNext()){
			AbstractCreator abs = beans.get(iters.next());
			abs.save();
			
		}
		
		dao.closeSession();
		
		appContext.getBean(Starter.class).executeStart();
	}



	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.appContext = arg0;
		
	}

}
