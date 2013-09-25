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
package org.castafiore.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.InvalidApplicationConfigurationException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Implementation of {@link ApplicationRegistry} that using a spring
 * {@link ApplicationContext} as registry.
 * 
 * @author arossaye
 * 
 */
public class SpringApplicationRegistry implements ApplicationRegistry,
		ApplicationContextAware {

	private ApplicationContext context_ = null;

	/**
	 * @see ApplicationRegistry#getApplication(HttpServletRequest,
	 *      HttpServletResponse)
	 */
	public Application getApplication(HttpServletRequest request,
			HttpServletResponse response) {
		String applicationId = request.getParameter("casta_applicationid");
		if (applicationId == null) {
			throw new RuntimeException(
					"cannot load application. Please specify parameter casta_applicationid=<appname>");
		}
		try {

			Application application = (Application) context_
					.getBean(applicationId);
			return application;
		} catch (ClassCastException cce) {
			throw new InvalidApplicationConfigurationException(
					"The application "
							+ applicationId
							+ " configured in application context is not of type Application",
					cce);
		}
	}

	/**
	 * @see ApplicationContextAware#setApplicationContext(ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context_ = applicationContext;

	}

}
