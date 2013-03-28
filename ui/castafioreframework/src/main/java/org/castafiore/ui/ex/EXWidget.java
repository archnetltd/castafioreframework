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

public class EXWidget extends EXContainer {
	
	private final static String[] CORNERS = new String[]{
		"top", "right", "bottom", "left", "tl", "tr", "bl", "br", "all"
	};

	public EXWidget(String name, String tagName) {
		super(name, tagName);
		addClass("ui-widget");
	}
	
	public void setCorners(Corners corner){
		String sCorner = CORNERS[corner.ordinal()];
		removeCorners();
		addClass("ui-corner-" + sCorner);
	}
	
	public void removeCorners(){
		for(String corner : CORNERS){
			removeChild("ui-corner-" + corner);
		}
	}
	
	public void setReset(){
		addClass("ui-helper-reset");
	}

}
