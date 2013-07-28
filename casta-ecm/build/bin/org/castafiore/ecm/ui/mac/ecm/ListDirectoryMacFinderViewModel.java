package org.castafiore.ecm.ui.mac.ecm;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.mac.events.OpenColumnEvent;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.ui.mac.item.EXMacLabel;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class ListDirectoryMacFinderViewModel implements MacFinderColumnViewModel{

	private FileIterator iterator;
	
	public ListDirectoryMacFinderViewModel(String directoryToList, FileFilter filter) {
		super();
		RepositoryService service = SpringUtil.getRepositoryService();
		iterator = service.getDirectory(directoryToList, Util.getRemoteUser()).getFiles(filter);
		// TODO Auto-generated constructor stub
	}
	
	public ListDirectoryMacFinderViewModel(String directoryToList, Class ofType, boolean b) {
		super();
		RepositoryService service = SpringUtil.getRepositoryService();
		iterator = service.getDirectory(directoryToList, Util.getRemoteUser()).getFiles(ofType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		File f = iterator.get(index);
		EXMacLabel label = new EXMacLabel("", f.getName());
		
		if(f.getClazz().equals(Directory.class.getName()))
			label.setLeftItem(new EXContainer("", "span").addClass("ui-icon-folder-collapsed"));
		else
			label.setLeftItem(new EXContainer("", "span").addClass("ui-icon-document"));
		
		label.setRightItem(new EXContainer("", "span").addClass("ui-icon-triangle-1-e"));
		label.setAttribute("path", f.getAbsolutePath());
		label.addEvent(new OpenColumnEvent(), Event.CLICK);
		return label;
	}

	@Override
	public int size() {
		return iterator.count();
	}

}
