package org.castafiore.site.wizard;

import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.ui.ex.EXContainer;

public class EXFinal extends EXXHTMLFragment {

	public EXFinal(String name, String username) {
		super(name, "webos/gs/EXFinal.xhtml");
		String url= "http://www.emallofmauritius.com/ecommerce.jsp?m=" + username;
		addChild(new EXContainer("ecommerce", "a").setAttribute("href",url ).setText(url));
	}

}
