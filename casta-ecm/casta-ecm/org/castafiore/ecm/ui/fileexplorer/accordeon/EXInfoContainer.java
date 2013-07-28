package org.castafiore.ecm.ui.fileexplorer.accordeon;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class EXInfoContainer extends EXContainer{

	public EXInfoContainer() {
		super("containerPerms", "div");
		//Container mainContainer = ComponentUtil.getContainer("containerPerms", "div", null, null);
		setWidth(Dimension.parse("100%"));
		
		
		Container infoContainer = new EXContainer("infoContainer", "div");
		infoContainer.setWidth(Dimension.parse("100%"));
		
		EXContainer fileName = new EXContainer("fileName", "span");
		fileName.setText("Name :");
		fileName.setStyle("display", "block");
		fileName.setStyle("margin", "6px");
		infoContainer.addChild(fileName);
		
		EXContainer owner = new EXContainer("fileOwner", "span");
		owner.setStyle("display", "block");
		owner.setStyle("margin", "6px");
		owner.setText("Owner :");
		infoContainer.addChild(owner);
		
		EXContainer type = new EXContainer("fileType", "span");
		type.setStyle("display", "block");
		type.setStyle("margin", "6px");
		type.setText("File type:");
		infoContainer.addChild(type);
		
		
		Container checkOutContainer = ComponentUtil.getContainer("checkOutContainer", "div",null, null);
		EXContainer lblkCheckout = new EXContainer("", "span");
		lblkCheckout.setStyle("display", "inline");
		lblkCheckout.setStyle("margin", "6px");
		lblkCheckout.setText("Checkout:");
		checkOutContainer.addChild(lblkCheckout);
		
		EXCheckBox checkBox = new EXCheckBox("checkOut");
		checkOutContainer.addChild(checkBox);
		checkBox.setStyle("display", "inline");
		checkBox.addEvent(new Event(){

		
			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}

			
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				
				Explorer explorer = container.getAncestorOfType(Explorer.class);
				String selectedFile = explorer.getSelectedFiles()[0];
				File f = SpringUtil.getRepositoryService().getFile(selectedFile, Util.getRemoteUser());
				if( ((EXCheckBox)container).isChecked() ){
					//SpringUtil.getRepositoryService().lockFile(f, Util.getRemoteUser());
					f.lock();
					f.save();
				}else{
					f.releaseLock();
					f.save();
					//SpringUtil.getRepositoryService().unlockFile(f, Util.getRemoteUser());
				}
				
				
				explorer.getView().getIcon(selectedFile).setFile(f);
				return true;
			
			}

			
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, Event.CHANGE);
		
		addChild(checkOutContainer);
		
		addChild(infoContainer);
	}

}
