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
 package org.castafiore.ecm.ui.query;

import java.util.List;

import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXMultiInput;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class EXMultiFileSelector extends EXMultiInput implements
		SelectFileHandler {

	public EXMultiFileSelector(String name) {
		super(name);
		addRow("/root/users/" + Util.getRemoteUser());

	}

	public   StatefullComponent getInput(){
		return new EXLabel("", "");
	}
	
	public void onEdit(String selected, Container row) {
		showExplorer(selected, row);
	}

	public void onAddRow(String lastItem, Container newRow) {
		showExplorer(lastItem, newRow);
	}

	private void showExplorer(String path, Container row) {
		
		
		EXFinder finder = new EXFinder("myFileSelector", null, this, path);
		finder.setAttribute("callerrowid", row.getId());
		addChild(finder);
		finder.setStyle("position", "absolute");
		finder.setStyle("z-index", "6000");
		//finder.a
		
	}

	public List<String> getSelectedFiles() {
		return (List<String>) super.getValue();
	}

	public void setSelectedFiles(List<String> files) {
		setValue(files);
	}

	public void onSelectFile(String abosolutePath, EXFileSelector selector) {
		getDescendentById(selector.getAttribute("callerrowid"))
				.getChildByIndex(0).getChildByIndex(0).setText(abosolutePath);
		selector.getAncestorOfType(EXPanel.class).remove();

	}

	@Override
	public void onSelectFile(File file, EXFinder selector) {
		getDescendentById(selector.getAttribute("callerrowid"))
		.getChildByIndex(0).getChildByIndex(0).setText(file.getAbsolutePath());
		selector.remove();
		setRendered(false);
		
	}

}
