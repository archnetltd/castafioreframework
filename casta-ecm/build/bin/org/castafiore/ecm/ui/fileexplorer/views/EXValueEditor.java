package org.castafiore.ecm.ui.fileexplorer.views;

import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;

public class EXValueEditor extends EXContainer implements FileEditor{

	private boolean _new = false;
	
	
	public EXValueEditor() {
		super("EXValueEditor", "div");
		addChild(new EXContainer("", "label").setText("Enter property value :").setStyle("display", "block"));
		addChild(new EXTextArea("value").setStyle("height", "270px"));
		
	}

	@Override
	public void initialiseEditor(File file, String directory, boolean isNew) {
		_new = isNew;
		setAttribute("dir", directory);
		if(!_new){
			setAttribute("path", file.getAbsolutePath());
			getDescendentOfType(EXTextArea.class).setValue(((Value)file).getString());
		}
		
		
	}

	@Override
	public File save(String name) {
		if(!_new){
			Value val = (Value)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			val.setString(getDescendentOfType(EXTextArea.class).getValue().toString());
			val.save();
			return val;
		}else{
			Directory dir = SpringUtil.getRepositoryService().getDirectory(getAttribute("dir"), Util.getRemoteUser());
			Value val = dir.createFile(name, Value.class);
			val.setString(getDescendentOfType(EXTextArea.class).getValue().toString());
			val.save();
			return val;
		}
		
		
	}

}
