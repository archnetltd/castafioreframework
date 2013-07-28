package org.castafiore.spring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Starter implements Serializable, ApplicationContextAware, Comparator<Startable>{

	private ApplicationContext applicationContext ;
	
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
	public void executeStart(){
		
		Map<String, Startable> startables = applicationContext.getBeansOfType(Startable.class);
		
		Collection<Startable> colStartable = startables.values();
		
		List<Startable> lst = new ArrayList<Startable>(colStartable);
		
		Collections.sort(lst, this);
		//List<Startable> arrat
		
		for(Startable startable : lst){
			startable.start();
		}
		
	}



	public int compare(Startable o1, Startable o2) {
		return o1.getPriority().compareTo(o2.getPriority());
	}
	
	
	

}
