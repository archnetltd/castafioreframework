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
 package org.castafiore.ecm.ui.fileexplorer.icon;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class DefaultIconImageProvider implements IconImageProvider {
	
	private Properties extensionMappings = new Properties();
	
	private Map<String, String> icons = new HashMap<String, String>();
	
	
	public DefaultIconImageProvider(){
		try{
			extensionMappings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/ecm/extension-mapping.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	public Map<String, String> getIcons() {
		return icons;
	}



	public void setIcons(Map<String, String> icons) {
		this.icons = icons;
	}



	public String getIConImage(File f) throws UIException {
		
		String iconImage = "file.gif";
		
		if(f instanceof BinaryFile)
		{
			String[] parts = StringUtil.split(f.getName(), ".");;
			if(parts.length > 1)
			{
				//has extension
				String extension = parts[parts.length-1];
				String img = extensionMappings.getProperty(extension.toLowerCase().trim(), "file");
				iconImage = img + ".gif";
			}
		}
		else if(icons.containsKey(f.getClazz())){
			iconImage = icons.get(f.getClazz());
			
			//we have a mapping for each class name somewhere
		}
//		else if(f.isDirectory())
//		{
//			iconImage = "folder.gif";
//		}
		return iconImage;
		
	}

}
