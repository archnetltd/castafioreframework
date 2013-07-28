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
 package org.castafiore.ecm.ui.fileexplorer.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.ExplorerView;
import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class FilesTableCellRenderer implements CellRenderer, Event {

	
	final static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy hh:mm");
	public Container getComponentAt(int row, int column, int page, TableModel model, EXTable table) {
		if(column == 0)
		{
			File f = (File)model.getValueAt(column, row, page);
			EXButtonWithLabel driveButton = null;
			if(f.getClazz().equalsIgnoreCase(Directory.class.getName()))
			{
				driveButton = new ListIcon(f,"BlueClosedFolder.gif");
				
			}
			else
			{
				driveButton = new ListIcon(f,"EditDocument.gif");
			}
			driveButton.addEvent(new OpenFileEvent(){

				@Override
				public void ClientAction(ClientProxy application) {
					application.mask();
					application.makeServerRequest(application.getId(), new JMap().put("path", application.getAttribute("path")),this);
					
				}
				
			}, Event.CLICK);
			//driveButton.setAttribute("fi", value)
			return driveButton;
		}
		else if(column == 1)
		{
			EXContainer ctn = new EXContainer("div", "div");
			Calendar cal = (Calendar)model.getValueAt(column, row, page);
			Date date = cal.getTime();
			
			ctn.setText(format.format(date));
			return ctn;
		}else{
			 return new EXCheckBox("cb").addEvent(this,CLICK).setAttribute("path", model.getValueAt(column, row, page).toString()); 
		}
	}

	public void onChangePage(Container component, int row, int column, int page, TableModel model, EXTable table) {
		if(column == 0)
		{
		
			ListIcon driveButton = (ListIcon)component;
			File f = (File)model.getValueAt(column, row, page);
			//EXButtonWithLabel driveButton = null;
			if(f.getClazz().equalsIgnoreCase(Directory.class.getName()))
			{
				driveButton.setIcon("BlueClosedFolder.gif", "medium");
				driveButton.setAttribute("path", f.getAbsolutePath());
			}
			else
			{
				driveButton.setIcon("EditDocument.gif", "medium");
				driveButton.setAttribute("path", f.getAbsolutePath());
				//driveButton = new ListIcon(f,"EditDocument.gif");
			}
			driveButton.setText(f.getName());
		}
		else if(column == 1)
		{
			
			Calendar cal = (Calendar)model.getValueAt(column, row, page);
			Date date = cal.getTime();
			
			component.setText(format.format(date));
		}else{
			 component.setAttribute("path", model.getValueAt(column, row, page).toString()); 
		}
		
	}
	
	public static class ListIcon extends EXButtonWithLabel implements ICon {
		
		
		public ListIcon(File f,  String iconName) {
			super(f.getAbsolutePath(), f.getName(), iconName, "medium");
			
			setAttribute("path", f.getAbsolutePath());
		}

		public File getFile() {
			return SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		}

		
		public void setFile(File file) {
			setAttribute("path", file.getAbsolutePath());
			
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Explorer exp = container.getAncestorOfType(Explorer.class);
		//String[] as = exp.getSelectedFiles();
		//String path = container.getAttribute("path");
		final List<String> selected = new ArrayList<String>();
		ComponentUtil.iterateOverDescendentsOfType(container.getAncestorOfType(EXTable.class), EXCheckBox.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				
				if(!c.getName().equalsIgnoreCase("cball")){
					boolean checked = ((EXCheckBox)c).isChecked();
					if(checked)
						selected.add(c.getAttribute("path"));
				}
				
			}
		});
		
		exp.setSelectedFiles(selected.toArray(new String[selected.size()]));
		
		
		
		
		
//		if (((EXCheckBox)container).isChecked()){
//			if(as == null){
//				as = new String[]{path};
//			}else{
//				//ArrayUtils.add(as, path);
//				List<String> tmp = new ArrayList<String>();
//				for(String s : as ){
//					tmp.add(s);
//				}
//				tmp.add(path);
//				as = tmp.toArray(new String[tmp.size()]);
//			}
//			
//		}else{
//			ArrayUtils.remove(as, as.length-1);
//		}
		
		//exp.setSelectedFiles(as);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
