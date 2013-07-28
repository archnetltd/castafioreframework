package org.castafiore.ecm.ui.mac.ecm;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.wfs.Util;

public class ShowChangeShortcutRefOptionsEventHandler implements OpenColumnEventHandler {

	@Override
	public EXMacFinderColumn getColumn(Container caller) {
		String path = caller.getAttribute("path");
		ChangeSortcRefOptionsMacFinderViewModel vModel = new ChangeSortcRefOptionsMacFinderViewModel(SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser()));
		EXMacFinderColumn column = new EXMacFinderColumn("filesOptions");
		column.setViewModel(vModel);
		column.setOpenColumnEventHandler(vModel);
		return column;
	}

}
