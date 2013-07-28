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

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.tree.EXTree;

public class FileExplorerUtil {
	
	
	public static void refreshNodeInTree(Container explorer, String path){
		EXTree tree = explorer.getDescendentOfType(EXTree.class);
		
		if(tree != null){
			String id = tree.getDescendentByName(path).getId();
			tree.getNode(id).refreshNode();
		}
		
		
	}
	
	
	

}
