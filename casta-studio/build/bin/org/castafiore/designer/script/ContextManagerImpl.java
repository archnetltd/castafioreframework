package org.castafiore.designer.script;

import groovy.lang.Binding;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.Studio;
import org.castafiore.ui.Container;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextManagerImpl implements ContextManager, ApplicationContextAware{
	
	private ApplicationContext context;
	
	private Binding binding; 

	@Override
	public Binding getCurrentContext(Container component) {
		if(binding == null){
			final Map variable = new HashMap();
			
			variable.put("me", component);
			variable.put("repository", context.getBean("repositoryService"));
			
			variable.put("kernel",context );
			variable.put("studio", new Studio());
			
			PortalContainer pc = component.getAncestorOfType(PortalContainer.class);
			
			ComponentUtil.iterateOverDescendentsOfType(pc, Container.class,new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					if(StringUtil.isNotEmpty(c.getAttribute("des-id"))){
						variable.put(c.getName(), c);
					}
					
				}
			});
			binding = new Binding(variable);
		}
		binding.setVariable("studio", new Studio());
		binding.setVariable("me", component);
		binding.setVariable("root", component.getRoot());
		binding.setVariable("component", component);
		return binding;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
		
	}

}
