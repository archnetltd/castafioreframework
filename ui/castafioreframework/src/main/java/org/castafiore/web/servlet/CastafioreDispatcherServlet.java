package org.castafiore.web.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CastafioreDispatcherServlet extends DispatcherServlet{

	public CastafioreDispatcherServlet() {
		super();
		super.setDetectAllHandlerMappings(false);
		super.setDetectAllHandlerAdapters(false);
		super.setDetectAllViewResolvers(false);
		super.setDetectAllHandlerExceptionResolvers(false);
	}

	public CastafioreDispatcherServlet(
			WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
		// TODO Auto-generated constructor stub
	}
	
}
