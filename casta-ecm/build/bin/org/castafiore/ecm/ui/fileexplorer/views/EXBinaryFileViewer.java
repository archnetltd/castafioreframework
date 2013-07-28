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
 package org.castafiore.ecm.ui.fileexplorer.views;

import org.castafiore.ecm.ui.editor.EditImageEvent;
import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.ecm.ui.fileexplorer.icon.IconImageProvider;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class EXBinaryFileViewer extends EXContainer implements FileEditor{

	public BinaryFile file = null;
	
	private String currentDir;
	
	private boolean isNew = true;
	
	public EXBinaryFileViewer() {
		super("download", "div");
		//setWidth(Dimension.parse("864px"));
		//setHeight(Dimension.parse("200px"));
		//setShowHeader(false);
		//setShowFooter(false);
		//setDraggable(false);
	}
	public boolean isImageType(String mimetype)
	{	
		if(mimetype != null)
			return mimetype.startsWith("image");
		return false;
	}
	
	public boolean isTextType(String mimetype){
		if(mimetype != null)
			return mimetype.startsWith("text");
		return false;
	}

private String getViewUrl(BinaryFile bf){
	Application app = getRoot();
	String contextPath = app.getContextPath();
	String serverPort = app.getServerPort();
	String servaerName = app.getServerName();
	
	String result = "http://" + servaerName + ":" + serverPort  + contextPath + "/" + ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath()) ;
	result = "http://docs.google.com/viewer?url=" + result;
	return result;
}
public void addField(String label, StatefullComponent input){
	addChild(new EXContainer("", "label").setText(label).setStyle("display", "block"));
	addChild(input);
}
public StatefullComponent getField(String name){
	return (StatefullComponent)getDescendentByName(name);
}

public void setBody(Container c){
	this.getChildren().clear();
	setRendered(false);
	addChild(c);
}
	public void initialiseEditor(File file,String directory, boolean isNew) {
		try{
			this.file = (BinaryFile)file;
			this.isNew = isNew;
			this.currentDir = directory;
			if(isNew || isTextType(this.file.getMimeType())){
				addField("Text :", new EXTextArea("text"));
				getDescendentOfType(EXTextArea.class).setStyle("width", "800px");
				//getDescendentOfType(EXTextArea.class).setStyle("width", "465px");
				//getDescendentOfType(EXTextArea.class).setStyle("height", "300px");
				if(file != null)
					getDescendentOfType(EXTextArea.class).setValue(IOUtil.getStreamContentAsString(this.file.getInputStream()));
			}else{
				EXXHTMLFragment fragment = new EXXHTMLFragment("inner", ResourceUtil.getDownloadURL("classpath","org/castafiore/ecm/ui/fileexplorer/views/EXBinaryFileViewer.xhtml"));
				
				setBody( fragment);
				
				String downloadUrl = ResourceUtil.getIconUrl(BaseSpringUtil.getBeanOfType(IconImageProvider.class).getIConImage(file), "large");
				if(isImageType(this.file.getMimeType()))
				{
						downloadUrl = ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath());
				}
				Container a = ComponentUtil.getContainer("download", "a", "Download", "").setAttribute("target", "_blank");
				a.setAttribute("href", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath()));
				fragment.addChild(a);
				
				
				String viewUrl = getViewUrl(this.file);
				Container view = new EXContainer("view", "a");
				
				
				if(isImageType(this.file.getMimeType())){
					view.setText("Edit using pixlr").setAttribute("src", ResourceUtil.getDownloadURL("ecm", downloadUrl));
					view.setAttribute("href", "#").addEvent(new EditImageEvent(), Event.CLICK);
				}else{
					view.setAttribute("href", viewUrl).setAttribute("target", "_blank").setText("view");
				}
				Container img = ComponentUtil.getContainer("icon", "img", null, null);
				img.setAttribute("src", downloadUrl);
				fragment.addChild(img);
				fragment.addChild(view);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	public File save(String name) {
		try{
			if(isNew){
				
				
				file = futil.getDirectory(currentDir).createFile(name,BinaryFile.class);
				
				
//				
				file.setOwner(Util.getRemoteUser());
			}
				
				
				if(isTextType(file.getMimeType())){
					
					file.write(getDescendentOfType(EXTextArea.class).getValue().toString().getBytes());
					file.save();
					
					return file;
				}
			
		}catch(Exception e){
			throw new UIException(e);
		}
		return file;
		
		
	}
	

}
