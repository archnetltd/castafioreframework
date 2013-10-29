package org.castafiore.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.castafiore.ComponentNotFoundException;
import org.castafiore.Constant;
import org.castafiore.KeyValuePair;
import org.castafiore.resource.BinaryFileData;
import org.castafiore.resource.FileData;
import org.castafiore.resource.ResourceLocator;
import org.castafiore.ui.Application;
import org.castafiore.ui.CastafioreController;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.CastafioreApplicationContextHolder;
import org.castafiore.ui.engine.CastafioreEngine;
import org.castafiore.ui.engine.CastafioreFilter;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.js.JSObject;
import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ListOrderedMap;
import org.castafiore.utils.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/**/*")
public class Castafiore implements ApplicationContextAware {

	private ApplicationContext context_;

	private static ThreadLocal<ApplicationContext> currentContext = new ThreadLocal<ApplicationContext>();

	private Map<?, ?> filters = null;

	public void init() {
		if (filters == null) {
			filters = context_.getBeansOfType(CastafioreFilter.class);
		}
		if (currentContext.get() == null) {
			currentContext.set(context_);
		}
	}

	public static ApplicationContext getCurrentContext() {
		return currentContext.get();
	}

	protected void pre(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		init();
		Iterator<?> iter = filters.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			CastafioreFilter filter = (CastafioreFilter) filters.get(key);
			try {
				filter.doStart((HttpServletRequest) request);
			} catch (Exception e) {
				throw new ServletException(
						"exception occured on method doStart in CastafioreFilter "
								+ filter.getClass().getName(), e);
			}

		}
	}

	protected void error(HttpServletRequest request,
			HttpServletResponse response, Exception e) throws Exception {

		Iterator<?> iterend = filters.keySet().iterator();
		while (iterend.hasNext()) {
			String key = iterend.next().toString();
			CastafioreFilter filter = (CastafioreFilter) filters.get(key);
			try {
				filter.onException((HttpServletRequest) request, e);
			} catch (Exception ee) {

				throw new ServletException(
						"exception occured on method onException in CastafioreFilter "
								+ filter.getClass().getName(), e);
			}
		}
		throw new ServletException(e);
	}

	protected void post(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Iterator<?> iterend = filters.keySet().iterator();
		while (iterend.hasNext()) {
			String key = iterend.next().toString();
			CastafioreFilter filter = (CastafioreFilter) filters.get(key);
			try {
				filter.doEnd((HttpServletRequest) request);
			} catch (Exception e) {
				throw new ServletException(
						"exception occured on method doEnd in CastafioreFilter "
								+ filter.getClass().getName(), e);
			} finally {

			}
		}
		currentContext.set(null);
		CastafioreApplicationContextHolder.resetApplicationContext();
	}

	@RequestMapping("/ui")
	public void doServlet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			pre(request, response);
			doService(request, response);
		} catch (Exception e) {
			error(request, response, e);
		} finally {
			post(request, response);
		}

	}

	@RequestMapping("resource/{spec}/**/*")
	public void doResource(@PathVariable("spec") String spec,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pre(request, response);
		try {
			
			ResourceLocator locator = (ResourceLocator) context_
					.getBean("resourcelocator_" + spec);
			String url = request.getRequestURL().toString();

			String res = StringUtil.splitByWholeSeparator(url, spec)[1];
			
			FileData f = locator.getResource(spec + ":" + res, "");
			OutputStream os = response.getOutputStream();
			response.setContentType(f.getMimeType());
			((HttpServletResponse) response).setHeader("Content-Disposition",
					"filename=" + f.getName());
			ChannelUtil.TransferData(f.getInputStream(), os);
			os.flush();
		} catch (Exception e) {
			String url = request.getRequestURL().toString();
			System.err.println(url);
			error(request, response, e);
		} finally {
			post(request, response);
		}
	}

	@RequestMapping("methods/{application}/{componentid}")
	public void doMethod(@PathVariable("application") String applicationid,
			@PathVariable("componentid") String componentid,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pre(request, response);
		String methodName = request.getParameter("method");
		String param = request.getParameter("paramName") != null ? request
				.getParameter(request.getParameter("paramName")) : null;
		try {
			CastafioreApplicationContextHolder.getCurrentApplication();
			Application applicationInstance = (Application) ((HttpServletRequest) request)
					.getSession().getAttribute(applicationid);
			if (applicationInstance == null) {
				applicationInstance = (Application) context_
						.getBean(applicationid);
			}
			Container c = ComponentUtil.getContainerById(applicationInstance,
					componentid);

			if (c instanceof CastafioreController) {
				((CastafioreController) c).handleRequest(request, response);
				return;
			}

			Object o = c.getClass().getMethod(methodName, String.class)
					.invoke(c, param);
			if (o != null) {
				if (o instanceof InputStream) {
					ChannelUtil.TransferData((InputStream) o,
							response.getOutputStream());
					response.getOutputStream().flush();
				} else if (o instanceof JSObject) {
					response.getOutputStream().write(
							((JSObject) o).getJavascript().getBytes());
					response.getOutputStream().flush();
				} else {
					response.getOutputStream().write(o.toString().getBytes());
					response.getOutputStream().flush();
				}

				((HttpServletResponse) response).setHeader(
						"Content-Disposition",
						"filename=" + methodName.replace("_", "."));
			}

		} catch (Exception e) {
			error(request, response, e);
		} finally {
			post(request, response);
		}
	}

	private Map<String, String> getParameterMap(Map<?, ?> parameters) {

		Map<String, String> result = new HashMap<String, String>(
				parameters.size());
		Iterator<?> iter = parameters.keySet().iterator();

		while (iter.hasNext()) {
			String key = iter.next().toString();

			String value = ((String[]) parameters.get(key))[0].toString();

			result.put(key, value);
		}

		return result;
	}

	/**
	 * Instantiate a new instance of FileData to hold uploaded data
	 * 
	 * @return
	 */
	private FileData getNewInstance() {
		try {
			Class<?> cls = Thread.currentThread().getContextClassLoader()
					.loadClass("org.castafiore.wfs.types.BinaryFile");
			return (FileData) cls.newInstance();
		} catch (ClassNotFoundException e) {
			return new BinaryFileData();
		} catch (Exception e) {
			return new BinaryFileData();
		}
	}

	protected void doGeter(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		FileUploadListener listener = null;
		StringBuffer buffy = new StringBuffer();
		long bytesRead = 0, contentLength = 0;

		// Make sure the session has started
		if (session == null) {
			return;
		} else if (session != null) {
			// Check to see if we've created the listener object yet
			listener = (FileUploadListener) session.getAttribute("LISTENER");

			if (listener == null) {
				return;
			} else {
				// Get the meta information
				bytesRead = listener.getBytesRead();
				contentLength = listener.getContentLength();
			}
		}

		response.setContentType("text/xml");

		buffy.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		buffy.append("<response>\n");
		buffy.append("\t<bytes_read>" + bytesRead + "</bytes_read>\n");
		buffy.append("\t<content_length>" + contentLength
				+ "</content_length>\n");

		// Check to see if we're done
		if (bytesRead == contentLength) {
			buffy.append("\t<finished />\n");

			// No reason to keep listener in session since we're done
			session.setAttribute("LISTENER", null);
		} else {
			// Calculate the percent complete
			long percentComplete = ((100 * bytesRead) / contentLength);

			buffy.append("\t<percent_complete>" + percentComplete
					+ "</percent_complete>\n");
		}

		buffy.append("</response>\n");

		out.println(buffy.toString());
		out.flush();
		out.close();
	}

	/**
	 * handles upload made by EXUpload component
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 */
	private void handleMultipartRequest(HttpServletRequest request,
			ServletResponse response) throws ServletException {
		// logger.debug("handling multipart request. A file is being uploaded....");
		try {
			Map<?, ?> pro = (Map<?, ?>) context_.getBean("uploadprops");

			ServletFileUpload upload = new ServletFileUpload(
					new DiskFileItemFactory(1024, new File(pro.get(
							"repository.dir").toString())));

			FileUploadListener listener = new FileUploadListener();

			HttpSession session = request.getSession();

			session.setAttribute("LISTENER", listener);

			upload.setProgressListener(listener);

			Iterator<?> iter = upload.parseRequest(request).iterator();
			String applicationId = null;
			String componentId = null;
			EXUpload exUpload = null;
			Application applicationInstance = null;
			List<FileData> ds = new ArrayList<FileData>();

			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					if (item.getFieldName().equals("casta_applicationid")) {
						applicationId = IOUtil.getStreamContentAsString(item
								.getInputStream());
						applicationInstance = (Application) request
								.getSession().getAttribute(applicationId);
					} else if (item.getFieldName().equalsIgnoreCase(
							"casta_componentid")) {
						componentId = IOUtil.getStreamContentAsString(item
								.getInputStream());
					}
				} else {
					FileData bFile = null;
					String name = item.getName();
					File savedFile = new File(pro.get("upload.dir") + "/"
							+ new Date().getTime() + "_" + item.getName()); // new
																			// File(request.getRealPath("/")+"uploadedFiles/"+name);

					item.write(savedFile);

					bFile = getNewInstance();
					bFile.setUrl(savedFile.getAbsolutePath());
					bFile.setName(item.getName());
					bFile.setName(name);
					ds.add(bFile);
				}
			}

			exUpload = (EXUpload) applicationInstance
					.getDescendentById(componentId);
			for (FileData f : ds) {
				exUpload.addFile(f);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private CastafioreEngine getEngine(HttpServletRequest req) {
		CastafioreEngine engine = (CastafioreEngine) req.getSession()
				.getAttribute("CastafioreEngine");
		if (engine == null) {
			engine = new CastafioreEngine();
			req.getSession().setAttribute("CastafioreEngine", engine);
			return (CastafioreEngine) req.getSession().getAttribute(
					"CastafioreEngine");
		} else {
			return engine;
		}
	}

	public void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean isMultipart = ServletFileUpload
				.isMultipartContent((HttpServletRequest) request);
		ComponentUtil.loadApplication((HttpServletRequest) request,
				(HttpServletResponse) response, context_);

		if (isMultipart) {
			handleMultipartRequest((HttpServletRequest) request, response);
		} else if ("true".equals(request.getParameter("upload"))) {
			doGeter(request, response);

		} else {
			request.setCharacterEncoding("UTF-8");

			Map<String, String> params = this.getParameterMap(request
					.getParameterMap());

			String componentId = request.getParameter("casta_componentid");
			String eventId = request.getParameter("casta_eventid");
			String applicationId = request.getParameter("casta_applicationid");

			Assert.notNull(
					applicationId,
					"cannot execute a castafiore request when the applicationid is null. Please verify that the parameter casta_applicationid has been set correctly in tag, jsp or whatever");

			Application applicationInstance = CastafioreApplicationContextHolder
					.getCurrentApplication();
			String script = "";

			if ((componentId == null && eventId == null && applicationInstance != null)) {
				applicationInstance.setRendered(false);

				script = getEngine(request).getJQuery(applicationInstance,
						"root_" + applicationId, applicationInstance,
						new ListOrderedMap<String, List<KeyValuePair>>());
				// script = script + "hideloading();";

			} else if ((componentId != null && eventId != null)
					&& applicationInstance != null) {
				try {
					script = getEngine(request).executeServerAction(
							componentId, applicationInstance,
							"root_" + applicationId, params);

					// script = script + "hideloading();";

				} catch (ComponentNotFoundException cnfe) {

					script = "window.location.reload( false );";
				}
			} else if ((componentId != null && eventId != null)
					&& applicationInstance == null) {

				script = "alert('Your session has expired. Browser will refresh');window.location.reload( false );";
			}

			Set<String> requiredScript = applicationInstance
					.getBufferedResources();
			if (requiredScript != null && requiredScript.size() > 0) {
				StringBuilder reqScript = new StringBuilder();
				reqScript.append(
						Constant.NO_CONFLICT + ".plugin('"
								+ applicationInstance.getId() + "',{").append(
						"files:[");
				int scount = 0;
				for (String s : requiredScript) {
					reqScript.append("'").append(s).append("'");
					scount++;
					if (scount < requiredScript.size()) {
						reqScript.append(",");
					}
				}
				reqScript.append("],").append(
						"selectors:['" + Constant.ID_PREF + "root_"
								+ applicationId + "'],");
				reqScript.append("callback:function(){").append(script)
						.append("}").append("});");
				reqScript.append(Constant.NO_CONFLICT + ".plugin('"
						+ applicationInstance.getId() + "').get();");
				script = reqScript.toString();
			}
			applicationInstance.flush(12031980);

			script = "<script>" + Constant.NO_CONFLICT
					+ "(document).ready(function(){" + script + "});</script>";

			response.getOutputStream().write(script.getBytes());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context_ = applicationContext;

	}

}
