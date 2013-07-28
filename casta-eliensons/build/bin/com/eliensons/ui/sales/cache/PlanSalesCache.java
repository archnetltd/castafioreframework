package com.eliensons.ui.sales.cache;

import org.castafiore.searchengine.back.OSApplicationRegistry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;

public class PlanSalesCache extends EXContainer{

	public PlanSalesCache() {
		super("PlanSalesCache", "div");
		addChild(SpringUtil.getBeanOfType(OSApplicationRegistry.class).getWindow("PlanSales").setDisplay(false));
	}

}
