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
 package org.castafiore.designer.model;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.renderer.ListRenderer;
import org.castafiore.ui.Container;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.ui.DataProvider;

public class NavListRenderer implements ListRenderer<NavigationDTO> {
	
	private DataProvider<NavigationDTO> dataProvider;

	public String getCategory() {
		return "navigation";
	}

	public Container getContainer(NavigationDTO instance, int index) {
		Container a  = ComponentUtil.getContainer("", "a", "Menu Item " + index, null);
		a.setAttribute("uri", instance.getUri());
		
		return a;
	}

	public List<NavigationDTO> getDummyList() {
		int expectedSize = getExpectedSize();
		List<NavigationDTO> result = new ArrayList<NavigationDTO>();
		for(int i = 0; i < expectedSize; i ++){
			NavigationDTO nav = new NavigationDTO();
			nav.setLabel("Navigation item " + i);
			nav.setPageReference("page-ref-" + i);
			result.add(nav);
		}
		
		return result;
	}

	public int getExpectedSize() {
		if(isConfigured()){
			return getDataProvider().getSize();
		}else{
			return 6;
		}
	}

	public Class<NavigationDTO> getExpectedType() {
		return NavigationDTO.class;
	}

	public List<NavigationDTO> getRealList() {
		return getDataProvider().getList();
	}

	public String getUniqueId() {
		return "arch:menu";
	}

	public boolean isConfigured() {
		if(dataProvider == null){
			return false;
		}else{
			return true;
		}
	}

	public DataProvider<NavigationDTO> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DataProvider<NavigationDTO> dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	

}
