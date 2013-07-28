package org.castafiore.ecm.ui.mac.ecm.extlinks;

import org.castafiore.ecm.ui.mac.ecm.AbstractFileManipOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ListDirectoryMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ShowFileOptionsEventHandler;
import org.castafiore.ecm.ui.mac.ecm.files.LinkFormMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.files.UploadFileFormMacFinderViewModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Link;

public class ExternalLinkOptionsMacFinderViewModel extends AbstractFileManipOptionsMacFinderViewModel{

	
	private String filePath ;

	public ExternalLinkOptionsMacFinderViewModel(String filePath) {
		super();
		addLabel("List External Links", "ui-icon-carat-2-e-w", "listExternalLinks").addLabel("Add External Link", "ui-icon-plusthick", "addExternalLinks");
		this.filePath = filePath;
	}

	public EXMacFinderColumn listExternalLinks(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("children");
		ListDirectoryMacFinderViewModel viewModel = new ListDirectoryMacFinderViewModel(filePath, Link.class,true);
		column.setViewModel(viewModel);
		column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler(null));
		return column;
	}
	
	public EXMacFinderColumn addExternalLinks(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("children");
		LinkFormMacFinderViewModel viewModel = new LinkFormMacFinderViewModel(filePath);
		column.setViewModel(viewModel);
		
		//column.setStyle("width", "400px");
		//column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler());
		return column;
	}
}
