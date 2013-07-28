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
 package org.castafiore.designer.portal;

import java.util.List;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designer.layout.EXDroppablePrimitiveTagLayoutContainer;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.ui.Container;

public class EXDesignablePageContainer extends EXDroppablePrimitiveTagLayoutContainer implements PageContainer {

	
	
	public EXDesignablePageContainer() {
		super();
	}

	public EXDesignablePageContainer(String name, String tageName) {
		super(name, tageName);
	}

	public Container setPage(Container page) {
		
		Container cPage = getChild(page.getName());
		if(cPage == null){
			cPage = page;
			page.addClass("CASTA_PAGE");
			addChild(page);
		}
		
		List<Container> children = getChildren();
		for(Container child : children){
			if(child.getName().equals(cPage.getName())){
				child.setDisplay(true);
			}else{
				child.setDisplay(false);
			}
		}
		return cPage;
	}

	public Container getPage() {

		
		List<Container> children = getChildren();
		for(Container child : children){
			if(child.isVisible()){
				return child;
			}
		}
		
		if(getChildren().size() > 0){
			Container child = children.get(0);
			child.setDisplay(true);
			return child;
		}
		
		return null;

		
	}

	public boolean isCached(String pageName) {
		
		return (getChild(pageName)!= null);
	}
	
	
	public Container getCachedPageIfPresent(String pageName){
		return getChild(pageName);
	}

	
	
	
}
