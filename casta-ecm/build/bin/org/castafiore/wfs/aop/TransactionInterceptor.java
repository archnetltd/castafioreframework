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
 package org.castafiore.wfs.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



public class TransactionInterceptor implements MethodInterceptor, ApplicationContextAware {
	
	private ApplicationContext applicationContext;

	

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.applicationContext = context;
		
	}



	public Object invoke(MethodInvocation invocation) throws Throwable {
		//HibernateUtil.OpenSession(applicationContext);
		try{
			Object result = invocation.proceed();
			return result;
		}finally{
			//HibernateUtil.closeSession(applicationContext);
		}
		
		
	}

}
