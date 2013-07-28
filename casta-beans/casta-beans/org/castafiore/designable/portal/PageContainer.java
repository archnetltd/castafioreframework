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
 package org.castafiore.designable.portal;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.ui.Container;

public interface PageContainer extends DesignableLayoutContainer {
	
	/**
	 * Sets the current visible page in the PageContainer
	 * @param page
	 * @return
	 */
	public Container setPage(Container page);
	
	/**
	 * returns the current visible page in the PageContainer
	 * @return
	 */
	public Container getPage();
	
	/**
	 * checks if the current page is already cached in PageContainer
	 * @param pageName
	 */
	public boolean isCached(String pageName);
	
	
	/**
	 * returns the page with specified name if already cached
	 */
	public Container getCachedPageIfPresent(String pageName);

}
