/*
 * 
 */
package org.castafiore.ui.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.WebServletAwareApplication;

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
