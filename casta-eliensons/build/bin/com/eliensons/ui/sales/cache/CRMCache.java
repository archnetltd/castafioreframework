package com.eliensons.ui.sales.cache;

import org.castafiore.searchengine.back.OSApplicationRegistry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;

public class CRMCache extends EXContainer{

	public CRMCache() {
		super("CRMCache", "div");
		addChild(SpringUtil.getBeanOfType(OSApplicationRegistry.class).getWindow("crm").setDisplay(false));
	}

}
