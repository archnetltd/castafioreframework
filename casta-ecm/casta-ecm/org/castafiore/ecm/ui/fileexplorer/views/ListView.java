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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.ecm.ui.fileexplorer.ExplorerView;
import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ecm.ui.fileexplorer.dialog.FilesTableCellRenderer;
import org.castafiore.ecm.ui.fileexplorer.dialog.FilesTableModel;
import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXScrollableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class ListView extends EXContainer implements ExplorerView{

	public ListView() {
		super("ListView", "div");
		//setWidth(Dimension.parse("500px"));
		setHeight(Dimension.parse("500px"));
		addClass("ui-widget-content").setStyle("border-left", "none");
	}
	
	public ViewModel<ToolBarItem> getActionToolbarModel() {
		//return new MutableToolbarModel().addItem(new SaveTButton("save"));
		return null;
	}
	

	public int refreshView(String dir) throws UIException {
		this.getChildren().clear();
		
		FilesTableModel tableModel = new FilesTableModel(dir, null);
		
		EXTable table = new EXTable("filesList",tableModel);
		table.setWidth(Dimension.parse("100%"));
		table.setCellRenderer(new FilesTableCellRenderer());
		table.setColumnModel(new FilesTableColumnModel());
		
		EXPagineableTable pTable = new EXPagineableTable("pFilesList", table);
		pTable.setHeight(Dimension.parse("511px")).setStyle("overflow", "scroll");
		addChild(pTable);
		setRendered(false);
		return tableModel.getRowCount();
	}

	public void refreshView(Directory dir) throws UIException {
		refreshView(dir.getAbsolutePath());
	}
	
	public String getIdentifierString() {
		return getClass().getName();
	}

	
	public ICon getIcon( final String filePath) {
		final List<ICon> r = new ArrayList<ICon>();
		ComponentUtil.iterateOverDescendentsOfType(this, ICon.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				if(c.getAttribute("path").equalsIgnoreCase(filePath)){
					r.add((ICon)c);
				}
				
			}
		});
		if(r.size() >0){
			return r.get(0);
		}else{
			return null;
		}
	}

	public void addItem(File f) {
		EXTable table = getDescendentOfType(EXTable.class);
		Container tbody = table.getDescendentByName("tbody");
		
		Container tr = new EXContainer("", "tr");
		tbody.addChild(tr);
		
		EXButtonWithLabel driveButton = null;
		if(f.getClazz().equalsIgnoreCase(Directory.class.getName()))
		{
			driveButton = new FilesTableCellRenderer.ListIcon(f,"BlueClosedFolder.gif");
			
		}
		else
		{
			driveButton = new FilesTableCellRenderer.ListIcon(f,"EditDocument.gif");
		}
		
		driveButton.addEvent(new OpenFileEvent(){
			public void ClientAction(ClientProxy application) {
				application.mask();
				application.makeServerRequest(application.getId(), new JMap().put("path", application.getAttribute("path")),this);
				
			}
		}, Event.CLICK);
		
		tr.addChild(new EXContainer("t", "td").addChild(driveButton));
		
		EXContainer ctn = new EXContainer("div", "div");
		Calendar cal = f.getLastModified();
		Date date = cal.getTime();
		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy hh:mm");
		ctn.setText(format.format(date));
		tr.addChild(new EXContainer("t", "td").addChild(ctn));
		
		tr.addChild(new EXContainer("t", "td").addChild(new EXCheckBox("cb").addEvent(new FilesTableCellRenderer(),Event.CLICK).setAttribute("path",f.getAbsolutePath())));
		
		
	}


	public void showFiles(List<File> files) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public String[] getSelectedFiles() {
//		EXTable table = getDescendentOfType(EXTable.class);
//		final List<String> ff = new ArrayList<String>();
//		ComponentUtil.iterateOverDescendentsOfType(table, EXCheckBox.class, new ComponentVisitor() {
//			
//			@Override
//			public void doVisit(Container c) {
//				EXCheckBox cb = (EXCheckBox)c;
//				if(cb.isChecked()){
//					ff.add(cb.getAttribute("path"));
//				}
//				
//			}
//		});
//		
//		return ff.toArray(new String[ff.size()]);
//	}

}
