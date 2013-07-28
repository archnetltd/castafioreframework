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
 package org.castafiore.community.ui;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.utils.BaseSpringUtil;

public class EXFileExplorerDesignableFactory extends AbstractDesignableFactory{

	public EXFileExplorerDesignableFactory() {
		super("EXFileExplorerDesignableFactory");
		setText("File explorer");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCategory() {
		return "ECM";
	}

	@Override
	public Container getInstance() {
		return BaseSpringUtil.getBean("fileExplorer");
	}

	public String getUniqueId() {
		return "ecm:fileExplorer";
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getRequiredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
}
