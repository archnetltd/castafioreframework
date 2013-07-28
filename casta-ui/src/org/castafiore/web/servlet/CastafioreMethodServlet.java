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

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.js.JSObject;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.ComponentUtil;
import org.springframework.web.servlet.mvc.Controller;

public class CastafioreMethodServlet extends AbstractCastafioreServlet{
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(request.getParameter("controller") != null){
			String controller = request.getParameter("controller");
			
			 Controller c = (Controller)BaseSpringUtil.getBean(controller);
			 c.handleRequest(request, response);
			 return;
		}
		
		
		String applicationid  = request.getParameter("applicationid");
		String componentid = request.getParameter("componentid");
		String methodName = request.getParameter("method");
		String param = request.getParameter("paramName")!= null? request.getParameter(request.getParameter("paramName")):null;
		try
		{
			
			
			Application applicationInstance = (Application) ((HttpServletRequest)request).getSession().getAttribute(applicationid);
			Container c = ComponentUtil.getContainerById(applicationInstance, componentid);
			if(c instanceof Controller){
				((Controller)c).handleRequest(request, response);
				return;
			}
			
			Object o = c.getClass().getMethod(methodName, String.class).invoke(c, param);
			if(o != null){
				if(o instanceof InputStream){  
					ChannelUtil.TransferData((InputStream)o, response.getOutputStream());
					response.getOutputStream().flush();
				}else if(o instanceof JSObject){
					response.getOutputStream().write(((JSObject)o).getJavascript().getBytes());
					response.getOutputStream().flush();
				}else{
					response.getOutputStream().write(o.toString().getBytes());
					response.getOutputStream().flush();
				}
				
				//response.setContentType(MimeUtility);
				((HttpServletResponse)response).setHeader("Content-Disposition", "filename=" + methodName.replace("_", ".")); 
			}

			
		}
		catch(Exception e)
		{
			throw new ServletException("unable to load method since the params passed are not correct" ,e);
		}
		
		
	}


	
}
