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
 package org.castafiore.ui.engine.context;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContextLoaderFilter implements CastafioreFilter{

	private static final String REQUEST_ATTRIBUTES_ATTRIBUTE = 	RequestContextLoaderFilter.class.getName() + ".REQUEST_ATTRIBUTES";
	
	public void doEnd(HttpServletRequest request) throws Exception {
		ServletRequestAttributes attributes =(ServletRequestAttributes)request.getAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE);
		ServletRequestAttributes threadAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (threadAttributes != null) {
			// We're assumably within the original request thread...
			if (attributes == null) {
				attributes = threadAttributes;
			}
			LocaleContextHolder.resetLocaleContext();
			RequestContextHolder.resetRequestAttributes();
		}
		if (attributes != null) {
			attributes.requestCompleted();
		}
		
	}

	public void doStart(HttpServletRequest request) throws Exception {
		ServletRequestAttributes attributes = new ServletRequestAttributes(request);
		request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		RequestContextHolder.setRequestAttributes(attributes);
		
	}

	public void onException(HttpServletRequest request, Exception e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
