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
 package org.castafiore.wfs.search;

import java.util.Map;

import org.castafiore.wfs.search.textextractors.TextExtractor;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;
/**
 * 
 * @author Kureem Rossaye
 *
 */
public class MapConfigurableExtractorFactory implements TextExtractorFactory{
	
	private Map<String, TextExtractor> config ;


	public synchronized TextExtractor getTextExtractor(File file) {
		if(config.containsKey(file.getClazz())){
			return config.get(file.getClazz());
		}else if(file instanceof Article){
				return config.get(Article.class.getName());
		}else{
			return config.get(File.class.getName());
		}
	}


	public Map<String, TextExtractor> getConfig() {
		return config;
	}


	public void setConfig(Map<String, TextExtractor> config) {
		this.config = config;
	}
	
	

}
