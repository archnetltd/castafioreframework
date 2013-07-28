package org.castafiore.ecm.dav.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.DeletableResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.ConflictException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;
import com.bradmcevoy.http.exceptions.NotFoundException;

public class BinaryFileResource implements GetableResource, PropFindableResource, DeletableResource{

	private BinaryFile bf = null;
	
	private String path;
	
	private RepositoryService repositoryService;
	
	private void init(){
		if(bf == null){
			bf = (BinaryFile)repositoryService.getFile(path, "");
		}
	}
	
	
	public BinaryFileResource(String path, RepositoryService repositoryService) {
		super();
		this.path = path;
		this.repositoryService = repositoryService;
		init();
		
	}


	@Override
	public Long getContentLength() {
		init();
		try{
			
				byte[] bytes =IOUtil.getStreamContentAsBytes(bf.getInputStream());
				return new Long(bytes.length);
				
			
			//return bf.getInputStream().
			
			
			
			}catch(Exception e){
				e.printStackTrace();
		}
			return 0L;
	}

	@Override
	public String getContentType(String arg0) {
		init();
		return bf.getMimeType();
	}

	@Override
	public Long getMaxAgeSeconds(Auth arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendContent(OutputStream out, Range arg1,Map<String, String> arg2, String arg3) throws IOException,
			NotAuthorizedException, BadRequestException, NotFoundException {
		init();
		try{
		byte[] bytes =IOUtil.getStreamContentAsBytes(bf.getInputStream());
		
		out.write(bytes);
		out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Object authenticate(String user, String password) {
		return user;
	}

	@Override
	public boolean authorise(Request arg0, Method arg1, Auth arg2) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String checkRedirect(Request arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getModifiedDate() {
		init();
		return bf.getLastModified().getTime();
	}

	@Override
	public String getName() {
		init();
		// TODO Auto-generated method stub
		return bf.getName();
	}

	@Override
	public String getRealm() {
		return "MyCompany";
	}

	@Override
	public String getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Date getCreateDate() {
		init();
		return bf.getDateCreated().getTime();
	}


	@Override
	public void delete() throws NotAuthorizedException, ConflictException,
			BadRequestException {
		
		BinaryFile bf = (BinaryFile)repositoryService.getFile(path, "");
		Directory parent = bf.getParent();
		bf.remove();
		parent.save();
	}
}
