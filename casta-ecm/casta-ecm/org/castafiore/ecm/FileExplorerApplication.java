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
 package org.castafiore.ecm;

import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FileExplorerApplication extends EXApplication implements ApplicationContextAware {
	
	private ApplicationContext context;

	public FileExplorerApplication() {
		super("fileExplorerApp");
		
		// TODO Auto-generated constructor stub
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
		
	}
	
	
	public ApplicationContext getApplicationContext(){
		return context;
	}

	public void init() {
		try
		{
			SecurityService service = SpringUtil.getSecurityService();
			service.login("system", "admin");
			Container container = (Container)SpringUtil.getBean("fileExplorer");
			addChild(container);
		}catch(Exception e){
			throw new RuntimeException(e);
			//e.printStackTrace();
		}
		
	}

}
