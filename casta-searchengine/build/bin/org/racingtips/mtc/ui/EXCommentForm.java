package org.racingtips.mtc.ui;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXCommentForm extends EXXHTMLFragment implements EventDispatcher{

	public EXCommentForm(String name) {
		super(name, "templates/racingtips/EXCommentForm.xhtml");
		addChild(new EXInput("titleOfComment").setStyle("width", "320px"));
		addChild(new EXTextArea("summaryOfComment").setAttribute("rows", "5").setAttribute("cols", "60"));
		addChild(new EXContainer("ok", "button").addClass("form-submit").setText("Ok").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("cancel", "button").addClass("form-submit").setText("Cancel").addEvent(DISPATCHER, Event.CLICK));
		
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equals("ok")){
			source.getAncestorOfType(EXRace.class).addComment(((StatefullComponent)getChild("titleOfComment")).getValue().toString(), ((StatefullComponent)getChild("summaryOfComment")).getValue().toString());
		}
		source.getAncestorOfType(EXCommentForm.class).setDisplay(false);
		
	}

}
