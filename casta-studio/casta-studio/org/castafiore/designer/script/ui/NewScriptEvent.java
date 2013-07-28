package org.castafiore.designer.script.ui;

import java.util.Map;

import org.castafiore.designer.script.ScriptService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.msgbox.EXPrompt;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class NewScriptEvent implements Event {

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		final String ppath = container.getAncestorOfType(EXScriptEditor.class).getPortalPath();
		final EXScriptEditor editor = container.getAncestorOfType(EXScriptEditor.class);
		EXPrompt prompt = new EXPrompt("", "New Module","Please enter the name of the module :" ){

			@Override
			public boolean onOk(Map<String, String> request) {
				String name = getInputValue();
				if(StringUtil.isNotEmpty(name)){
					ScriptService service = SpringUtil.getBeanOfType(ScriptService.class);
					BinaryFile bf = service.getScript(name, ppath);
					if(bf == null){
						service.saveScript(name, " ".getBytes(),ppath);	
						editor.addScriptButton(name);
						editor.openScript(name);
					}else{
						getInput().addClass("ui-state-error");
						throw new UIException("Please choose another name. This module already exists");
					}
				}else{
					getInput().addClass("ui-state-error");
					throw new UIException("Please enter a name for the module");
				}
				return true;
			}
			
		};
		prompt.setStyle("width", "375px");
		container.getAncestorOfType(PopupContainer.class).addPopup(prompt);
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
