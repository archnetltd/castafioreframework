package org.castafiore.ecm.dav;

import org.castafiore.spring.SpringUtil;

import com.bradmcevoy.http.AuthenticationService;
import com.bradmcevoy.http.ResourceFactory;
import com.bradmcevoy.http.ResourceFactoryFactory;
import com.bradmcevoy.http.webdav.DefaultWebDavResponseHandler;
import com.bradmcevoy.http.webdav.WebDavResponseHandler;

public class CastafioreWebDabResourceResourceFactory implements ResourceFactoryFactory {

	private static AuthenticationService authenticationService;
	
	private ResourceFactory resourceFactory;
	
	@Override
	public ResourceFactory createResourceFactory() {
		if(resourceFactory == null){
			resourceFactory = new CastafioreWebDAVResourceFactory(SpringUtil.getRepositoryService());
		}
		return resourceFactory;
	}

	@Override
	public WebDavResponseHandler createResponseHandler() {
		return new DefaultWebDavResponseHandler(authenticationService);
	}

	@Override
	public void init() {
		
		if( authenticationService == null ) {
			authenticationService = new AuthenticationService(); 
		}
	}

}
