/*
 * 
 */
package org.castafiore.ui;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Interface that makes an application aware of {@link HttpServlet} request and
 * response.<br>
 * This is suitable to be able to get parameters, access session or cookies or
 * whatever<br>
 * However, it creates a dependancy over the Servlet API making your application
 * workable only in the web context<br>
 * In most cases it is not necessary to use this interface.
 * 
 * @author Kureem Rossaye
 * @since 1.0
 */
public interface WebServletAwareApplication extends Application {

	public void setRequest(HttpServletRequest request);

	public void setResponse(HttpServletResponse response);

}
