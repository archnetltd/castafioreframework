package org.castafiore.ecm.ui.mac.ecm;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class ShowFileOptionsEventHandler implements OpenColumnEventHandler{
	
	
	
	private Container originalCaller = null;

	public ShowFileOptionsEventHandler(Container originalCaller){
		this.originalCaller  = originalCaller;
	}
	
	
//	public EXMacFinderColumn getColumn(Container caller, boolean forShortcut, String shortcutPath) {
//		String path = caller.getAttribute("path");
//		File file = SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
//		FilesOptionsMacFinderViewModel vModel = new FilesOptionsMacFinderViewModel(file);
//		EXMacFinderColumn column = new EXMacFinderColumn("filesOptions");
//		column.setViewModel(vModel);
//		column.setOpenColumnEventHandler(vModel);
//		return column;
//	}
	
	
	@Override
	public EXMacFinderColumn getColumn(Container caller) {
		
		String path = caller.getAttribute("path");
		File file = SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		FilesOptionsMacFinderViewModel vModel = null;
		if(originalCaller == null)
			vModel = new FilesOptionsMacFinderViewModel(file);
		else
			vModel = new FilesOptionsMacFinderViewModel(file,originalCaller);
		EXMacFinderColumn column = new EXMacFinderColumn("filesOptions");
		column.setViewModel(vModel);
		column.setOpenColumnEventHandler(vModel);
		return column;
	}
	
	

}
