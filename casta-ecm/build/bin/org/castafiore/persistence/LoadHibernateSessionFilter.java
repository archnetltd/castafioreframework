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

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.castafiore.spring.SpringUtil;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LoadHibernateSessionFilter implements Filter {
	private Transaction t = null;
	
	private int creatorHash=0;

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		SessionFactory factory = SpringUtil.getBeanOfType(SessionFactory.class);
		Map castaSession = SpringUtil.getBean("casta_session");
		Session s = null;
		if(!castaSession.containsKey("hibernate.session")){
			s = factory.openSession();
			s.setFlushMode(FlushMode.COMMIT);
			s.setCacheMode(CacheMode.IGNORE);
			castaSession.put("hibernate.session", s);
			
		}else{
			s = (Session)castaSession.get("hibernate.session");
		}
		
		//t = null;
		try{
			if(t == null){
				t = s.beginTransaction();
				creatorHash = request.hashCode();
			}
			
			if(!t.isActive()){
				t.begin();
				creatorHash = request.hashCode();
			}
			
			chain.doFilter(request, response);
			
			//s.flush();
			if(t.isActive()){
				if(creatorHash == request.hashCode()){
					s.flush();
					t.commit();
					s.clear();
					
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			if(t != null && t.isActive()){
				if(creatorHash == request.hashCode()){
					t.rollback();
					s.clear();
				}
			}
			
			if(s != null){
				
			}
		}
		
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
