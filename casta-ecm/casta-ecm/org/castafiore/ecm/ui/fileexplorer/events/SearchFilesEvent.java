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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;

public class SearchFilesEvent implements Event {

	
	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
	}

	
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXInput input = container.getParent().getDescendentOfType(EXInput.class);
		String searchString = input.getValue().toString();
		
		if(StringUtil.isNotEmpty(searchString)){
			
			Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
			Map map = session.getSessionFactory().getAllClassMetadata();
			
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()){
				String cls = iter.next().toString();
//				Class clz = Thread.currentThread().getContextClassLoader().loadClass(cls);
//				if(clz.isAssignableFrom(FileImpl.class)){
//					ClassMetadata m = (ClassMetadata)map.get(cls);
//					String[] properties = m.getPropertyNames();
//					for(String property : properties){
//						//m.getPropertyType(property).is
//					}
//				}
			}
			List<File> files =SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setFullTextSearch(searchString), Util.getRemoteUser());
			
			container.getAncestorOfType(Explorer.class).getView().showFiles(files);
		}
		
		return true;
	}


	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
