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
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class FinderEditFileEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXFinderContainer.class));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			String selectedFile = container.getAncestorOfType(EXFinderContainer.class).getAttribute("selectedFile");
			File file = SpringUtil.getRepositoryService().getFile(selectedFile, Util.getRemoteUser());
			if(file instanceof BinaryFile ){
				//EXPanel app = container.getAncestorOfType(EXPanel.class);
				final BinaryFile bf= (BinaryFile)file;
				
				if(bf.getMimeType()!= null && bf.getMimeType().contains("image")){
					request.put("cannotedit", "true");
					return true;
				}
				
				
				EXButton button = new EXButton("save", "Save");
				
				String content = IOUtil.getStreamContentAsString(bf.getInputStream());
				final EXInput name = new EXInput("name");
				name.setValue(file.getName());
				name.setAttribute("disabled", "true");
				final EXTextArea text = new EXTextArea("text");
				text.setValue(content);
				button.addEvent(new Event(){

					@Override
					public void ClientAction(ClientProxy container) {
						container.mask(container.getAncestorOfType(EXPanel.class));
						container.makeServerRequest(this);
						
					}

					@Override
					public boolean ServerAction(Container container,
							Map<String, String> request) throws UIException {
						try
						{
							String sText = text.getValue().toString();
							bf.write(sText.getBytes());
							
							bf.save();
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
				
				EXDynaformPanel panel = new EXDynaformPanel("TextDocument", "Edit this text document");
				EXButton closeButton = new EXButton("close", "Cancel");
				closeButton.addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
				//panel.setAttribute("currentDir", container.getAncestorOfType(EXFileSelector.class).getCurrentDir());
				
				panel.addField("Name :",name);
				panel.addField("Text :", text);
				panel.getDescendentOfType(EXTextArea.class).setStyle("width", "372px");
				panel.getDescendentOfType(EXTextArea.class).setStyle("height", "300px");
				panel.addButton(button);
				panel.addButton(closeButton);
				panel.setDraggable(true);
				panel.setShowCloseButton(true);
				panel.setWidth(Dimension.parse("466px"));
				container.getAncestorOfType(PopupContainer.class).addPopup(panel);
				panel.setStyle("z-index", "4000").setStyle("left", "190px").setStyle("top", "-44px");
				return true;
			}
//			EXPanel panel = new EXPanel("editor", "Edit this file");
//			EXFileEditor editor = new EXFileEditor();
//			editor.openFile(file, file.getParent().getAbsolutePath(), false);
//			panel.setBody(new EXFileEditor());
//			panel.setWidth(Dimension.parse("585px"));
//			container.getAncestorOfType(Application.class).addChild(panel);
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
		
		
		
	}
	
	

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("cannotedit")){
			container.alert("This file cannot be edited online now. Please download it");
		}
		
	}
	
	

}
