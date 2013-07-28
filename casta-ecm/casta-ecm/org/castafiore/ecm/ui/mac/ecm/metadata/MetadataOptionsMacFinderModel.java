package org.castafiore.ecm.ui.mac.ecm.metadata;

import org.castafiore.ecm.ui.mac.ecm.AbstractFileManipOptionsMacFinderViewModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;

public class MetadataOptionsMacFinderModel extends AbstractFileManipOptionsMacFinderViewModel {

	private String filePath;
	
	public MetadataOptionsMacFinderModel(String filePath) {
		super();
		addLabel("List Metadata", "ui-icon-carat-2-e-w", "listMetadata").addLabel("Add Metadata", "ui-icon-plusthick", "addMetadata");
		this.filePath = filePath;
	}

	public EXMacFinderColumn listMetadata(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("listMetadata");
		column.setViewModel(new ListFileMetadataMacFinderModel(filePath));
		return column;
	}
	
	public EXMacFinderColumn addMetadata(Container caller){
		return null;
	}
	
}
