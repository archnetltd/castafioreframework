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
 package org.castafiore.web.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.engine.context.CastafioreFilter;
import org.castafiore.utils.BaseSpringUtil;

public abstract class AbstractCastafioreServlet extends HttpServlet {

	//protected final static Log logger = LogFactory.getLog(AbstractCastafioreServlet.class);
	
	
	
	
	
	
	@Override
	public void init() throws ServletException {
		
		BaseSpringUtil.init(this);
	}



	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		//long start = System.currentTimeMillis();
		Map filters = BaseSpringUtil.getApplicationContext().getBeansOfType(CastafioreFilter.class);
		Iterator iter = filters.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next().toString();
			CastafioreFilter filter = (CastafioreFilter)filters.get(key);
			try{
				filter.doStart((HttpServletRequest)request);
			}catch(Exception e){
				throw new ServletException("exception occured on method doStart in CastafioreFilter " + filter.getClass().getName(), e);
			}
			
		}
		try{
				doService((HttpServletRequest)request, (HttpServletResponse)response);
			}
			catch(Exception e){
				Iterator iterend = filters.keySet().iterator();
				while(iterend.hasNext()){
					String key = iterend.next().toString();
					CastafioreFilter filter = (CastafioreFilter)filters.get(key);
					try{
						filter.onException((HttpServletRequest)request,e);
					}catch(Exception ee){
						
						throw new ServletException("exception occured on method onException in CastafioreFilter " + filter.getClass().getName(), e);
					}
				}
				CastafioreApplicationContextHolder.resetApplicationContext();
				throw new ServletException(e);
			}
			
			
			Iterator iterend = filters.keySet().iterator();
			while(iterend.hasNext()){
				String key = iterend.next().toString();
				CastafioreFilter filter = (CastafioreFilter)filters.get(key);
				try{
					filter.doEnd((HttpServletRequest)request);
				}catch(Exception e){
					throw new ServletException("exception occured on method doEnd in CastafioreFilter " + filter.getClass().getName(), e);
				}
				finally{
					
				}
			}
			CastafioreApplicationContextHolder.resetApplicationContext();
			//logger.info("completed in " + (System.currentTimeMillis() - start) + " ms");
			
		}



		public abstract void doService(HttpServletRequest request,HttpServletResponse response)throws Exception ;
		
	}
	
	

