package org.castafiore.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebServletAwareApplication extends Application {
	
	public void setRequest(HttpServletRequest request);
	
	public void setResponse(HttpServletResponse response);
	
	

}
