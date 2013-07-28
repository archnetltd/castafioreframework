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
 package org.castafiore.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.castafiore.context.SerializableWebApplicationContext;
import org.castafiore.web.servlet.AbstractCastafioreServlet;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class BaseSpringUtil {
	
	private static ConfigurableWebApplicationContext context = null;
	
	//private static ThreadLocal<ApplicationContext> ContextThreadHolder = new ThreadLocal<ApplicationContext>();
	 
	public static void init(AbstractCastafioreServlet servlet){
		if(context == null){
			context = new SerializableWebApplicationContext();
			context.setServletConfig(servlet.getServletConfig());
			context.setNamespace("castafiore");
			//wac.setConfigLocation(getContextConfigLocation());
			context.setConfigLocation("/WEB-INF/configs/**/*-config.xml");
			context.setServletContext(servlet.getServletContext());
			context.refresh();
		}
	}
	
	
	public static ApplicationContext getApplicationContext(){
		
		if(context == null){
			throw new RuntimeException("Application context has not been initialised yet....");
		}
		return context;
		
		
	}
	
public static <T extends Object> T getBeanOfType(Class<T> clazz){
		
		if(containerBuffer.containsKey(clazz)){
			return (T)getApplicationContext().getBean(containerBuffer.get(clazz));
		}
		String[] names = getApplicationContext().getBeanNamesForType(clazz);
		
		if(names != null && names.length > 0){
			containerBuffer.put(clazz, names[0]);
			return (T)getApplicationContext().getBean(names[0]);
		}throw new RuntimeException("cannot find bean of type " + clazz.getName() + " configured in any application context");
	}
	
private static Map<Class<?>, String> containerBuffer = new HashMap<Class<?>, String>();
	
	public static <T> T getBean(String beanId) {
		
	  	return (T)getApplicationContext().getBean(beanId);
	  
	}
	
	public static BeanDefinition getBeanDefinition(String beanId)
	{
		return ((XmlWebApplicationContext) getApplicationContext()).getBeanFactory().getBeanDefinition(beanId);
	}
	
	public static MessageSource getMessageSource()
	{
		return getBean("messageSource");
	}
	
	public static Locale getCurrentLocale()
	{
		return Locale.ENGLISH;
	}
	
//	public static <T extends Object> T getBeanOfType(Class<T> clazz){
//		String[] names = getApplicationContext().getBeanNamesForType(clazz);
//		
//		if(names != null && names.length > 0){
//			return (T)getApplicationContext().getBean(names[0]);
//		}throw new RuntimeException("cannot find bean of type " + clazz.getName() + " configured in any application context");
//	}

}
