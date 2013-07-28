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
 package org.castafiore.ecm.ui.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.ExplorerView;
import org.springframework.util.PathMatcher;

public class PathViewConfigImpl implements PathViewConfig {
	
	private PathMatcher pathMatcher;
	
	private List<String> patterns;

	private Map<String, ExplorerView> configs = new HashMap<String, ExplorerView>();
	
	public void init()
	{
		String[] keys = configs.keySet().toArray(new String[configs.keySet().size()]);
		
		patterns = new ArrayList<String>();
		for(String s : keys){
			patterns.add(s);
		}
		
		Collections.sort(patterns);
		Collections.reverse(patterns);
	}
	
	public ExplorerView getView(String path) {
	
		for(String pattern : patterns)
		{
			if(pathMatcher.match(pattern, path))
			{
				return configs.get(pattern);
			}
		}
		return null;
	}


	public Map<String, ExplorerView> getConfigs() {
		return configs;
	}


	public void setConfigs(Map<String, ExplorerView> configs) {
		this.configs = configs;
	}


	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}


	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}
	
	

}
