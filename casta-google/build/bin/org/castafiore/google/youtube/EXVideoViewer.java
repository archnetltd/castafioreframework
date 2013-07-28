package org.castafiore.google.youtube;

import org.castafiore.groovy.EXGroovyContainer;
import org.castafiore.utils.ResourceUtil;

public class EXVideoViewer extends EXGroovyContainer {
	
	private String url ="http://www.youtube-nocookie.com/v/e-GfPQ134I4";

	public EXVideoViewer(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/google/youtube/EXVideoViewer.xhtml"));
		addClass("main_image");
	}
	
	
	public void setVideoUrl(String url){
		this.url = url;
		setRendered(false);
	}
	public String getUrl(){
		return url;
	}

}
