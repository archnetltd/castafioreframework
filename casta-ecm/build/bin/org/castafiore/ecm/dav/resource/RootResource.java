package org.castafiore.ecm.dav.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.ecm.dav.CastafioreWebDAVResourceFactory;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.CollectionResource;
import com.bradmcevoy.http.DigestResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.MakeCollectionableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.PutableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.ConflictException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;
import com.bradmcevoy.http.exceptions.NotFoundException;
import com.bradmcevoy.http.http11.auth.DigestResponse;


public class RootResource implements PropFindableResource, CollectionResource, PutableResource, MakeCollectionableResource, GetableResource{
	
	private RepositoryService repositoryService;
	
	String path ;
	
	private String name;

	public RootResource(RepositoryService repositoryService, String path, String name) {
		super();
		this.repositoryService = repositoryService;
		this.path = path;
		
		this.name = name;
	}

	@Override
	public Date getCreateDate() {
		// Unknown
		return null;
	}

	@Override
	public Object authenticate(String user, String pwd) {
		try{
			boolean b = SpringUtil.getSecurityService().login(user, pwd);
			if(b){
				return user;
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public boolean authorise(Request request, Method method, Auth auth) {
		return true;
	}

	@Override
	public String checkRedirect(Request arg0) {
		// No redirects
		return "";
	}

	@Override
	public Date getModifiedDate() {
		// Unknown
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getRealm() {
		return CastafioreWebDAVResourceFactory.REALM;
	}

	@Override
	public String getUniqueId() {
		return null;
	}

	@Override
	public Resource child(String name) {
		return null;
	}

	@Override
	public List<? extends Resource> getChildren() {
		List<Resource> result = new ArrayList<Resource>();
		List<File> files = repositoryService.getDirectory(path, "").getFiles().toList();
		for(File f : files){
			if(f instanceof Directory && !(f instanceof BinaryFile)){
				result.add(new RootResource(repositoryService, f.getAbsolutePath(), f.getName()));
			}else if(f instanceof BinaryFile){
				result.add(new BinaryFileResource(f.getAbsolutePath(), repositoryService));
			}
		}
		
		return result;
	}

	@Override
	public Resource createNew(String newName, InputStream inputStream,Long length, String contentType) throws IOException,ConflictException, NotAuthorizedException, BadRequestException {
		try{
		
			if(!repositoryService.itemExists(path + "/" + newName)){
				Directory dir = repositoryService.getDirectory(path, "");
				BinaryFile bf = dir.createFile(newName, BinaryFile.class);
				OutputStream out = bf.getOutputStream();
				byte[] bytes = IOUtil.getStreamContentAsBytes(inputStream);
				out.write(bytes);
				bf.setSize(bytes.length);
				out.flush();
				out.close();
				dir.save();
				
				SpringUtil.getBeanOfType(Dao.class).closeSession();
				
				return new BinaryFileResource(bf.getAbsolutePath(), repositoryService);
				}else{
					BinaryFile ne = (BinaryFile)repositoryService.getFile(path + "/" + newName, "");
					OutputStream out = ne.getOutputStream();
					out.write(IOUtil.getStreamContentAsBytes(inputStream));
					out.flush();
					out.close();
					return new BinaryFileResource(ne.getAbsolutePath(), repositoryService);
				}
			}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public CollectionResource createCollection(String newName)
			throws NotAuthorizedException, ConflictException,
			BadRequestException {
		Directory dir = repositoryService.getDirectory(path, "");
		Directory bf = dir.createFile(newName, Directory.class);
		dir.save();
		
		SpringUtil.getBeanOfType(Dao.class).closeSession();
		
		return new RootResource(repositoryService, bf.getAbsolutePath(), newName);
	}

	@Override
	public Long getContentLength() {
		
		return new Long(getString().length());
		
	}
	
	
	private String getString(){
		Directory dir = repositoryService.getDirectory(path, "");
		StringBuilder b = new StringBuilder();
		for(File f : dir.getFiles().toList()){
			b.append("<a href='/"+f.getAbsolutePath().replace("/root/users/", "")+"'>").append(f.getName()).append("</a></br>");
		}
		
		return b.toString();
	}

	@Override
	public String getContentType(String accepts) {
		return "text/html";
	}

	@Override
	public Long getMaxAgeSeconds(Auth auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendContent(OutputStream out, Range range,
			Map<String, String> params, String contentType) throws IOException,
			NotAuthorizedException, BadRequestException, NotFoundException {
		out.write(getString().getBytes());
		
	}	

}
