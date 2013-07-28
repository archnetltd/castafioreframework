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
 package org.castafiore.ecm.ui.fileexplorer;

import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.dynaform.models.ListProvider;
import org.castafiore.utils.ExceptionUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.File;

public abstract class AbstractRepositoryFileListProvider implements ListProvider{
	
	
	public List<File> getList(Class beanType) {
		try
		{
			
			RepositoryService service = SpringUtil.getRepositoryService();
			QueryParameters params = new QueryParameters().setEntity(beanType);
			return service.executeQuery(params, Util.getRemoteUser());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw ExceptionUtil.getRuntimeException("unable to execute " + this.getClass().getName(), e);
		}
	}
	
	public abstract Class getEntityClass();

}
