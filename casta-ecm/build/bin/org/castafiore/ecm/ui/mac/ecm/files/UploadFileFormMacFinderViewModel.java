package org.castafiore.ecm.ui.mac.ecm.files;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.item.EXMacButtonRow;
import org.castafiore.ui.mac.item.EXMacFieldItem;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;


/*
 * \
 * Create forms for all Types
 * Document
 * Product
 * Post
 * Comment
 * Event
 * Link
 * Directory
 * 
 * 
 * Dont bother for uploads, attachements, links
 * 
 * 
 * add Name in upload form
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */









public class UploadFileFormMacFinderViewModel implements MacFinderColumnViewModel, Event{

	private String filePath;
	
	
	public UploadFileFormMacFinderViewModel(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		if(index == 0){
			return new EXMacFieldItem("", "Browse to upload a file",(StatefullComponent)new EXUpload("upload").setStyle("border", "none"));
		}else{
			EXMacButtonRow row = new EXMacButtonRow("buttons");
			row.addButton("save", "Save this file", "ui-icon-disk", this);
			return row;
		}
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXMacFinderColumn.class));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		BinaryFile file =(BinaryFile)container.getAncestorOfType(EXMacFinderColumn.class).getDescendentOfType(EXUpload.class).getFile();
		file.makeOwner(Util.getRemoteUser());
		file.putInto(filePath);
		file.getParent().save();
		//SpringUtil.getRepositoryService().update(file, Util.getRemoteUser());
		//SpringUtil.getRepositoryService().saveIn(filePath, file, Util.getRemoteUser());
		return true;
		//parent.openUsing(caller)
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
