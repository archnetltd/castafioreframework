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

package org.castafiore.spring;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.security.api.RelationshipManager;
import org.castafiore.security.api.SecurityService;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.service.RepositoryService;


/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class SpringUtil extends BaseSpringUtil {
	
	
	
	
	
	public static org.castafiore.wfs.security.SecurityManager getSecurityManager()
	{
		return getBean("securityManager");
	}
	
	
	public static SecurityService getSecurityService()
	{
		return getBean("securityService");
	}
	
	public static RepositoryService getRepositoryService()
	{
		
		return getBean("repositoryService");
		
	}
	
	
	
	
	public static RelationshipManager getRelationshipManager(){
		return getBeanOfType(RelationshipManager.class);
	}
	
	/*public static PathViewConfig getPathViewConfig(){
		return getBean("pathViewConfig");
	}
	
	public static IconImageProvider getIconImageProvider()
	{
		return getBean("iconImageProvider");
	}
	
	
	public MetdataService getMetadataService(){
		return getBean("metadataService");
	}*/
	
	
	
	
}
