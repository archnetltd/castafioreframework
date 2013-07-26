package org.castafiore.shoppingmall.crm.newsletter;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;


public class EXNewsletter  extends EXXHTMLFragment {

	public EXNewsletter(String name) {
		super(name, "templates/EXNewsletter.xhtml");
		addChild(new EXInput("title"));
		addChild(new EXRichTextArea("content").setStyle("width", "740px").setStyle("height", "250px"));
		
		addChild(new EXButton("saveNewsletter", "Save").addEvent(EventDispatcher.DISPATCHER, Event.CLICK));
		addChild(new EXButton("cancelNewsletter", "Cancel").addEvent(EventDispatcher.DISPATCHER, Event.CLICK));
	}
	
	public EXNewsletter clear(){
		getChild("title").setText("");
		getChild("content").setText("");
		return this;
	}

	
	public Newsletter fillNewsletter(Newsletter nl){

		nl.setTitle( ((StatefullComponent) getChild("title")).getValue().toString());
		nl.setSummary(((StatefullComponent) getChild("content")).getValue().toString());
		nl.setStatus(nl.STATE_NEW);
		nl.setNewsletterType(nl.TYPE_MAIL);
		
		return nl;
		
		
	}

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
