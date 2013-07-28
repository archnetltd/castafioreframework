package org.castafiore.designer.dataenvironment;

import org.castafiore.beans.info.BeanInfoService;
import org.castafiore.designable.InvalidDesignableException;

public class DatasourceFactoryServiceImpl implements DatasourceFactoryService{

	
	private BeanInfoService beanInfoService;
	
	
	@Override
	public DatasourceFactory getDatasourceFactory(String factoryId) {
		DatasourceFactory result = beanInfoService.getBeanOfType(DatasourceFactory.class, factoryId);
		
		if(result == null)
			throw new InvalidDesignableException("The unique Datasource factory " + factoryId + " cannot be found. Probably it was not configured");
		else
			return result;
	}


	public BeanInfoService getBeanInfoService() {
		return beanInfoService;
	}


	public void setBeanInfoService(BeanInfoService beanInfoService) {
		this.beanInfoService = beanInfoService;
	}
	
	
	

}
