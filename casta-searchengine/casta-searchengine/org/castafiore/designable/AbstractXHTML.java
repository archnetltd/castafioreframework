package org.castafiore.designable;

import org.castafiore.designer.layout.EXDroppableXHTMLLayoutContainer;

public class AbstractXHTML extends EXDroppableXHTMLLayoutContainer{

	public AbstractXHTML(String name) {
		super(name);
		
		setTemplateLocation("templates/designable/" + getClass().getSimpleName() + ".xhtml");
			
		
	}

}
