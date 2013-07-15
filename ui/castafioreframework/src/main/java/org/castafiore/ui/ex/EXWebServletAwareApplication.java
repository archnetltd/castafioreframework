/*
 * 
 */
package org.castafiore.ui.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.WebServletAwareApplication;

/**
 * More specialization of {@link EXApplication} exposing Servlet API to developer.
 * This convenient implementation should be used only of there is need to directly access the Servlet API. This creates a direct dependency of the API in application code
 * @author arossaye
 *
 */
public abstract class EXWebServletAwareApplication extends EXApplication implements WebServletAwareApplication{

	public EXWebServletAwareApplication(String name) {
		super(name);
	}

	private HttpServletRequest request_;
	
	private HttpServletResponse response_;
	
	@Override
	public void setRequest(HttpServletRequest request) {
		this.request_ = request;
		
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response_ = response;
		
	}
	
	
	public HttpServletRequest getRequest(){
		return this.request_;
	}
	
	
	public HttpServletResponse getResponse(){
		return this.response_;
	}

}
