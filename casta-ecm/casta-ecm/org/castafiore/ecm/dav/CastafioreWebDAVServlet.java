package org.castafiore.ecm.dav;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.bradmcevoy.http.MiltonServlet;

public class CastafioreWebDAVServlet extends MiltonServlet{
	
	@Override
    public void init(final ServletConfig config ) throws ServletException {
		ServletConfig config1 = new ServletConfig(){

			@Override
			public String getInitParameter(String p) {
				if(p.equalsIgnoreCase("authentication.handler.classes")){
					return "com.bradmcevoy.http.http11.auth.BasicAuthHandler";
				}else if(p.equalsIgnoreCase("resource.factory.factory.class")){
					return "org.castafiore.ecm.dav.CastafioreWebDabResourceResourceFactory";
				}else if(p.equalsIgnoreCase("not.found.url")){
					return "/404.jsp";
				}else if(p.equals("filter_0")){
					return "com.bradmcevoy.http.DebugFilter";
				}else{
					return config.getInitParameter(p);
				}
			}

			@Override
			public Enumeration getInitParameterNames() {
				Vector m = new Vector();
				
				Enumeration en = config.getInitParameterNames();
				while(en.hasMoreElements()){
					Object n = en.nextElement();
					m.add(n);
				}
				m.add("authentication.handler.classes");
				m.add("resource.factory.factory.class");
				m.add("not.found.url");
				m.add("filter_0");
				
				return m.elements();
			}

			@Override
			public ServletContext getServletContext() {
				return config.getServletContext();
			}

			@Override
			public String getServletName() {
				return config.getServletName();
			}
			
		};
       super.init(config1);
    }


}
