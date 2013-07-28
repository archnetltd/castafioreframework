package org.castafiore.ecm.ui.mac.ecm;

import org.castafiore.ecm.ui.mac.ecm.actions.ActionOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.attachments.AttachmentOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.extlinks.ExternalLinkOptionsMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.internallinks.InternalLinkOptionsMacFinderVewModel;
import org.castafiore.ecm.ui.mac.ecm.metadata.MetadataOptionsMacFinderModel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class FilesOptionsMacFinderViewModel extends AbstractFileManipOptionsMacFinderViewModel {

	String filePath ;
	
	private Container  originalCaller;
	
	public FilesOptionsMacFinderViewModel(File file, Container originalCaller) {
		super();
		super.addLabel("Select this file", "ui-icon-pencil", "selectFileForShortcut");
		addLabel("Explore", "ui-icon-arrowreturnthick-1-e", "explore");
		this.filePath = file.getAbsolutePath();
		this.originalCaller = originalCaller;
	}
	
	public FilesOptionsMacFinderViewModel(File file) {
		super();
		if(file instanceof Directory){
			super.addLabel("Open with...", "ui-icon-pencil", "openWith");
			addLabel("Explore", "ui-icon-arrowreturnthick-1-e", "explore");
			addLabel("Delete this file", "ui-icon-circle-close", "delete");
			addLabel("Attachements", "ui-icon-pin-s", "attachements");
			addLabel("Internal Links", "ui-icon-link", "internalLinks");
			addLabel("External Links", "ui-icon-extlink", "externalLinks");
		}
		if(file instanceof Shortcut){
			addLabel("Go to ref file", "ui-icon-extlink", "openShortcut");
			addLabel("Change ref file", "ui-icon-shuffle", "changeShortcut");
			addLabel("Delete this shortcut", "ui-icon-circle-close", "delete");
			addLabel("Delete shortcut and ref file", "ui-icon-circle-close", "deleteShortcutAndRef");
		}
		
		addLabel("Metadata", "ui-icon-battery-3", "metadata");
		addLabel("Actions", "ui-icon-script", "actions");
		addLabel("Permissions", "ui-icon-key", "permissions");
		this.filePath = file.getAbsolutePath();
	}
	
	
	private File getFile(Container caller){
		String path = caller.getAttribute("path");
		
		File f = SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		return f;
	}
	
	public EXMacFinderColumn openWith(Container caller){
		return null;
		
		
	}
	
	public EXMacFinderColumn attachements(Container caller){
		AttachmentOptionsMacFinderViewModel model = new AttachmentOptionsMacFinderViewModel(filePath);
		EXMacFinderColumn column = new EXMacFinderColumn("attachements");
		column.setViewModel(model);
		column.setOpenColumnEventHandler(model);
		return column;
	}
	
	public EXMacFinderColumn selectFileForShortcut(Container caller){
//		File f = SpringUtil.getRepositoryService().getFile(shortcutPath, Util.getRemoteUser());
//		if(f instanceof Shortcut){
//			((Shortcut)f).setReference(filePath);
//			SpringUtil.getRepositoryService().update(f, Util.getRemoteUser());
//		}
//		
//		return null;
		
		if(originalCaller instanceof StatefullComponent){
			((StatefullComponent)originalCaller).setValue(filePath);
		}else{
			originalCaller.setText(filePath);
		}
		return null;
	}
	
	public EXMacFinderColumn internalLinks(Container caller){
		InternalLinkOptionsMacFinderVewModel model = new InternalLinkOptionsMacFinderVewModel(filePath);
		EXMacFinderColumn column = new EXMacFinderColumn("internalLinks");
		column.setViewModel(model);
		column.setOpenColumnEventHandler(model);
		return column;
	}
	
	public EXMacFinderColumn externalLinks(Container caller){
		ExternalLinkOptionsMacFinderViewModel model = new ExternalLinkOptionsMacFinderViewModel(filePath);
		EXMacFinderColumn column = new EXMacFinderColumn("externalLinks");
		column.setViewModel(model);
		column.setOpenColumnEventHandler(model);
		return column;
	}
	public EXMacFinderColumn metadata(Container caller){
		MetadataOptionsMacFinderModel model = new MetadataOptionsMacFinderModel(filePath);
		EXMacFinderColumn column = new EXMacFinderColumn("metadata");
		column.setViewModel(model);
		column.setOpenColumnEventHandler(model);
		return column;
	}
	
	public EXMacFinderColumn actions(Container caller){
		ActionOptionsMacFinderViewModel model = new ActionOptionsMacFinderViewModel(filePath);
		EXMacFinderColumn column = new EXMacFinderColumn("actions");
		column.setViewModel(model);
		column.setOpenColumnEventHandler(model);
		return column;
	}
	
	public EXMacFinderColumn explore(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("children");
		ListDirectoryMacFinderViewModel viewModel = new ListDirectoryMacFinderViewModel(filePath, null);
		column.setViewModel(viewModel);
		column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler(originalCaller));
		return column;
	}
	public EXMacFinderColumn permissions(Container caller){
		return null;
	}
	
	public EXMacFinderColumn openShortcut(Container caller){
		File f = SpringUtil.getRepositoryService().getFile(filePath, Util.getRemoteUser());
		if(f instanceof Shortcut){
			Shortcut s = (Shortcut)f;
			File ref = s.getReferencedFile();
			FilesOptionsMacFinderViewModel vModel = new FilesOptionsMacFinderViewModel(ref);
			EXMacFinderColumn column = new EXMacFinderColumn("filesOptions");
			column.setViewModel(vModel);
			column.setOpenColumnEventHandler(vModel);
			return column;
		}
		throw new UIException("This file is not a shortcut");
		
	}
	
	public EXMacFinderColumn changeShortcut(Container caller){
		EXMacFinderColumn column = new EXMacFinderColumn("");
		ListDirectoryMacFinderViewModel model = new ListDirectoryMacFinderViewModel("/root/users", null);
		column.setViewModel(model);
		OpenColumnEventHandler handler = new ShowChangeShortcutRefOptionsEventHandler();
		column.setOpenColumnEventHandler(handler);
		return column;
	}
	
	public EXMacFinderColumn deleteShortcutAndRef(Container caller){
		return null;
	}
	
	public EXMacFinderColumn delete(Container caller){
		return null;
	}
}
