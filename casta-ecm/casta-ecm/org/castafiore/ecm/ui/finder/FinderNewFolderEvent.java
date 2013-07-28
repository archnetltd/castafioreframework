package org.castafiore.ecm.ui.finder;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class FinderNewFolderEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXFinder.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		final String selectedFile = container.getAncestorOfType(EXFinderContainer.class).getAttribute("selectedFile");
		EXDynaformPanel panel = new EXDynaformPanel("", "Create a new folder");
		panel.addField("New folder :", new EXInput("input"));
		panel.addButton(new EXButton("save", "Save"));
		panel.addButton(new EXButton("cancel", "Cancel"));
		panel.getDescendentByName("cancel").addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
		panel.setWidth(Dimension.parse("466px"));
		container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		panel.getDescendentByName("save").addEvent(new Event(){

			@Override
			public void ClientAction(ClientProxy container) {
				container.mask();
				container.makeServerRequest(this);
				
			}

			@Override
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				try
				{
					
					
					String name=container.getAncestorOfType(EXDynaformPanel.class).getField("input").getValue().toString();
					if(StringUtil.isNotEmpty(name)){
					
						Directory dir = futil.getDirectory(selectedFile).createFile(name, Directory.class);//new Directory();
						
						dir.makeOwner(Util.getRemoteUser());
						dir.save();
						
						container.getAncestorOfType(EXFinder.class).addNewFile(dir);
						container.getAncestorOfType(EXPanel.class).remove();
					}else{
						container.getAncestorOfType(EXDynaformPanel.class).getField("input").addClass("ui-state-error");
						throw new UIException("A name is required for the new folder");
					}
				}
				catch(Exception e){
					throw new UIException(e);
				}
				return true;
			}

			@Override
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, Event.CLICK);
		
		panel.setStyle("z-index", "4000").setStyle("left", "190px").setStyle("top", "45px");
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
