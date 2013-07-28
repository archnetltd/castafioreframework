package org.castafiore.shoppingmall;

import java.util.LinkedHashMap;
import java.util.Map;

import org.castafiore.wfs.service.RepositoryService;

public class ShoppingMallManagerImpl implements ShoppingMallManager{
	
	private Map<String, ShoppingMall> malls = new LinkedHashMap<String, ShoppingMall>();
	
	
	private RepositoryService repositoryService;


	@Override
	public ShoppingMall getDefaultMall() {
		return getMall(ShoppingMall.ROOT);
	}

	@Override
	public ShoppingMall getMall(String dir) {
		if(!malls.containsKey(dir)){
			ShoppingMallImpl mall = new ShoppingMallImpl(repositoryService, dir);
			mall.checkAndInit();
			malls.put(dir, mall);
		}
		return malls.get(dir);
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
	

}
