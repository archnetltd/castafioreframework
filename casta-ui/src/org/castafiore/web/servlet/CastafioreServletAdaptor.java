package org.castafiore.web.servlet;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.Constant;
import org.castafiore.ui.Application;
import org.castafiore.ui.engine.CastafioreEngine;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.springframework.util.Assert;

public class CastafioreServletAdaptor {
	protected final static Log logger = LogFactory.getLog(AbstractCastafioreServlet.class);

	private static CastafioreEngine getEngine(HttpServletRequest req) {
		CastafioreEngine engine = (CastafioreEngine) req.getSession().getAttribute("CastafioreEngine");
		if (engine == null) {
			engine = new CastafioreEngine();
			req.getSession().setAttribute("CastafioreEngine", engine);
			return (CastafioreEngine) req.getSession().getAttribute("CastafioreEngine");
		} else {
			return engine;
		}
	}

	public static String doAdapt(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//BaseSpringUtil.init(request);
		
		ComponentUtil.loadApplication((HttpServletRequest) request,(HttpServletResponse) response);

		if (logger.isDebugEnabled())
			logger.debug("handling normal action request");
		
		request.setCharacterEncoding("UTF-8");

		String applicationId = request.getParameter("casta_applicationid");

		Assert.notNull(applicationId,"cannot execute a castafiore request when the applicationid is null. Please verify that the parameter casta_applicationid has been set correctly in tag, jsp or whatever");

		// gets the already loaded application
		Application applicationInstance = CastafioreApplicationContextHolder.getCurrentApplication();
		String script = "";

		if ( applicationInstance != null) {
			// first access to the application. componentId and EventId should
			// be null
			if (logger.isDebugEnabled())
				logger.debug("re-rendering the application");
			applicationInstance.setRendered(false);
			if (logger.isDebugEnabled())
				logger.debug("re-generate jquery for application");
			script = getEngine(request).getJQuery(applicationInstance,
					"root_" + applicationId, applicationInstance,
					new ListOrderedMap());
			script = script + "hideloading();";

		} 

		Set<String> requiredScript = applicationInstance.getBufferedResources();
		if (requiredScript != null && requiredScript.size() > 0) {
			StringBuilder reqScript = new StringBuilder();
			reqScript.append(Constant.NO_CONFLICT + ".plugin('"+ applicationInstance.getId() + "',{").append("files:[");
			int scount = 0;
			for (String s : requiredScript) {
				reqScript.append("'").append(s).append("'");
				scount++;
				if (scount < requiredScript.size()) {
					reqScript.append(",");
				}
			}
			reqScript.append("],").append("selectors:['" + Constant.ID_PREF + "root_" + applicationId+ "'],");
			reqScript.append("callback:function(){").append(script).append("}").append("});");
			reqScript.append(Constant.NO_CONFLICT + ".plugin('"+ applicationInstance.getId() + "').get();");
			script = reqScript.toString();
		}
		if (logger.isDebugEnabled())
			logger.debug(script);
		// System.out.println(script);
		applicationInstance.flush(12031980);

		script = "<script>" + Constant.NO_CONFLICT	+ "(document).ready(function(){" + script + "});</script>";
		return script;

	}

}
