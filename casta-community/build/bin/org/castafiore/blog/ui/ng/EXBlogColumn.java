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
 package org.castafiore.blog.ui.ng;

import org.castafiore.ui.Droppable;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class EXBlogColumn extends EXContainer implements Droppable{

	public EXBlogColumn(String name) {
		super(name, "div");
		addClass("portal-leftcolumn");
	}

	public String[] getAcceptClasses() {
		return new String[]{"dbx-box"};
	}

	public JMap getDroppableOptions() {
		
		return null;
	}
	
	public void onReady(ClientProxy container){
		container.addMethod("sortable", new JMap().put("handle", ".dbx-handle").put("connectWith", ".column"));
	}
	
	

}
