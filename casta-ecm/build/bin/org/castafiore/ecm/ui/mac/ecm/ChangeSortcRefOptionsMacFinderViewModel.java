package org.castafiore.ecm.ui.mac.ecm;

import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class ChangeSortcRefOptionsMacFinderViewModel extends FilesOptionsMacFinderViewModel {

	public ChangeSortcRefOptionsMacFinderViewModel(File file) {
		super(file);
		super.clear();
		if(file instanceof Directory){
			super.addLabel("Open with...", "ui-icon-pencil", "openWith");
			addLabel("Explore", "ui-icon-arrowreturnthick-1-e", "explore");
			addLabel("Select file", "ui-icon-check", "selectFile");
		}

		this.filePath = file.getAbsolutePath();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EXMacFinderColumn selectFile(Container caller){
		return null;
	}

}
