/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.designer;

import java.io.InputStream;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXCSSEditor extends EXPanel implements Event , PopupContainer{

	public EXCSSEditor(String name, String ecmPath) {
		super(name, "CSS Editor");
		setAttribute("ecmPath", ecmPath);
		setWidth(Dimension.parse("500px"));
		EXTextArea textArea = new EXCodeMirror("css", "", "css");
		textArea.setWidth(Dimension.parse("478px"));
		textArea.setHeight(Dimension.parse("348px"));
		String css = loadCss();
		textArea.setValue(css);
		setBody(textArea);
		setShowFooter(true);
		EXButton save = new EXButton("save", "Save");
		save.addEvent(this, Event.CLICK);
		getFooterContainer().addChild(save);
		EXButton cancel = new EXButton("Cancel", "Close");
		cancel.addEvent(CLOSE_EVENT, Event.CLICK);
		getFooterContainer().addChild(cancel);
		setStyle("visibility", "visible");
		setStyle("z-index", "2000");
		setShowCloseButton(true);
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
	}
	
	public void addPopup(Container popup){
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
	}
	
	
	public void save()throws Exception{
		RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
		try{
			BinaryFile bf = (BinaryFile)service.getFile(getAttribute("ecmPath"), Util.getRemoteUser());
			bf.write(getCss().getBytes());
			futil.update(bf);
		}catch(FileNotFoundException nfe){
			
			String path = getAttribute("ecmPath");
			String[] names = StringUtil.split(path, "/");
			String currentName = names[names.length-1];
			String currentDir = "";
			for(int i = 0; i < names.length-1; i++){
				currentDir = currentDir + "/" + names[i];
			}
			
			Directory direc = futil.getDirectory(currentDir);
			BinaryFile bf = direc.createFile(currentName,BinaryFile.class);
			
			bf.write(getCss().getBytes());
			bf.save();
			
		}
	}
	
	public String loadCss(){
		try{
			RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
			BinaryFile bf = (BinaryFile)service.getFile(getAttribute("ecmPath"), Util.getRemoteUser());
			InputStream in = bf.getInputStream();
			return IOUtil.getStreamContentAsString(in);
		}catch(Exception e){
			return "";
		}
	}
	
	
	public String getCss(){
		String css = getDescendentOfType(EXTextArea.class).getValue().toString();
		return css;
	}
	
	


	@Override
	public void ClientAction(ClientProxy container) {
		JMap options = new JMap();//.put("data", new Var("editAreaLoader.getValue(\""+getDescendentOfType(EXTextArea.class).getId()+"\")"));
		container.makeServerRequest(options, this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			
			
			
			if(container.getName().equalsIgnoreCase("save")){
				save();
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
//		if(request.containsKey("txtid")){
//			String id = request.get("txtid");
//			container.appendJSFragment("editAreaLoader.init({\"id\":\""+ id+"\",\"syntax\":\"css\",\"display\" : \"later\",\"start_highlight\":true,\"allow_resize\":\"both\",\"language\":\"en\"});");
//			container.appendJSFragment("eAL.toggle(\""+id+"\");");
//			
//		}else if(request.containsKey("cltxt")){
//			String id = request.get("cltxt");
//			JMap options = new JMap().put("data", new Var("editAreaLoader.getValue(\""+id+"\")"));
//			container.makeServerRequest(options, this);
//		}
		
	}

}
