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
 package org.castafiore.beans.info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.castafiore.beans.IdentifyAble;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanInfoServiceImpl implements BeanInfoService, ApplicationContextAware{
	
	private ApplicationContext appContext;

	public  IBeanInfo getBeanInfo(String uniqueId) {
		Map<String, IBeanInfo> beanInfos = appContext.getBeansOfType(IBeanInfo.class);
		
		Iterator<String> iterKeys = beanInfos.keySet().iterator();
		
		while(iterKeys.hasNext()){
			String key = iterKeys.next();
			
			IBeanInfo info = beanInfos.get(key);
			if(uniqueId.equals(info.getSupportedUniqueId())){
				return info;
			}
		}
		
		return null;
	}

	public Map<String, IBeanInfo> getBeanInfo(Class<? extends IdentifyAble> clazz) {
		Map<String, ? extends IdentifyAble> beans = appContext.getBeansOfType(clazz);
		Iterator<String> iter = beans.keySet().iterator();
		
		Map<String, IBeanInfo> result = new HashMap<String, IBeanInfo>(); 
		while(iter.hasNext()){
			String key = iter.next();
			
			IdentifyAble idBean = beans.get(key);
			IBeanInfo info = getBeanInfo(idBean.getUniqueId());
			if(info != null){
				result.put(idBean.getUniqueId(),info);
			}
			
		}
		
		return result;
	}

	public <T extends IdentifyAble> T getBeanOfType(Class<T> clazz, String uniqeId) {
		Map<String,? extends IdentifyAble> beanInfos = appContext.getBeansOfType(clazz);
		
		Iterator<String> iterKeys = beanInfos.keySet().iterator();
		
		while(iterKeys.hasNext()){
			String key = iterKeys.next();
			
			IdentifyAble idBean = beanInfos.get(key);
			if(uniqeId.equals(idBean.getUniqueId())){
				return  (T)idBean;
			}
		}
		
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
		
	}

}
