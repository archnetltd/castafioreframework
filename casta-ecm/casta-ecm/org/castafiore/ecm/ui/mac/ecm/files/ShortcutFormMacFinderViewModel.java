package org.castafiore.ecm.ui.mac.ecm.files;

import java.util.Map;

import org.castafiore.ecm.ui.mac.ecm.ListDirectoryMacFinderViewModel;
import org.castafiore.ecm.ui.mac.ecm.ShowFileOptionsEventHandler;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.events.OpenColumnEvent;
import org.castafiore.ui.mac.events.OpenColumnEventHandler;
import org.castafiore.ui.mac.item.EXMacButtonRow;
import org.castafiore.ui.mac.item.EXMacFieldItem;
import org.castafiore.ui.mac.item.EXMacLabel;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.wfs.Util;

public class ShortcutFormMacFinderViewModel implements MacFinderColumnViewModel, Event, OpenColumnEventHandler{

	@Override
	public MacColumnItem getValueAt(int index) {
		if(index == 0){
			return new EXMacFieldItem("", "Please enter the name of the shortcut",new EXInput("name"));
		}else if(index ==1){
			EXMacLabel label = new EXMacLabel("", "Browse folder. Double Click to select the file");
			label.addEvent(new OpenColumnEvent(), Event.CLICK);
			label.setRightItem(new EXContainer("", "span").addClass("ui-icon-triangle-1-e"));
			return label;
		}else{
			EXMacButtonRow row = new EXMacButtonRow("buttons");
			row.addButton("save", "Save this shortcut", "ui-icon-disk", this);
			return row;
		}
	}

	@Override
	public int size() {
		return 3;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXMacFinderColumn.class));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public EXMacFinderColumn getColumn(Container caller) {
		EXMacFinderColumn column = new EXMacFinderColumn("children");
		ListDirectoryMacFinderViewModel viewModel = new ListDirectoryMacFinderViewModel("/root/users/" + Util.getRemoteUser(), null);
		column.setViewModel(viewModel);
		column.setOpenColumnEventHandler(new ShowFileOptionsEventHandler(caller));
		return column;
	}

}
