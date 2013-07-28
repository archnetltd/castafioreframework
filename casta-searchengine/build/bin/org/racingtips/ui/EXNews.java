package org.racingtips.ui;

import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXNews extends EXXHTMLFragment{

	public EXNews() {
		super("EXNews", ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/articles/news.xhtml"));
		
	}

}
