package org.castafiore.workflow.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.workflow.State;
import org.castafiore.workflow.Workflow;
import org.castafiore.workflow.WorkflowManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WorkflowManagerImpl implements WorkflowManager {
	
	private static ApplicationContext context;
	
	static{
		context = new ClassPathXmlApplicationContext("workflow.xml");
	}

	

	public Workflow getWorkflow(String name) {
		Map<String, Workflow> beans = context.getBeansOfType(Workflow.class);
		String[] result = new String[beans.keySet().size()];
		Iterator<String> keys = beans.keySet().iterator();
		
		while(keys.hasNext()){
			String key = keys.next();
			
			Workflow work = beans.get(key);
			if(work.getName().equals(name)){
				return work;
			}
			
		}
		return null;
	}

	public String[] getWorkflowNames() {
		Map<String, Workflow> beans = context.getBeansOfType(Workflow.class);
		String[] result = new String[beans.keySet().size()];
		Iterator<String> keys = beans.keySet().iterator();
		int count = 0;
		while(keys.hasNext()){
			result[count]= beans.get(keys.next()).getName();
			count++;
		}
		return result;
	}

}
