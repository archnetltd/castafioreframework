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

package org.castafiore.utils;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.Constant;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.JSObject;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public class JavascriptUtil {
	
	
	public static String getAlert(String message)
	{
		return "alert('"+message+"');";
	}
	
	public static String simpleEscape(String input){
		if (input == null) {
			return input;
		}

		StringBuffer filtered = new StringBuffer(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			}
			else if (c == '\'') {
				filtered.append("\\'");
			}
			else if (c == '\\') {
				filtered.append("\\\\");
			}
			else if (c == '\t') {
				filtered.append("\\t");
			}
			else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			}
			else if (c == '\r') {
				filtered.append("\\n");
			}
			else if (c == '\f') {
				filtered.append("\\f");
			}
			else {
				filtered.append(c);
			}
			prevChar = c;
		}
		return filtered.toString();
	}
	
	public static String javaScriptEscape(String input) {
		if (input == null) {
			return input;
		}

		StringBuffer filtered = new StringBuffer(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			}
			else if (c == '\'') {
				filtered.append("\\'");
			}
			else if (c == '\\') {
				filtered.append("\\\\");
			}
			else if (c == '/') {
				filtered.append("\\/");
			}
			else if (c == '\t') {
				filtered.append("\\t");
			}
			else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			}
			else if (c == '\r') {
				filtered.append("\\n");
			}
			else if (c == '\f') {
				filtered.append("\\f");
			}
			else {
				filtered.append(c);
			}
			prevChar = c;
		}
		return filtered.toString();
	}
	
	public static String generateJS(Object...params){
		String result = "";
		for (Object o : params) 
		{
			if(o == null)
			{
				continue;
			}
			else if(o instanceof Number)
			{
				result = result + "," + o;
				
			}
			else if( o instanceof Boolean){
				result = result + "," + o;
			}
				
			else if(o instanceof ClientProxy)
			{
				result = result + "," + "function(){" + ((ClientProxy)o).getCompleteJQuery() + "}";
			}
			else if(o instanceof JSObject){
				result = result + "," + ((JSObject)o).getJavascript();
			}
			else
			{
				String s = o.toString();
				if(!s.startsWith(Constant.NO_CONFLICT ))
					result = result + "," + ClientProxy.addQuote(s);
				else
					result = result + "," + s;
			}
		}
		if(result.length() > 0)
			result = result.substring(1);
		
		return result;
	}
	
	public static JMap toJMap(Map<String, String> map){
		
		JMap jmap = new JMap();
		
		
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			
			jmap.put(key, map.get(key));
		}
		
		return jmap;
		
		
	}

}
