/*
 * Copyright (C) 2007-2008 Castafiore
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


import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.resource.FileData;
import org.castafiore.resource.ResourceLocator;
import org.castafiore.resource.ResourceLocatorFactory;
import org.castafiore.utils.ChannelUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class CastafioreResourceServlet extends AbstractCastafioreServlet
{
	//protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String spec = null;
		try
		{
			spec = request.getParameter("spec");
			//logger.debug(spec);
			ResourceLocator locator = ResourceLocatorFactory.getResourceLocator(spec);
			String width = request.getParameter("width");
			FileData f = locator.getResource(spec, width);
			if(f != null)
			{
				try
				{
					OutputStream os = response.getOutputStream();
					response.setContentType(f.getMimeType());
					((HttpServletResponse)response).setHeader("Content-Disposition", "filename=" + f.getName()); 
					ChannelUtil.TransferData(f.getInputStream(), os);
					os.flush();	 
				}
				catch(ClassCastException cce)
				{
					throw new Exception("the file specified by the path " + spec + " is not a binary file");
				}
			}
			else
			{
				 throw new Exception("the file specified by the path " + spec + " cannot be found. Possibly deleted");
			}
		}
		catch(Exception e)
		{
			if(spec == null)
				throw new ServletException("unable to load resource since the specification passed is null" ,e);
			else
				throw new ServletException("unable to load resource with the specification " + spec ,e);
		}
		
		
	}

}
