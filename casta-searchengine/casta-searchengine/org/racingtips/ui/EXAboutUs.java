package org.racingtips.ui;

import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXAboutUs extends EXXHTMLFragment {

	public EXAboutUs() {
		super("EXAboutUs", ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/articles/aboutus.xhtml"));
	}

}
