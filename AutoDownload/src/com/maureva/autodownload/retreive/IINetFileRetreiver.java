package com.maureva.autodownload.retreive;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class IINetFileRetreiver  {
	
	DefaultHttpClient httpclient= new DefaultHttpClient();

	public void connect() throws ClientProtocolException, IOException {
		try {
			
			final Map<String, String> redirect = new HashMap<String, String>();
			
			httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope("iata-s.iinet.org", 443),
                    new UsernamePasswordCredentials("MD01SS01", "GMFFGLHL"));
			
			httpclient.setRedirectStrategy(new DefaultRedirectStrategy(){

				@Override
				protected boolean isRedirectable(String method) {
					return true;
				}

				@Override
				public boolean isRedirected(HttpRequest request,
						HttpResponse response, HttpContext context)
						throws ProtocolException {
					if(response.containsHeader("Location"))
						redirect.put("Location", response.getFirstHeader("Location").getValue());
					else{
						response.setHeader("Location", redirect.get("Location").replace("update", "retrieve") + "&timerange=31");
						if(redirect.containsKey("Second")){
							return super.isRedirected(request, response, context);
						}else{
							redirect.put("Second", "true");
							return true;
						}
					}
					return super.isRedirected(request, response, context);
				}
				
			});
			HttpPost httpost = new HttpPost("https://iata-s.iinet.org/login.php?name=MD01SS01&pass=GCFJNDYG");
			
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String location = redirect.get("Location");
			httpclient.setRedirectStrategy(new DefaultRedirectStrategy());
			String html = EntityUtils.toString(entity);
			downloadFiles(html, location);

		}catch(Exception e){e.printStackTrace();} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	
	private Element getFilesTable(Source source){
		for(Element e : source.getAllElements("form")){
			if("download.php".equalsIgnoreCase(e.getAttributeValue("action"))){
				return e.getFirstElement("table");
			}
		}
		return null;
	}
	
	public void downloadFiles(String html, String location)throws Exception{
		Source source = new Source(html);
		
		String baseUrl = location.replace("update.php", "https://iata-s.iinet.org/download.php");
		baseUrl = baseUrl + "&timerange=0"; 
		Element table = getFilesTable(source);
		
		if(table != null){
			System.out.println("Files found for download");
			//StringBuilder header = new StringBuilder();
			
			Element body = table.getFirstElement("tbody");
			
			for(Element tr : body.getAllElements("tr")){
				Element submit = tr.getFirstElement("input");
				String name =submit.getAttributeValue("NAME");
				try{
					
					Integer.parseInt(name);
					String url =baseUrl + "&" + name + "=RETRIEVE";
					System.out.println("Posting at : " +url);
					HttpPost post = new HttpPost(url);
					post.addHeader("Referer", "https://iata-s.iinet.org/retrieve.php");
					post.addHeader("User-Agent","	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0");
					post.addHeader("Host","	iata-s.iinet.org");
				
					
					
					HttpResponse response = httpclient.execute(post);
					
					for(Header h : response.getAllHeaders()){
						System.out.println(h);
					}
					
					
					downloadFile(response.getEntity(),name);
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		}else{
			System.out.println("no file found, trying to get from previous 31 days");	
		}
	}
	
	private void downloadFile(HttpEntity entity, String name)throws Exception{
		try {
			System.out.println(EntityUtils.toString(entity));
		    OutputStream output = new FileOutputStream("c:\\java\\"+name+".zip");
		    try {
		        InputStream in = entity.getContent();
		        
		        IOUtils.copy(in, output);
		    	
		    }catch(Exception e){e.printStackTrace();} finally {
		        IOUtils.closeQuietly(output);
		    }
		} finally {
		}
	}
}
