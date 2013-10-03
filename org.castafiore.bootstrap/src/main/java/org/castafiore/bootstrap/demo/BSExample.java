package org.castafiore.bootstrap.demo;

import java.net.URLEncoder;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.utils.JavascriptUtil;

public class BSExample extends EXContainer  {
	private String code;
	
	public BSExample(String name) {
		super(name, "div");
		addChild(new EXContainer("example", "div").addClass("bs-example"));
		addChild(new EXContainer("preview-iframe", "iframe").setAttribute("src", "about:blank")); 
	}

	public BSExample setExample(Container c, String code) {
		getChild("example").addChild(c.addClass("clearfix"));
		this.code = code;
		return this;
	}
	
	
	public void onReady(ClientProxy proxy){
		String id = getDescendentByName("preview-iframe").getId();
		proxy.appendJSFragment("$('#"+id+"').contents().find('html').html('"+JavascriptUtil.javaScriptEscape("<script src=https://gist.github.com/kureem/6776174.js></script>")+"');");
	}
	
	
	

}
