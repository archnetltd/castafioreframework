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

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.Container;
import org.castafiore.ui.HTMLTag;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;
import org.springframework.util.Assert;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public abstract class EXHtmlTag extends EXComponent implements HTMLTag,
		Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Set<String> changedStyles_ = new LinkedHashSet<String>();

	protected Set<String> changedAttributes_ = new LinkedHashSet<String>();

	protected Map<String, String> attributes_ = new LinkedHashMap<String, String>();

	private Map<String, String> styles_ = new LinkedHashMap<String, String>();

	private String tag_;

	private String text_ = "";

	public EXHtmlTag(String name, String tagName) {
		super(name);
		this.setAttribute("name", name);
		this.tag_ = tagName;
	}

	public abstract void flush(int secretKey);

	/**
	 * @see Container#setAttribute(String, String)
	 */
	public String getAttribute(String name) {
		return StringUtil.getValue(name, this.attributes_);
	}

	/**
	 * @see EXDynamicHTMLTag#getHTML()
	 */
	public abstract String getHTML();

	/**
	 * @see Container#getStyle(String)
	 */
	public String getStyle(String name) {
		return StringUtil.getValue(name, styles_);
	}

	/**
	 * @see Container#getTag()
	 */
	public String getTag() {
		return tag_;
	}

	/**
	 * @see HTMLTag#setAttribute(String, String)
	 */
	public Container setAttribute(String name, Var value) {
		Assert.notNull(name,
				"you cannot pass a name as null to set an attribute");
		String txt = value.getJavascript();
		attributes_.put(name, "js::-" + txt);
		changedAttributes_.add(name);
		return this;
	}

	/**
	 * @see HTMLTag#setAttribute(String, String)
	 */
	public Container setAttribute(String name, String value) {

		Assert.notNull(name,
				"you cannot pass a name as null to set an attribute");
		attributes_.put(name, value);
		changedAttributes_.add(name);
		return this;
	}

	/**
	 * @see HTMLTag#setStyle(String, String)
	 */
	public Container setStyle(String name, String value) {
		Assert.notNull(name, "you cannot pass a name as null to set a style");
		styles_.put(name, value);
		changedStyles_.add(name);
		return this;
	}

	/**
	 * @see HTMLTag#setStyle(String, Var)
	 */
	public Container setStyle(String name, Var var) {
		Assert.notNull(name, "you cannot pass a name as null to set a style");
		String txt = var.getJavascript();
		styles_.put(name, "js::-" + txt);
		changedStyles_.add(name);
		return this;
	}

	/**
	 * @see HTMLTag#getAttributeNames()
	 */
	public String[] getAttributeNames() {
		return this.attributes_.keySet().toArray(
				new String[attributes_.keySet().size()]);
	}

	/**
	 * @see HTMLTag#getStyleNames()
	 */
	public String[] getStyleNames() {
		return this.styles_.keySet().toArray(
				new String[styles_.keySet().size()]);
	}

	/**
	 * @see HTMLTag#getChangedAttributeNames()
	 */
	public String[] getChangedAttributeNames() {
		return changedAttributes_
				.toArray(new String[changedAttributes_.size()]);
	}

	/**
	 * @see HTMLTag#getChangedStyleNames()
	 */
	public String[] getChangedStyleNames() {
		return changedStyles_.toArray(new String[changedStyles_.size()]);
	}

	/**
	 * @see HTMLTag#getText(boolean)
	 */
	public String getText(boolean escape) {
		if (text_ == null) {
			return "";
		}
		if (escape)
			return JavascriptUtil.javaScriptEscape(text_.trim());
		else
			return text_;

	}

	/**
	 * @see HTMLTag#getText()
	 */
	public String getText() {
		return getText(true);
	}

	/**
	 * @see HTMLTag#setText(String)
	 */
	public Container setText(String text_) {
		this.text_ = text_;
		setRendered(false);
		return this;
	}

}
