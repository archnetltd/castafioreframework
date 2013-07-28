package org.castafiore.google.docs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.persistence.Entity;

import org.castafiore.wfs.cloud.CloudResource;
import org.castafiore.wfs.cloud.EditableCloudResource;
import org.castafiore.wfs.cloud.ReadableCloudResource;
import org.castafiore.wfs.cloud.WritableCloudResource;
import org.castafiore.wfs.types.Article;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.util.ServiceException;

@Entity
public class GoogleDoc extends Article implements CloudResource, EditableCloudResource, WritableCloudResource, ReadableCloudResource{
	

	private transient DocumentListEntry entry_;
	
	@Type(type="text")
	private String googleDocType;

	@Type(type="text")
	private String resourceId;

	public String getResourceId() {
		return resourceId;
	}
	public void setAbsolutePath(String path){
		super.setAbsolutePath(path);
	}
	public GoogleDoc(String name){
		super();
		setName(name);
		
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	


	public String getGoogleDocType() {
		return googleDocType;
	}

	public void setGoogleDocType(String googleDocType) {
		this.googleDocType = googleDocType;
	}

	public DocumentListEntry getEntry() throws IOException, ServiceException {
		if(entry_ == null){
			entry_= getAncestorOfType(GoogleDocAccount.class)
				.getResource(resourceId);
		}
		return entry_;
	}

	@Override
	public String getViewLink() {
		try {
			
			return getEntry().getDocumentLink().getHref();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getEditLink() {
		try{
			return getEntry().getEditLink().getHref();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(InputStream in, String mimeType) throws Exception {
		Assert.notNull(getAbsolutePath(), "GoogleDoc should be saved before writing into it");
		getAncestorOfType(GoogleDocAccount.class).updateFile(this,in, mimeType);
	}

	@Override
	public InputStream getInputStream() throws Exception {
		Assert.notNull(getAbsolutePath(), "GoogleDoc should be saved before writing into it");
		return new URL(((MediaContent)getEntry().getContent()).getUri()).openStream();
		
		
	}
	
	
	
	

}
