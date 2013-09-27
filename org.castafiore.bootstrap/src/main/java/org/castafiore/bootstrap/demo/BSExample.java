package org.castafiore.bootstrap.demo;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class BSExample extends EXContainer implements Event {
	
	private String code;

	public BSExample(String name) {
		super(name, "div");
		addChild(new EXContainer("example", "div").addClass("bs-example"));
		addChild(new EXContainer("hightlight", "div").addClass("highlight")
				.addChild(
						new EXContainer("pre", "pre").addClass("brush:java").addChild(new EXContainer(
								"code", "code").addClass("html")))); 
	}

	public BSExample setExample(Container c, String code) {
		getChild("example").addChild(c.addClass("clearfix"));
		getChild("example").addChild(new EXContainer("source", "a").setAttribute("href", "#" + c.getName()).setText("View source").addEvent(this, CLICK));
		this.code = code;
		return this;
	}
	
	
	
	private static String keywords = "abstract assert boolean break byte case catch char class const " +
			"continue default do double else enum extends " +
			"false final finally float for goto if implements import " +
			"instanceof int interface long native new null " +
			"package private protected public return " +
			"short static strictfp super switch synchronized this throw throws true " +
			"transient try void volatile while";
	
	private String highlight(String code){
		String[] kw = StringUtil.split(keywords, " ");
		for(String k : kw){
			code =code.replace(k, "<span style='color:red'>"+k+"</span>");
		}
		return code;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getText().contains("View")){
			getDescendentByName("code").setDisplay(true);
			if(!StringUtil.isNotEmpty( getDescendentByName("code").getText())){
				getDescendentByName("code").setText(highlight(code));
			}
			container.setText("Hide source");
		}else{
			getDescendentByName("code").setDisplay(false);
			container.setText("View source");
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}


}
