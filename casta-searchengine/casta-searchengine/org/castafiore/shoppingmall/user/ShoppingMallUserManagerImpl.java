package org.castafiore.shoppingmall.user;

import java.util.Map;

import org.castafiore.security.api.SecurityService;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;

public class ShoppingMallUserManagerImpl implements ShoppingMallUserManager{

	private RepositoryService repositoryService;
	
	private SecurityService securityService;
	
	
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}


	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}


	public SecurityService getSecurityService() {
		return securityService;
	}


	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}


	@Override
	public ShoppingMallUser getCurrentUser() {
		return getUser(Util.getRemoteUser()); 
	}
	
	
	public ShoppingMallUser getUser(String username){
		return new ShoppingMallUserImpl(repositoryService, securityService.loadUserByUsername(username));
	}

	@Override
	public ShoppingMallUser login(String username, String password)throws Exception {
		
			if(securityService.login(username, password))
				return new ShoppingMallUserImpl(repositoryService, securityService.loadUserByUsername(username));
			else
				return null;
		
		
		
	}

	@Override
	public ShoppingMallUser registerUser(Map<String, String> info) {
		
		return null;
	}

}
