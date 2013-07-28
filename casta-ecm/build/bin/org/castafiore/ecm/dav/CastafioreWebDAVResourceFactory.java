package org.castafiore.ecm.dav;


import org.castafiore.ecm.dav.resource.BinaryFileResource;
import org.castafiore.ecm.dav.resource.RootResource;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

import com.bradmcevoy.common.Path;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.ResourceFactory;


public class CastafioreWebDAVResourceFactory implements ResourceFactory{
	
	public static final String REALM = "MyCompany";
	
	private RepositoryService repositoryService;
	
	
	

	public CastafioreWebDAVResourceFactory(RepositoryService repositoryService) {
		super();
		this.repositoryService = repositoryService;
	}

	@Override
	public Resource getResource(String host, String p) {		
		Path path = Path.path(p).getStripFirst().getStripFirst();
		
		if( path.isRoot() ) {
			return null;
		}  else {
			try{
			String dir = "/root/users" + path.toString();
			File f = repositoryService.getFile(dir, "");
			if(f instanceof BinaryFile){
				return new BinaryFileResource(dir, repositoryService);
			}
			return new RootResource(repositoryService,"/root/users" + path.toString(), path.getParts()[path.getLength()-1]);
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			
		}
	}
	
	
	
	
	
	
	
	
}
