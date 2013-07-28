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

package org.castafiore.ui.ex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.html.DynamicHTMLTag;
import org.castafiore.utils.StringUtil;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public abstract  class EXDynamicHTMLTag extends EXHtmlTag implements DynamicHTMLTag , Container
{
	private Map<Integer, List<Event>> events = new LinkedHashMap<Integer, List<Event>>(0);

	public EXDynamicHTMLTag(String name, String tagName) 
	{
		super(name, tagName);
	}
	
	
	@Override
	public void flush(int secretKey) 
	{
		if(secretKey == 12031980)
		{
			this.setRendered(true);
		
			//this.changedAttributes_.clear();
			attributes_ = StringUtil.flushChanged(attributes_);
		
			//this.changedStyles_.clear();
			styles_ = StringUtil.flushChanged(styles_);
		}
		
	}
	
	@Override
	/**
	 * template method that should be overridden according to underlying technology used to load html
	 */
	public abstract String getHTML() ;
		
	public Container addEvent(Event event, int type) 
	{
		
		if (events.containsKey(type)) 
		{
			events.get(type).add(event);
		} 
		else 
		{
			events.put(type, new LinkedList<Event>());
			events.get(type).add(event);
		}
		setRendered(false);
		return this;
	}

	public Map<Integer, List<Event>> getEvents() 
	{
		return this.events;
	}
}
