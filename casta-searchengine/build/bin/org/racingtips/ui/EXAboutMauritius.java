package org.racingtips.ui;

import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXAboutMauritius extends EXXHTMLFragment {

	public EXAboutMauritius() {
		super("EXAboutMauritius", ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/articles/aboutmauritius.xhtml"));
	}

}