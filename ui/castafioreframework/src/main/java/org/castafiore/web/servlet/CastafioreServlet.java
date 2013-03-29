package org.castafiore.web.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CastafioreServlet extends DispatcherServlet{

	public CastafioreServlet() {
		super();
		super.setDetectAllHandlerMappings(false);
		super.setDetectAllHandlerAdapters(false);
		super.setDetectAllViewResolvers(false);
		super.setDetectAllHandlerExceptionResolvers(false);
		setContextConfigLocation("classpath:configs/**/*-config.xml");
	}

	public CastafioreServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
		super.setDetectAllHandlerMappings(false);
		super.setDetectAllHandlerAdapters(false);
		super.setDetectAllViewResolvers(false);
		super.setDetectAllHandlerExceptionResolvers(false);
		setContextConfigLocation("classpath:configs/**/*-config.xml");
	}
	
}
