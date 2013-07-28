package org.castafiore.designer.macro;

import java.util.Collection;
import java.util.List;

import org.castafiore.beans.info.BeanInfoService;
import org.castafiore.spring.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MacroServiceImpl implements MacroService, ApplicationContextAware{
	
	private BeanInfoService beanInfoService;
	
	private ApplicationContext context_;

	public BeanInfoService getBeanInfoService() {
		return beanInfoService;
	}

	public void setBeanInfoService(BeanInfoService beanInfoService) {
		this.beanInfoService = beanInfoService;
	}

	@Override
	public EventMacro getMacro(String eventId) {
		return beanInfoService.getBeanOfType(EventMacro.class, eventId);
	}

	@Override
	public Collection<EventMacro> getMacros() {
		return context_.getBeansOfType(EventMacro.class).values();
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context_ = arg0;
		
	}
	
	
	

}
