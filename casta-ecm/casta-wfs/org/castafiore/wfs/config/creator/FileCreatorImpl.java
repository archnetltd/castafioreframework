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
 package org.castafiore.wfs.config.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.persistence.Dao;
import org.castafiore.security.configs.CreatorHelper;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.FileImpl;
import org.castafiore.wfs.types.config.AbstractFileConfig;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FileCreatorImpl implements FileCreator , ApplicationContextAware{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ApplicationContext context;
	
	List<AbstractFileConfig> beans = new ArrayList<AbstractFileConfig>();
	
	private boolean complete = false;

	public void loadConfigs() {
		Map mBeans  = context.getBeansOfType(AbstractFileConfig.class);
		//Iterator iterKeys = mBeans.keySet().iterator();
		Set keys = mBeans.keySet();
		beans = new ArrayList<AbstractFileConfig>();
		
		Iterator iterKeys = keys.iterator();
		while(iterKeys.hasNext())
		{
			
			String key = iterKeys.next().toString();
			
			Object bean = mBeans.get(key);
			
			beans.add((AbstractFileConfig)bean);
		}
		
		Collections.sort(beans, new PathComparator());
		for(AbstractFileConfig s : beans)
		{
			logger.debug(s.getParentDir() + "/" + s.getName());
		}
	}
	
	public void start(){
		loadConfigs();
		create();
	}

	public static class PathComparator implements
			Comparator<AbstractFileConfig> {
		

		public int compare(AbstractFileConfig o1, AbstractFileConfig o2) {
			
			String str1 = o1.getParentDir();
			
			String str2 = o2.getParentDir();
			
			if (str1 == null || str2 == null) {
				throw new NullPointerException("The strings must not be null");
			}
			return  str1.compareTo(str2);
		}

	}
	
	
	public void create()
	{
		Dao dao = context.getBeansOfType(Dao.class).values().iterator().next();;
		//Session session = dao.getSession();
		List root = dao.getSession().createQuery("from " + FileImpl.class.getName() + " where absolutePath='/root'").list();
		if(root.size() == 0){
			Drive d = new Drive();
			d.setName("root");
			d.setAbsolutePath("/root");
			//d.setOwner()
			dao.getSession().save(d);
			dao.getSession().flush();
		}else{
			return;
		}
		
		
		try
		{
			for(AbstractFileConfig config : beans)
			{
				Session session = dao.getSession();
				config.save();
				dao.closeSession();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dao.rollBack();
			
		}
		
		context.getBeansOfType(CreatorHelper.class);
		
		
		complete=true;
	}


	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
		
	}

	public boolean isComplete() {
		return complete;
	}

}
