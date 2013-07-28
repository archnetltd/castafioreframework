package org.castafiore.designer.dataenvironment;

import java.util.List;

import org.castafiore.Types;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.ScrollableFileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;

public class EasyConfigFileIteratorDatasource extends AbstractFileIteratorDatasource{
	
	public EasyConfigFileIteratorDatasource(String name, String factoryId) {
		super(name, factoryId);
	}

	private QueryParameters buildParameters(){
		String factoryId = getFactoryId();
		DatasourceFactory factory = SpringUtil.getBeanOfType(DatasourceFactoryService.class).getDatasourceFactory(factoryId);
		QueryParameters params = new QueryParameters();
		for(String attributeName : factory.getRequiredAttributes()){
			String attributeValue = getAttribute(attributeName);
			if(attributeName.equalsIgnoreCase("Directories")){
				
				if(StringUtil.isNotEmpty(attributeValue)){
					String[] as = StringUtil.split(attributeValue, ";");
					for(String s : as){
						params.addSearchDir(s);
					}
				}
			}else if(attributeName.equalsIgnoreCase("Type")){
				if(StringUtil.isNotEmpty(attributeValue)){
					try{
						Class c = Thread.currentThread().getContextClassLoader().loadClass(attributeValue);
						params.setEntity(c);
					}catch(Exception e){
						//throw new UIException(e);
						log = log + "<br>" + e.getMessage();
					}
				}else{
					params.setEntity(FileImpl.class);
				}
				
			}else if(attributeName.equalsIgnoreCase("SQL")){
				if(StringUtil.isNotEmpty(attributeValue))
					params.addRestriction(Restrictions.sqlRestriction(attributeValue));
				
			}
			
		}
		
		return params;
	}

	@Override
	public List<File> doLoadData() {
		QueryParameters params = buildParameters();
		return SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
	}

	@Override
	public Class getEntity() {
		if(StringUtil.isNotEmpty(getAttribute("Type"))){
			try{
				Class c = Thread.currentThread().getContextClassLoader().loadClass(getAttribute("Type"));
				return c;
			}catch(Exception e){
				log = log + "<br>" + e.getMessage();
				return FileImpl.class;
			}
		}else{
			return FileImpl.class;
		}
	}
	
	
//	@Override
//	public Datasource doOpen() {
//		
//		
//		int pageSize = 10;
//		
//		try{
//			pageSize = Integer.parseInt(getAttribute("PageSize"));
//		}catch(Exception e){
//			
//		}
//		
//		fileIterator = new ScrollableFileIterator(pageSize,buildParameters(),Util.getRemoteUser());
//		return this;
//	}
//
//
//
//
//	@Override
//	public ClassMetadata loadMetadata() {
//		Class entity = buildParameters().getEntity();
//		return SpringUtil.getBeanOfType(Dao.class).getSession().getSessionFactory().getClassMetadata(entity);
//		
//	}


	


	

}
