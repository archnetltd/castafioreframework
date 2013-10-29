/*
 * 
 */
package org.castafiore.ui.ex.media;

import java.util.Map;

import org.castafiore.ui.template.Compiler;
import org.castafiore.ui.template.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class UIMP3Player extends EXXHTMLFragment implements org.castafiore.ui.template.Compiler{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public UIMP3Player(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/ui/ex/media/EXMP3Player.xhtml"));
		setMp3Url(ResourceUtil.getDownloadURL("classpath", "org/castafiore/ui/ex/media/mp3.mp3"));
	}
	
	

	@Override
	public Compiler getCompiler() {
		return this;
	}



	public String getMp3Url() {
		return getAttribute("mp3Url");
	}



	public UIMP3Player setMp3Url(String mp3Url) {
		setAttribute("mp3Url", mp3Url);
		setRendered(false);
		return this;
	}




	public String compile(String template, Map<String, Object> context)
			throws Exception {
		String url = "castafiore/resource?spec=classpath:org/castafiore/ui/ex/media/player.swf";
		return "<object type=\"application/x-shockwave-flash\" data=\""+url+"\" width=\"200\" height=\"20\">" +
				"<param name=\"movie\" value=\""+url+"\" />" +
						"<param name=\"FlashVars\" value=\"mp3="+getMp3Url()+"\" /></object>";
	}
	
	
	

}