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
 package org.castafiore.webwizard.forms.impl;

import org.castafiore.designer.model.Module;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXCheckBox;

public class EXModuleContainer extends EXGrid {

	public EXModuleContainer(String name, Module module) {
		super(name, 3, 6);
		addClass("ui-module-container");
		getCell(0, 0).setText("Title :").addClass("ui-module-label");
		getCell(1, 0).setText(module.getTitle()).addClass("ui-module-value ui-module-title");
		getCell(2, 0).addChild(new EXCheckBox(module.getName()));
		
		getCell(0, 1).setText("Price :").addClass("ui-module-label");
		getCell(1, 1).setText(module.getMonthlyFee()).addClass("ui-module-value ui-module-price");
		
		getCell(0, 2).setAttribute("colspan", "2").addClass("ui-module-label").setText("Description :");
		getCell(0, 3).setAttribute("colspan", "2").addClass("ui-module-description").setText(module.getDescription());
		
		Container imgContainer = new EXContainer("imgContainer", "div");
		imgContainer.addClass("ui-module-img-container");
		getCell(0, 4).setAttribute("colspan", "2").addClass("ui-module-label").setText("Thumbnails :");
		getCell(0, 5).setAttribute("colspan", "2").addClass("ui-module-thumbnails").addChild(imgContainer);
		
		String[] imgs = module.getThumbnails();
		
		for(String img : imgs){
			Container uiImg = new EXContainer("", "img");
			uiImg.setAttribute("src", img);
			uiImg.addClass("ui-module-image");
			imgContainer.addChild(uiImg);
		}
		
		
		
	}

}
