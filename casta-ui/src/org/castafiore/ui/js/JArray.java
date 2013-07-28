/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.js;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.utils.JavascriptUtil;



/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public final class JArray implements JSObject, Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected List<String> internal_ = new LinkedList<String>();


public void sort(){
	Collections.sort(internal_);
}

	/**
	 * adding an array
	 * @param array
	 * @return
	 */
	public JArray add(JArray array)
	{
		internal_.add(array.getJavascript());
		return this;
	}
	
	
	public JArray add(JMap jmap)
	{
		internal_.add(jmap.getJavascript());
		return this;
	}
	
	public boolean contains(String s){
		return internal_.contains(s);
	}
	
	/**
	 * add a simple string
	 * @param s
	 * @return
	 */
	public JArray add(String s)
	{
		internal_.add("\"" + JavascriptUtil.javaScriptEscape(s)+ "\"");
		return this;  
	}
	
	public JArray add(Number n)
	{
		internal_.add(n + "");
		return this;  
	}

	/**
	 * add a function
	 * @param functionjs
	 * @return
	 */
	public JArray add( ClientProxy functionjs)
	{
		StringBuilder function = new StringBuilder();
		
		function.append("function(){").append("\n").append(functionjs.getCompleteJQuery()).append("\n").append("}");
		
		internal_.add(function.toString());
		
		return this;
	}
	
	/**
	 * add a variable
	 * @param key
	 * @param var
	 * @return
	 */
	public JArray add(String key,Var var)
	{
		internal_.add(var.getJavascript());
		
		return this;
	}
	
	public List<String> getCompiled()
	{
		return this.internal_;
	}
	
	public String getJavascript()
	{
		Iterator<String> iterKey = this.internal_.iterator();
		StringBuilder result = new StringBuilder();
		result.append("[");
		while(iterKey.hasNext())
		{
			String value = iterKey.next();

			result.append(value);
			 
			if(iterKey.hasNext())
			{
				result.append(",");
			}		
		}
		result.append("]");
		return result.toString();
	}
	
	public boolean isEmpty()
	{
		return this.internal_.isEmpty();
	}
}
