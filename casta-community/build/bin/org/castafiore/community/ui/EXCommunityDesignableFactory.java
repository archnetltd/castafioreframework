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

public class EXCommunityDesignableFactory extends AbstractDesignableFactory {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	public EXCommunityDesignableFactory() {
		super("ExCommunityDesignableFactory");
		setText("Community");
	}

	@Override
	public String getCategory() {
		return "Community";
	}

	@Override
	public Container getInstance() {
		return new EXCommunity();
	}

	public String getUniqueId() {
		return "community:community";
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
