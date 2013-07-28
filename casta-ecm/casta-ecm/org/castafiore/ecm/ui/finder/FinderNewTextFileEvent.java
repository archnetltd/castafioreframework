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
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class FinderNewTextFileEvent implements Event{
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXFinderContainer.class));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			final String selectedFile = container.getAncestorOfType(EXFinderContainer.class).getAttribute("selectedFile");
			
			if(true){
				
				EXButton button = new EXButton("save", "Save");
				final EXInput name = new EXInput("name");
				final EXTextArea text = new EXTextArea("text");
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
							Directory dir = futil.getDirectory(selectedFile);
							BinaryFile bf = dir.createFile(name.getValue().toString(), BinaryFile.class);//new BinaryFile();
							
							String sText = text.getValue().toString();
							bf.write(sText.getBytes());
							
							bf.makeOwner(Util.getRemoteUser());
							dir.save();
							
							container.getAncestorOfType(EXFinder.class).addNewFile(bf);
							container.getAncestorOfType(EXPanel.class).remove();
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
				panel.setWidth(Dimension.parse("485px"));
				
				//ComponentUtil.metamorphosePanel(panel);
				container.getAncestorOfType(PopupContainer.class).addPopup(panel);
				panel.setStyle("z-index", "4000").setStyle("left", "190px").setStyle("top", "-44px");
				return true;
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
		
		
		
	}
	
	

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
