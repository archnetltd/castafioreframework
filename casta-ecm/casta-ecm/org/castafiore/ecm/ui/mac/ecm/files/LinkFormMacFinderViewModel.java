package org.castafiore.ecm.ui.mac.ecm.files;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
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
import org.castafiore.wfs.types.Link;

public class LinkFormMacFinderViewModel implements MacFinderColumnViewModel, Event{
	
private String filePath;
	
	
	public LinkFormMacFinderViewModel(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		if(index == 0){
			return new EXMacFieldItem("", "Please enter the name of the link",new EXInput("name"));
		}else if(index ==1){
			return new EXMacFieldItem("", "Please enter the label",new EXInput("label"));
		}else if(index == 2){
			return new EXMacFieldItem("", "Please enter the url",new EXInput("url"));
		}else{
			EXMacButtonRow row = new EXMacButtonRow("buttons");
			row.addButton("save", "Save this link", "ui-icon-disk", this);
			return row;
		}
	}

	@Override
	public int size() {
		return 4;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXMacFinderColumn.class));
		container.makeServerRequest(this);
		
	}
	
	public String getFieldValue(String field, Container caller){
		String result =((StatefullComponent)caller.getAncestorOfType(EXMacFinderColumn.class).getDescendentByName(field)).getValue().toString();
		return result;
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String name =getFieldValue("name", container);
		String label =getFieldValue("label", container);
		String url =getFieldValue("url", container);
		
		
		
		Link doc = futil.getDirectory(filePath).createFile(name, Link.class);//new Link();
		
		doc.setLabel(label);
		doc.setUrl(url);
		doc.makeOwner(Util.getRemoteUser());
		doc.save();
		
		return true;
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
