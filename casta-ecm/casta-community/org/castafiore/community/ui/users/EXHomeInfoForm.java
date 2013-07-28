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
 package org.castafiore.community.ui.users;

import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ComponentUtil;

public class EXHomeInfoForm extends EXDynaformPanel{

	public EXHomeInfoForm() {
		super("ExHomeInfoForm", "Home info");
		addField("# :", new EXInput("uniqueId"));
		addField("Street :", new EXInput("street"));
		addField("City :", new EXInput("city"));
		addField("State/Prov :", new EXInput("stateProv"));
		
		addField("Postal code :", new EXInput("postalCode"));
		addField("Country :", new EXInput("country"));
		addField("Mobile :", new EXInput("mobile"));
		addField("Tel :", new EXInput("tel"));
		addField("Email :", new EXInput("email"));
		addField("Website :", new EXInput("website"));
		
		addButton(new EXButton("Save", "Save"));
		addButton(new EXButton("Cancel", "Cancel"));
		setShowCloseButton(false);
		setShowHeader(false);
		setDraggable(false);
		setWidth(Dimension.parse("100%"));
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "270px");
	}
	
	
	

}
