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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class StyleUtil {
	
	private static List<String> styles = null;
	
	//private static Random rand = new Random();
	
	static{

		
		if(styles == null)
		{
			styles = new ArrayList<String>();
			try
			{
				BufferedReader in = new BufferedReader(	new InputStreamReader(	Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/properties/styles.properties"), Charset.forName("ISO-8859-1")));
				
				String inputLine;
		
				while ((inputLine = in.readLine()) != null)
				    styles.add(inputLine);
		
				in.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Collections.sort(styles);
		}
		
		
	}
	
	public static List<String> getStyles()
	{
		return styles;
	}
	
	

}
