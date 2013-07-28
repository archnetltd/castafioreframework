package org.castafiore.ecm.ui.mac.ecm.files;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.item.EXMacButtonRow;
import org.castafiore.ui.mac.item.EXMacFieldItem;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.Directory;

public class FolderFormMacFinderViewModel implements MacFinderColumnViewModel, Event{

	private String filePath;
	
	
	public FolderFormMacFinderViewModel(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		if(index == 0){
			return new EXMacFieldItem("", "Please enter the name of the folder",new EXInput("input"));
		}else{
			EXMacButtonRow row = new EXMacButtonRow("buttons");
			row.addButton("save", "Save this folder", "ui-icon-disk", this);
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
		String name =container.getAncestorOfType(EXMacFinderColumn.class).getDescendentOfType(EXInput.class).getValue().toString();
		
		Directory dir = futil.getDirectory(filePath).createFile(name, Directory.class);//new Directory();
		
		dir.makeOwner(Util.getRemoteUser());
		dir.save();
		
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
