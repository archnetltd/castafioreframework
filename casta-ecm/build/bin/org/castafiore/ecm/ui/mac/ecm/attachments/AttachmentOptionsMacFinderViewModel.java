package org.castafiore.ecm.ui.mac.ecm.attachments;

import org.castafiore.ecm.ui.mac.ecm.AbstractFileManipOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ListDirectoryMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ShowFileOptionsEventHandler;
import org.castafiore.ecm.ui.mac.ecm.files.UploadFileFormMacFinderViewModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.wfs.types.BinaryFile;

public class AttachmentOptionsMacFinderViewModel extends AbstractFileManipOptionsMacFinderViewModel implements OpenColumnEventHandler{
	private String filePath;
	
	public AttachmentOptionsMacFinderViewModel(String filePath) {
		super();
		addLabel("List Attachements", "ui-icon-carat-2-e-w", "listAttachements").addLabel("Add Attachement", "ui-icon-plusthick", "addAttachement");
		this.filePath = filePath;
	}

	
	public EXMacFinderColumn listAttachements(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("children");
		ListDirectoryMacFinderViewModel viewModel = new ListDirectoryMacFinderViewModel(filePath, BinaryFile.class,true);
		column.setViewModel(viewModel);
		column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler(null));
		return column;
	}
	
	public EXMacFinderColumn addAttachement(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("children");
		UploadFileFormMacFinderViewModel viewModel = new UploadFileFormMacFinderViewModel(filePath);
		column.setViewModel(viewModel);
		column.setStyle("width", "400px");
		//column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler());
		return column;
	}
	
}
