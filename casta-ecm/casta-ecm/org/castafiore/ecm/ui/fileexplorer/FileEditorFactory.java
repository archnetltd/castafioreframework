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
 package org.castafiore.ecm.ui.fileexplorer;

import java.io.Serializable;

import org.castafiore.ui.Container;
import org.castafiore.wfs.types.File;

public interface FileEditorFactory extends Serializable {
	
	public final static int EDIT_MODE = 0;
	
	public final static int CREATE_MODE = 1;
	
	/**
	 * returns a file editor for the specified file, on the specified explorer, in the specified mode
	 * The implementation should load the file editor and load the file in the current address of the explorer
	 * 
	 * @param file
	 * @param requester
	 * @param mode
	 * @return
	 */
	public Container getFileEditor(File file, Explorer requester, int mode);
}
