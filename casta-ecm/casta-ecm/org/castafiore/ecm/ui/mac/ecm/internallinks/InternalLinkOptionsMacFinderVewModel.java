package org.castafiore.ecm.ui.mac.ecm.internallinks;

import org.castafiore.ecm.ui.mac.ecm.AbstractFileManipOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.FilesOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ListDirectoryMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ShowFileOptionsEventHandler;
import org.castafiore.ecm.ui.mac.ecm.files.ShortcutFormMacFinderViewModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class InternalLinkOptionsMacFinderVewModel extends AbstractFileManipOptionsMacFinderViewModel{
	
	private String filePath ;

	public InternalLinkOptionsMacFinderVewModel(String filePath) {
		super();
		addLabel("List Internal Links", "ui-icon-carat-2-e-w", "listInternalLinks").addLabel("Add Internal Link", "ui-icon-plusthick", "addInternalLinks");
		this.filePath = filePath;
	}

	public EXMacFinderColumn listInternalLinks(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("shortcuts");
		ListDirectoryMacFinderViewModel viewModel = new ListDirectoryMacFinderViewModel(filePath, new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname instanceof Shortcut){
					return true;
				}else
					return false;
			}
		});
		column.setViewModel(viewModel);
		column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler(caller));
		return column;
	}
	
	public EXMacFinderColumn addInternalLinks(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("shortcuts");
		ShortcutFormMacFinderViewModel viewModel = new ShortcutFormMacFinderViewModel();
		column.setViewModel(viewModel);
		column.setOpenColumnEventHandler(viewModel);
		return column;
	}
	
}
