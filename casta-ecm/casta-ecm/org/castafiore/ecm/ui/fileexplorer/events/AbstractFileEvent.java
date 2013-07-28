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
 package org.castafiore.ecm.ui.fileexplorer.events;

import java.util.Map;

import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.service.RepositoryService;

public abstract class AbstractFileEvent implements Event{
	
	private RepositoryService repositoryService;
	
	protected  void checkRequest(Map<String, String> request)
	{
		if(!request.containsKey("path"))
		{
			usageException();
		}
	}
	
	protected UIException usageException()
	{
		String usage = "Parameter: path is required in request to open file";
		throw new UIException(usage);
	}

	public RepositoryService getRepositoryService() {
		if(repositoryService == null)
		{
			repositoryService = BaseSpringUtil.getBeanOfType(RepositoryService.class);
		}
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
	public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
		
		
	}

}
