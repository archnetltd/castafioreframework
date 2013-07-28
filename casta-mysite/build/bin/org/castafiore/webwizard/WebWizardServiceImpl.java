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
 package org.castafiore.webwizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.castafiore.designer.model.Module;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class WebWizardServiceImpl implements WebWizardService {
	

	private String workflowDir;
	
	private String templateUsername;
	
	
	
	public String getTemplateUsername() {
		return templateUsername;
	}

	public void setTemplateUsername(String templateUsername) {
		this.templateUsername = templateUsername;
	}

	private RepositoryService repositoryService;

	public List<Module> getModules() {
		List<Module> result = new ArrayList<Module>();
		try{
			FileIterator iter = repositoryService.getDirectory("/root/portal-data/modules", Util.getRemoteUser()).getFiles(BinaryFile.class);
			while(iter.hasNext()){
				BinaryFile bf   = (BinaryFile)iter.next();
				
				Properties prop = new Properties();
				prop.load(bf.getInputStream());
				Module m = new Module();
				m.setName(prop.getProperty("name"));
				m.setMonthlyFee(prop.getProperty("monthlyFee"));
				m.setDescription(prop.getProperty("description"));
				m.setTitle(prop.getProperty("title"));
				String[] img = StringUtil.split(prop.getProperty("img"),";");
				m.setThumbnails(img);
				result.add(m);
				
				//bf.getInputStream()
			}
			return result;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public List<Template> getTemplates() {
		List<Template> result = new ArrayList<Template>();
		try{
			FileIterator iter = repositoryService.getDirectory("/root/portal-data/master-templates", Util.getRemoteUser()).getFiles(Directory.class);
			while(iter.hasNext()){
				
				File f = iter.next();
				if(f.getName().indexOf(".") <= 0){
					Template t = new Template();
					t.setName(f.getName());
					result.add(t);
				}
				
			}
			
			return result;
		}catch(Exception e){
			
			throw new RuntimeException(e);
		}
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public String getWorkflowDir() {
		return workflowDir;
	}

	public void setWorkflowDir(String workflowDir) {
		this.workflowDir = workflowDir;
	}
	
	

	
	
	

}
