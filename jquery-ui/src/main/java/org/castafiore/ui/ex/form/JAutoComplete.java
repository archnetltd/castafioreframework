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

package org.castafiore.ui.ex.form;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class JAutoComplete extends EXInput  {

	private AutoCompleteSource source_ = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> dictionary = new ArrayList<String>();
	private JMap options = new JMap();

	public JAutoComplete(String name, String value, List<String> dictionary) {
		super(name, value);
		if (dictionary != null)
			this.dictionary = dictionary;

	}

	public JAutoComplete setSource(AutoCompleteSource source) {
		this.source_ = source;
		setRendered(false);
		return this;
	}

	public JAutoComplete addInDictionary(String... value) {
		if (value != null) {
			for (String val : value) {
				dictionary.add(val);
			}
		}
		setRendered(false);
		return this;
	}

	public JAutoComplete(String name, String value) {
		this(name, value, null);

	}

	public JAutoComplete setAutoFocus(Boolean b) {
		options.put("autoFocus", b);
		return this;
	}

	public JAutoComplete setDelay(Integer delay) {
		options.put("delay", delay);
		return this;
	}

	public JAutoComplete setDisabled(Boolean b) {
		options.put("disabled", b);
		return this;
	}

	public JAutoComplete setMinLength(Integer minLength) {
		options.put("minLength", minLength);
		return this;
	}

	public JAutoComplete setPosition(String position) {
		options.put("position", position);
		return this;
	}

	private JAutoComplete setEvent(Event e, String name) {
		addEvent(e, Event.MISC);
		ClientProxy proxy = new ClientProxy(this);
		e.ClientAction(proxy);
		options.put(name, proxy, "event", "ui");
		return this;
	}

	public JAutoComplete setChangeEvent(Event e) {
		return setEvent(e, "change");
	}

	public JAutoComplete setCloseEvent(Event e) {
		return setEvent(e, "close");
	}

	public JAutoComplete setCreateEvent(Event e) {
		return setEvent(e, "create");
	}

	public JAutoComplete setFocusEvent(Event e) {
		return setEvent(e, "focus");
	}

	public JAutoComplete setOpenEvent(Event e) {
		return setEvent(e, "open");
	}

	public JAutoComplete setResponseEvent(Event e) {
		return setEvent(e, "response");
	}

	public JAutoComplete setSearchEvent(Event e) {
		return setEvent(e, "search");
	}

	public JAutoComplete setSelectEvent(Event e) {
		return setEvent(e, "select");
	}

	public void setDictionary(List<String> dictionary) {
		this.dictionary = dictionary;
		setRendered(false);
	}

	public String readSource(String param) {
		if (source_ != null) {
			return source_.getSource(param).getJavascript();
		} else {
			return "{}";
		}
	}

	
	
	public void onReady(ClientProxy container) {
		if (source_ == null) {
			JArray array = new JArray();
			for (String s : dictionary) {
				array.add(s);
			}
			options.put("source", array);
		} else
			options.put("source",
					ResourceUtil.getMethodUrl(this, "readSource", "term"));
		container.addMethod("autocomplete", options);

	}

	

}
