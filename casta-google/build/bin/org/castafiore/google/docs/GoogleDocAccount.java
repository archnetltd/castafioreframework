package org.castafiore.google.docs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.iterator.SimpleFileIterator;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.docs.FolderEntry;
import com.google.gdata.data.docs.PresentationEntry;
import com.google.gdata.data.docs.SpreadsheetEntry;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
@Entity
public class GoogleDocAccount extends Article {

	@Type(type="text")
	private String username;

	@Type(type="text")
	private String password;
	
	private String connectionUrl ="https://docs.google.com/feeds/default/private/full/";
	
	private transient DocsService client;
	
	public GoogleDocAccount(){
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	

	public DocsService getClient() throws AuthenticationException {
		Assert.notNull(username, "username cannot be null");
		Assert.notNull(password, "password cannot be null");
		if (client == null) {
			client = new DocsService("JavaGDataClientSampleAppV3.0");
			client.setUserCredentials(username, password);
		}
		return client;
	}

	public DocumentListEntry addEmptyFile(GoogleDoc file) throws DocumentListException, IOException, ServiceException ,Exception{
		Assert.notNull(file,"cannot save null file");
		Assert.notNull(file.getName(), "GoogleDoc name cannot be null");
		
		Assert.notNull(file.getGoogleDocType(), "Google Doc type cannot be null");
		
		String type = file.getGoogleDocType();

		DocumentListEntry newEntry = null;
		if (type.equals("document")) {
			newEntry = new DocumentEntry();
		} else if (type.equals("presentation")) {
			newEntry = new PresentationEntry();
		} else if (type.equals("spreadsheet")) {
			newEntry = new SpreadsheetEntry();
		} else if (type.equals("folder")) {
			newEntry = new FolderEntry();
		}
		newEntry.setTitle(new PlainTextConstruct(file.getName()));
		return getClient().insert(new URL(connectionUrl),newEntry);
	}
	
	
	
	public DocumentListEntry getResource(String resourceId) throws IOException, ServiceException{
		URL url = new URL(connectionUrl + resourceId);
		DocumentListEntry entry = getClient().getEntry(url, DocumentListEntry.class);
		return entry;

	}
	
	

	@Override
	public FileIterator<File> getFiles(FileFilter filter) {
		try {
		List<File> result = new ArrayList<File>();
		List<DocumentListEntry> entries;
		
			entries = getEntries(0, 0, null, null);
		
		for(DocumentListEntry entry : entries){
			GoogleDoc doc = new GoogleDoc(entry.getTitle().getPlainText());
			//doc.setName(entry.getTitle().getPlainText());
		 
			doc.setGoogleDocType(entry.getType());
			doc.setParent(this);
			doc.setAbsolutePath(this.getAbsolutePath() + "/" + entry.getTitle());
			doc.setResourceId(entry.getResourceId());
			result.add(doc);
		}
		
			return new SimpleFileIterator(result);
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		} 
	}

	public void updateFile(GoogleDoc file, InputStream stream, String mimeType) throws Exception {
		DocumentListEntry newEntry = getResource(file.getResourceId());
		MediaSource source = new MediaStreamSource(stream, mimeType);
		newEntry.setMediaSource(source);
		newEntry.updateMedia(true);
		
		
	}

	
	
	public void deleteFile(String resourceId) throws IOException, ServiceException {
		getResource(resourceId).delete();
	}

	public List<DocumentListEntry> getEntries(int page, int pageSize,
			String orderby, String author) throws IOException, ServiceException {
		DocumentQuery query = new DocumentQuery(new URL(connectionUrl));
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
			query.setStartIndex(pageSize * page);
		}
		if (StringUtil.isNotEmpty(orderby)) {
			query.setSortMode(orderby);
		}

		if (StringUtil.isNotEmpty(author)) {
			query.setAuthor(author);
		}
		DocumentListFeed feed = getClient().getFeed(query, DocumentListFeed.class);
		return feed.getEntries();
	}
	
	

}
