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
 package org.castafiore.designer.explorer;

import java.util.Map;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ui.Container;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;

public class EXFileSelectorDesignableFactory extends AbstractDesignableFactory {

	public EXFileSelectorDesignableFactory() {
		super("EXFileSelectorDesignableFactory");
		setText("File selector");
	}

	@Override
	public String getCategory() {
		return "ECM";
	}

	@Override
	public Container getInstance() {
		return new EXFileSelector("EXFileSelector");
	}

	public String getUniqueId() {
		return "ecm:fileSelector";
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(attributeValue, Util.getRemoteUser());
		
		((EXFileSelector)c).refreshUI(dir);
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"rootDir"};
	}
}
