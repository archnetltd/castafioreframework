package org.castafiore.notifications;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.security.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class NotificationManagerImpl implements NotificationManager, ApplicationContextAware{


	private ApplicationContext context_;
	
	
	public List<NotificationAgent> getAgents() {
		
		Map<String, NotificationAgent> agents = context_.getBeansOfType(NotificationAgent.class);
		List<NotificationAgent> lstAgents = new ArrayList<NotificationAgent>();
		Iterator<String> names = agents.keySet().iterator();
		while(names.hasNext()){
			NotificationAgent agent = agents.get(names.next());
			lstAgents.add(agent);
		}
		
		return lstAgents;
		
	}

	public List<NotificationAgent> getAgents(User user) {
		Map<String, NotificationAgent> agents = context_.getBeansOfType(NotificationAgent.class);
		List<NotificationAgent> lstAgents = new ArrayList<NotificationAgent>();
		Iterator<String> names = agents.keySet().iterator();
		while(names.hasNext()){
			NotificationAgent agent = agents.get(names.next());
			if(agent.isUserRegistered(user))
				lstAgents.add(agent);
		}
		return lstAgents;
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context_ = context;
		
	}

}
