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
 package org.castafiore.designer;

import java.io.InputStream;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.designer.script.ScriptService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;

public class EXPortalExecutor extends EXApplication implements RefreshSentive{
	
	
	private String defaultHome = "home";
	
	private RepositoryService repositoryService;
	
	//private ScriptService scriptService;

	
	
	public EXPortalExecutor() {
		super("portal");
		//scriptService = SpringUtil.getBeanOfType(ScriptService.class);
		repositoryService = SpringUtil.getRepositoryService();
	}
	
	
	public void setData( final String path){
		try{
			PortalContainer c = loadPortal( path);
			addChild(c);
			Studio.goToPage("home", c);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	protected PortalContainer loadPortal( String path)throws Exception{
		BinaryFile fileData = (BinaryFile)repositoryService.getFile(path, Util.getRemoteUser());
		InputStream in = fileData.getInputStream();
		PortalContainer c = (PortalContainer)DesignableDTO.buildContainer(in, false);
		c.setDefinitionPath(path);
		
		//load stylesheets
		loadStylesheets(c);
		
		SpringUtil.getBeanOfType(ScriptService.class).evaluateModules(c);
		
		return c;
	}
	
	protected void loadStylesheets(PortalContainer pc){
		String path = pc.getDefinitionPath();
		String[] parts = StringUtil.split(path, "/");
		String parent = "";
		if(parts.length > 1){
			for(int i = 0; i < parts.length -1; i ++){
				parent = parent + "/" + parts[i];
			}
		}
		String name = parts[parts.length-1];
		String cssName = StringUtil.split(name, ".")[0] + ".css";
		Container link = ComponentUtil.getContainer("", "link", null, null);
		link.setAttribute("rel", "stylesheet");
		link.setAttribute("href", ResourceUtil.getDownloadURL("ecm", path + "/" + cssName));
		link.setAttribute("type", "text/css");
		pc.addChild(link);
	}
	
	public void refresh(){
		
			this.getChildren().clear();
			this.setRendered(false);
			String path = (String)getConfigContext().get("portalPath");
			if(path != null)
				setData(path);
		
	}


	public void onRefresh() {
		if(getConfigContext().containsKey("devmode")){
		refresh();
		}
		
	}


	


	public String getDefaultHome() {
		return defaultHome;
	}


	public void setDefaultHome(String defaultHome) {
		this.defaultHome = defaultHome;
	}


	public RepositoryService getRepositoryService() {
		return repositoryService;
	}


	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}


	
	
	

}
