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
 package org.castafiore.beans.ui;

import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.navigation.EXAccordeon;

public class EXBeanInfo extends EXBorderLayoutContainer {
	
	public EXBeanInfo(){
		super("EXBeanInfo");
		EXAccordeon acc = new EXAccordeon("acc");
		acc.setWidth(Dimension.parse("225px"));
		acc.setModel(new BeanInfoAccModel());
		acc.setStyle("border", "silver 1px silver");
		acc.setHeight(Dimension.parse("500px"));
		addChild(acc,LEFT);
	}

}
