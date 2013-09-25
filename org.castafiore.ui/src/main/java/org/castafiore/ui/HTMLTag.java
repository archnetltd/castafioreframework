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

package org.castafiore.ui;

import org.castafiore.ui.js.Var;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public interface HTMLTag extends Component {

	public String getText();

	public String getText(boolean escape);

	public Container setText(String text);

	/**
	 * 
	 * @return the tag name of the instance
	 */
	public String getTag();

	/**
	 * 
	 * @param name
	 *            - the name of the attribute to return
	 * @return the value of the attribute
	 */
	public String getAttribute(String name);

	/**
	 * sets an attribute to the
	 * 
	 * @param name
	 * @param value
	 */
	public Container setAttribute(String name, String value);

	/**
	 * returns attributes names accumulated during the whole life cycle of the
	 * component
	 * 
	 * @return
	 */
	public String[] getAttributeNames();

	/**
	 * returns the style for the name specified
	 * 
	 * @param name
	 * @return
	 */
	public String getStyle(String name);

	public Container setAttribute(String name, Var value);

	/**
	 * sets the style for the name specified
	 * 
	 * @param name
	 * @param value
	 */
	public Container setStyle(String name, String value);

	/**
	 * sets a style dynamically
	 * 
	 * @param name
	 * @param var
	 * @return
	 */
	public Container setStyle(String name, Var var);

	/**
	 * @return style names accumulated during the whole life cycle if the tag
	 */
	public String[] getStyleNames();

	/**
	 * returns the static html to start with
	 * 
	 * generally, real root tag of the component
	 * 
	 * @return
	 */
	public String getHTML();

	/**
	 * returns the attribute names that have changed after the last flush was
	 * called
	 * 
	 * @return
	 */
	public String[] getChangedAttributeNames();

	/**
	 * returns the attribute names that have changed after the last flush was
	 * called
	 * 
	 * @return
	 */
	public String[] getChangedStyleNames();

	/**
	 * This method should never be called The only way I have found to prevent
	 * users from calling this method, is by passing a secret key as parameter.
	 * If ever you happen to call this method, the effect on the browser cannot
	 * be predicted
	 */
	public void flush(int secretKey);

}
