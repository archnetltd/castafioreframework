package org.castafiore.ecm.ui.fileexplorer.views;

import java.util.List;

import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.js.JArray;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;
import org.hibernate.Session;

public class EXShortcutEditor extends EXContainer implements FileEditor{
	private boolean _new = false;
	
	public EXShortcutEditor() {
		super("EXShortcutEditor", "div");
		addChild(new EXContainer("", "label").setText("Enter path reference :").setStyle("display", "block"));
		addChild(new EXAutoComplete("value", "").setSource(new AutoCompleteSource() {
			
			@Override
			public JArray getSource(String param) {
				Session s = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
				String sql = "select absolutePath from WFS_FILE order by absolutePath";
				if(param != null && param.length() > 0){
					sql = "select absolutePath from WFS_FILE where absolutePath like '%" + param  + "%' order by absolutePath";
				}
				
				List l =s.createSQLQuery(sql).setMaxResults(10).list();
				
				JArray result = new JArray();
				
				for(Object o : l){
					result.add(o.toString());
				}
				
				return result;
			}
		}).setStyle("width", "800px"));
		
	}

	

	@Override
	public void initialiseEditor(File file, String directory, boolean isNew) {
		_new = isNew;
		setAttribute("dir", directory);
		if(!_new){
			setAttribute("path", file.getAbsolutePath());
			getDescendentOfType(EXInput.class).setValue(((Shortcut)file).getReference());
		}
		
	}

	@Override
	public File save(String name) {
		if(!_new){
			Shortcut val = (Shortcut)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			val.setReference(getDescendentOfType(EXInput.class).getValue().toString());
			val.save();
			return val;
		
		}else{
			Directory dir = SpringUtil.getRepositoryService().getDirectory(getAttribute("dir"), Util.getRemoteUser());
			Shortcut val = dir.createFile(name, Shortcut.class);
			val.setReference(getDescendentOfType(EXInput.class).getValue().toString());
			val.save();
			return val;
		}
	}
	
	
	
	
}
