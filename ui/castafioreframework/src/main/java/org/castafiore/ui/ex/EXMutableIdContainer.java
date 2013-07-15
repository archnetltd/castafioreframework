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
 package org.castafiore.ui.ex;

import org.castafiore.ui.Container;

public class EXMutableIdContainer extends EXContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id ;
	
	public EXMutableIdContainer(String name, String tagName, String id) {
		super(name, tagName);
		this.id = id;
		
	}
	
	public String getId(){
		if(id == null){
			return super.getId();
		}else{
			return id;
		}
	}
	
	public void setId(String id){
		if(id!= null && !id.equals(this.id)){
			this.id = id;
			Container parent = getParent();
			if(parent != null){
				parent.setRendered(false);
			}else{
				setRendered(false);
			}
		}
		
	}

}
