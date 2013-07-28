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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.designable.EXPrimitiveLayoutDesignableFactory;
import org.castafiore.designer.layout.EXDroppablePrimitiveTagLayoutContainer;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.json.JSONObject;

public class EXDesignablePortalContainer extends EXDroppablePrimitiveTagLayoutContainer implements PortalContainer , PopupContainer{

	private Map<String, String> session = new HashMap<String,String> ();
	
	private List<Datasource> datasources = new LinkedList<Datasource>();
	

	
	
	public EXDesignablePortalContainer() {
		super();
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
	}

	public EXDesignablePortalContainer(String name, String tageName) {
		super(name, tageName);
	}

	public String getDefinitionPath() {
		
		return getAttribute("definitionpath");
	}

	
	public void setDefinitionPath(String value){
		setAttribute("definitionpath", value);
	}

	
	public Map<String, String> getSession() {
		return session;
	}

	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
		
	}
	
	
	private BinaryFile getNavBf(String name)throws Exception{
		Directory porta = SpringUtil.getRepositoryService().getDirectory(getDefinitionPath(), Util.getRemoteUser());
		
		Directory navs = porta.getFile("navigations", Directory.class);
		BinaryFile bf = null;
		if(navs == null){
			navs = porta.createFile("navigations", BinaryFile.class);
			bf= navs.createFile(name, BinaryFile.class);
			NavigationDTO dto = new NavigationDTO();
			dto.setLabel("Root");
			dto.setName("root");
			dto.setPageReference("root");
			dto.setUri("root");
			String ss = NavigationDTO.getJSON(dto).toString();
			bf.write(ss.getBytes());
			porta.save();
			return bf;
		}else{
			bf =  navs.getFile(name, BinaryFile.class);
			if(bf == null){
				bf = navs.createFile(name, BinaryFile.class);
				NavigationDTO dto = new NavigationDTO();
				dto.setLabel("Root");
				dto.setName("root");
				dto.setPageReference("root");
				dto.setUri("root");
				String ss = NavigationDTO.getJSON(dto).toString();
				bf.write(ss.getBytes());
				porta.save();
				return bf;
			}else{
				return bf;
			}
		}
		
		
		
		
		
		
		
		
		
	}
	
	public NavigationDTO getNavigation(String name)throws Exception{
		BinaryFile bf = getNavBf(name);
		String s = IOUtil.getStreamContentAsString( bf.getInputStream());
		return NavigationDTO.getObject(new JSONObject(s));
		
	}
	
	public String[] getNavigationNames(){
		Directory porta = SpringUtil.getRepositoryService().getDirectory(getDefinitionPath(), Util.getRemoteUser());
		Directory navs = porta.getFile("navigations", Directory.class);
		if(navs == null){
			navs = porta.createFile("navigations", BinaryFile.class);
			return new String[]{"Default"};
		}else{
			List<BinaryFile> files = navs.getFiles(BinaryFile.class).toList();
			List<String> names = new ArrayList<String>();
			
			for(File f : files){
				names.add(f.getName());
			}
			return names.toArray(new String[names.size()]);
		}
	}
	
	public void saveNavigation(String name,NavigationDTO dto)throws Exception{
		BinaryFile bf = getNavBf(name);
		bf.write(NavigationDTO.getJSON(dto).toString().getBytes());
		bf.save();
		
	}

	@Override
	public PortalContainer addDatasource(Datasource d) {
		datasources.add(d);
		return this;
	}

	@Override
	public List<Datasource> getDatasources() {
		return datasources;
	}
}
